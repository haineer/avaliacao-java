package br.com.confitec.teste.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Triple;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classe com as regras de cálculo para o produto prestamista (POC e v2).
 * <p>
 * Para executar, basta invocar o método main informando os seguintes parâmetros
 * via <code>String[]</code>:
 * <ul>
 * <li>dataNascimento</li>
 * <li>valorEmprestimo</li>
 * <li>tipoLinhaCredito</li>
 * <li>taxaJurosCDC</li>
 * <li>prazoEmDias</li>
 * </ul>
 * Exemplo: <code>1990-01-01 125000 1 1.35 1440</code>
 *
 * @author <a href="mailto:hainer.silva@confitec.com.br">Hainer Alan Silva</a>
 */
public class CalculoPrestamista {
	private static final String ID_TRANSACAO = UUID.randomUUID().toString();
	private static final NumberFormat FORMATTER = NumberFormat.getCurrencyInstance();

//	@Autowired
//	private static FuncaoBuscarFaixaReferencia funcaoBuscarFaixaReferencia;

	public static void main(final String[] args) {
		final LocalDate dataNascimento = LocalDate.parse(args[0]);
		final BigDecimal valorEmprestimo = new BigDecimal(args[1]);
		final Integer tipoLinhaCredito = Integer.parseInt(args[2]);
		final BigDecimal taxaJurosCDC = new BigDecimal(args[3]).divide(BigDecimal.valueOf(100));
		final Integer prazoEmDias = Integer.parseInt(args[4]);

		final long idade = ChronoUnit.YEARS.between(dataNascimento, LocalDate.now());

		calcularCotacaoPrestamistaPoc(Collections.singletonList(CoberturaPrestamistaEnum.MNA), valorEmprestimo,
				prazoEmDias);
		calcularCotacaoPrestamista(Arrays.asList(CoberturaPrestamistaEnum.values()), tipoLinhaCredito, valorEmprestimo,
				taxaJurosCDC, prazoEmDias, idade);
	}

	/**
	 * Realiza o cálculo de cotação prestamista para emissão única e capital fixo
	 * (POC).
	 * <p>
	 * <code>
	 * <table>
	 * <tr>
	 * <td>premioNet</td>
	 * <td>=</td>
	 * <td>cs * tn * n</td>
	 * </tr>
	 * <tr>
	 * <td>premioLiquido</td>
	 * <td>=</td>
	 * <td><center>premioNet</center>
	 * <hr>
	 * <center>(1 - Σ%c)</center></td>
	 * </tr>
	 * <tr>
	 * <td>valorTotalComissao</td>
	 * <td>=</td>
	 * <td>premioLiquido - premioNET</td>
	 * </tr>
	 * <tr>
	 * <td>premioBruto</td>
	 * <td>=</td>
	 * <td>premioLiquido * (1 + %iof)</td>
	 * </tr>
	 * <tr>
	 * <td>valorIof</td>
	 * <td>=</td>
	 * <td>premioBruto - premioLiquido</td>
	 * </tr>
	 * </table>
	 * </code>
	 * <p>
	 * Onde:
	 * <ul>
	 * <li><code><b>cs</b></code> - Capital segurado</li>
	 * <li><code><b>tn</b></code> - Taxa NET (fixa)</li>
	 * <li><code><b>n</b></code> - Prazo total da operação de crédito em dias</li>
	 * <li><code><b>Σ%c</b></code> - Somatório dos percentuais de comissões</li>
	 * </ul>
	 *
	 * @param listCobertura   Lista de coberturas contratadas;
	 * @param valorEmprestimo Valor do empréstimo sem seguro <code>[cs]</code>;
	 * @param prazoEmDias     Prazo de pagamento da operação de crédito
	 *                        <code>[n]</code>.
	 */
	public static void calcularCotacaoPrestamistaPoc(final List<CoberturaPrestamistaEnum> listCobertura,
			final BigDecimal valorEmprestimo, final Integer prazoEmDias) {
		System.out.println("Emissão única | Capital segurado fixo (POC)");

		final AtomicReference<BigDecimal> premioNet = new AtomicReference<>(BigDecimal.ZERO);
		final Map<CoberturaPrestamistaEnum, ResultadoFinanceiro> mapCalculoCobertura = new HashMap<>(
				listCobertura.size());

		listCobertura.forEach(cobertura -> {
			final BigDecimal taxaNet = recuperarTaxaNet(prazoEmDias, 1, cobertura, 30, Boolean.FALSE, Boolean.TRUE);

			premioNet.set(valorEmprestimo.multiply(taxaNet).multiply(BigDecimal.valueOf(prazoEmDias)).setScale(4,
					RoundingMode.FLOOR));

			mapCalculoCobertura.put(cobertura,
					calcularCoberturaPrestamista(taxaNet, premioNet.get(), cobertura.getAliquotaIof()));
		});

		final ResultadoFinanceiro total = sumarizarCotacao(mapCalculoCobertura);

		System.out.println(total);
	}

	/**
	 * Realiza o cálculo de cotação prestamista para emissão única e capital igual
	 * ao saldo devedor.
	 * <p>
	 * <code>
	 * <table>
	 * <tr>
	 * <td>premioNet (MNA/IPTA)</td>
	 * <td>=</td>
	 * <td>Σsd * (1 + p) * tn * 30</td>
	 * </tr>
	 * <tr>
	 * <td>premioNet (DI/IFTA)</td>
	 * <td>=</td>
	 * <td>3 * (1 + p) * P * tn * n</td>
	 * </tr>
	 * <tr>
	 * <td>premioLiquido</td>
	 * <td>=</td>
	 * <td><center>premioNet</center>
	 * <hr>
	 * <center>(1 - Σ%c)</center></td>
	 * </tr>
	 * <tr>
	 * <td>valorTotalComissao</td>
	 * <td>=</td>
	 * <td>premioLiquido - premioNet</td>
	 * </tr>
	 * <tr>
	 * <td>premioBruto</td>
	 * <td>=</td>
	 * <td>premioLiquido * (1 + %iof)</td>
	 * </tr>
	 * <tr>
	 * <td>valorIof</td>
	 * <td>=</td>
	 * <td>premioBruto - premioLiquido</td>
	 * </tr>
	 * </table>
	 * </code>
	 * <p>
	 * Onde:
	 * <ul>
	 * <li><code><b>Σsd</b></code> - Somatório do saldo devedor acumulado com
	 * margem</li>
	 * <li><code><b>p</b></code> - Margem percentual</li>
	 * <li><code><b>tn</b></code> - Taxa NET diária</li>
	 * <li><code><b>P</b></code> - Valor da parcela da operação de crédito sem
	 * seguro</li>
	 * <li><code><b>n</b></code> - Prazo total da operação de crédito em dias</li>
	 * <li><code><b>Σ%c</b></code> - Somatório dos percentuais de comissões</li>
	 * <li><code><b>%iof</b></code> - Percentual de IOF da cobertura</li>
	 * </ul>
	 *
	 * @param listCobertura    Lista de coberturas contratadas;
	 * @param tipoLinhaCredito Tipo de empréstimo solicitado;
	 * @param valorEmprestimo  Valor do empréstimo sem seguro;
	 * @param taxaJuros        Taxa de juros;
	 * @param prazoEmDias      Prazo de pagamento da operação de crédito
	 *                         <code>[n]</code>;
	 * @param idade            Idade do proponente do seguro.
	 */
	public static void calcularCotacaoPrestamista(final List<CoberturaPrestamistaEnum> listCobertura,
			final Integer tipoLinhaCredito, final BigDecimal valorEmprestimo, final BigDecimal taxaJuros,
			final Integer prazoEmDias, final long idade) {
		System.out.println("Emissão única | Capital segurado igual ao saldo devedor");

		final AtomicReference<BigDecimal> premioNet = new AtomicReference<>(BigDecimal.ZERO);
		final Map<CoberturaPrestamistaEnum, ResultadoFinanceiro> mapCalculoCobertura = new HashMap<>(
				listCobertura.size());

		final int quantidadeDiasMes = 30;
		final int prazoEmMeses = prazoEmDias / quantidadeDiasMes;
		final BigDecimal margem = recuperarMargem(prazoEmDias);

		final BigDecimal valorParcela = calcularValorParcela(taxaJuros, prazoEmMeses, valorEmprestimo);
		final BigDecimal valorSaldoDevedorMargem = calcularSaldoDevedor(taxaJuros, prazoEmMeses, valorEmprestimo,
				valorParcela, margem);

		listCobertura.forEach(cobertura -> {
			final BigDecimal taxaNet = recuperarTaxaNet(prazoEmDias, tipoLinhaCredito, cobertura, idade, Boolean.FALSE,
					Boolean.FALSE);

			if (cobertura.isTemporaria())
				premioNet.set(BigDecimal.valueOf(3).multiply(BigDecimal.ONE.add(margem)).multiply(valorParcela)
						.multiply(taxaNet).multiply(BigDecimal.valueOf(prazoEmDias)).setScale(4, RoundingMode.FLOOR));
			else
				premioNet.set(valorSaldoDevedorMargem.multiply(taxaNet).multiply(BigDecimal.valueOf(quantidadeDiasMes))
						.setScale(4, RoundingMode.FLOOR));

			mapCalculoCobertura.put(cobertura,
					calcularCoberturaPrestamista(taxaNet, premioNet.get(), cobertura.getAliquotaIof()));
		});

		final ResultadoFinanceiro total = sumarizarCotacao(mapCalculoCobertura);

		System.out.println("Valor parcela                 : ".concat(FORMATTER.format(valorParcela)));
		System.out.println("Valor saldo devedor com margem: ".concat(FORMATTER.format(valorSaldoDevedorMargem)));
		System.out.println(total);
	}

	/**
	 * Recupera a margem configurada na tabela <i>tbPrestamistaMargem</i> para o
	 * prazo do financiamento.
	 * <p>
	 * <strong>Estas faixas deverão ser mapeadas e recuperadas do Redis!</strong>
	 *
	 * <pre>
	 * tbPrestamistaMargem_[prazoEmDias]
	 * </pre>
	 * <p>
	 * <code>
	 * <table border="1">
	 * <center>
	 * <tr>
	 * <th>De (dias)</th>
	 * <th>Até (dias)</th>
	 * <th>Majoração</th>
	 * </tr>
	 * <tr>
	 * <td><center>120</center></td>
	 * <td><center>730</center></td>
	 * <td><center><b>6%</b></center></td>
	 * </tr>
	 * <tr>
	 * <td><center>731</center></td>
	 * <td><center>1461</center></td>
	 * <td><center><b>10%</b></center></td>
	 * </tr>
	 * <tr>
	 * <td><center>1462</center></td>
	 * <td><center>2191</center></td>
	 * <td><center><b>12%</b></center></td>
	 * </tr>
	 * <tr>
	 * <td><center>2192</center></td>
	 * <td><center>2923</center></td>
	 * <td><center><b>15%</b></center></td>
	 * </tr>
	 * <tr>
	 * <td><center>2924</center></td>
	 * <td><center>3654</center></td>
	 * <td><center><b>15%</b></center></td>
	 * </tr>
	 * <tr>
	 * <td><center>3655</center></td>
	 * <td><center>3958</center></td>
	 * <td><center><b>15%</b></center></td>
	 * </tr>
	 * </table>
	 * </code>
	 *
	 * @param prazoEmDias Prazo que será enquadrado nas faixas de referência
	 *                    configuradas.
	 *
	 * @return Margem percentual para majoração.
	 */
	private static BigDecimal recuperarMargem(final Integer prazoEmDias) {
		final Object[] args = { ID_TRANSACAO, "tbPrestamistaMargem", prazoEmDias };
//		return funcaoBuscarFaixaReferencia.executar(args);

		if (prazoEmDias >= 120 && prazoEmDias <= 730)
			return BigDecimal.valueOf(0.06);
		if (prazoEmDias >= 731 && prazoEmDias <= 1461)
			return BigDecimal.valueOf(0.10);
		if (prazoEmDias >= 1462 && prazoEmDias <= 2191)
			return BigDecimal.valueOf(0.12);
		return BigDecimal.valueOf(0.15);
	}

	/**
	 * Recupera a taxa NET configurada na tabela <i>tbPrestamistaTaxa</i> para a
	 * cobertura de acordo com os dados do seguro.
	 * <p>
	 * <strong>Estes dados deverão ser mapeados e recuperados do Redis!</strong>
	 *
	 * <pre>
	 * tbPrestamistaTaxa_[prazoEmDias]_[tipoLinhaCredito]_[idCobertura]_[idade]_[canalPresencial]
	 * </pre>
	 *
	 * @param prazoEmDias      Prazo que será enquadrado nas faixas de referência
	 *                         configuradas;
	 * @param tipoLinhaCredito Tipo de linha de crédito:
	 *                         <ol>
	 *                         <li>Consignado</li>
	 *                         <li>Crédito pessoal</li>
	 *                         <li>Crédito veículo</li>
	 *                         <li>Parcela única</li>
	 *                         </ol>
	 * @param cobertura        Cobertura;
	 * @param idade            Idade do proponente;
	 * @param canalPresencial  Indicador de canal presencial;
	 * @param capitalFixo      Indicador de cálculo com regra da POC.
	 *
	 * @return Taxa NET diária.
	 */
	private static BigDecimal recuperarTaxaNet(final Integer prazoEmDias, final Integer tipoLinhaCredito,
			final CoberturaPrestamistaEnum cobertura, final long idade, final boolean canalPresencial,
			final boolean capitalFixo) {
		if (capitalFixo)
			return BigDecimal.valueOf(0.0000355);

		switch (cobertura) {
		case MNA:
			return BigDecimal.valueOf(0.0000351);
		case IPTA:
			return BigDecimal.valueOf(0.0000023);
		case DI_IFTA:
			return BigDecimal.valueOf(0.0001621);
		default:
			final Object[] args = { ID_TRANSACAO, "tbPrestamistaTaxa", prazoEmDias, tipoLinhaCredito, cobertura.getId(),
					idade, canalPresencial };
//			return funcaoBuscarFaixaReferencia.executar(args);

			return BigDecimal.valueOf(0.0000355);
		}
	}

	/**
	 * Calcula o pagamento de um empréstimo de acordo com pagamentos constantes e
	 * com uma taxa de juros constante.
	 * <p>
	 * <code>
	 * <table>
	 * <tr>
	 * <td>pmt</td>
	 * <td>=</td>
	 * <td><center>vp * (((1 + taxa)<sup>nper</sup> * taxa)</center>
	 * <hr>
	 * <center>((1 + taxa)<sup>nper</sup> - 1))</center></td>
	 * </tr>
	 * </table>
	 * </code>
	 *
	 * @param taxaJuros     A taxa de juros para o empréstimo <code>[taxa]</code>;
	 * @param prazoEmMeses  O número total de pagamentos pelo empréstimo
	 *                      <code>[nper]</code>;
	 * @param valorPresente O valor presente, ou a quantia total agora equivalente a
	 *                      uma série de pagamentos futuros; também conhecido como
	 *                      principal <code>[vp]</code>.
	 *
	 * @return Valor de pagamento mensal do empréstimo <code>[pmt]</code>.
	 *
	 * @see <a href=
	 *      "https://support.microsoft.com/pt-br/office/fun%C3%A7%C3%A3o-pgto-0214da64-9a63-4996-bc20-214433fa6441">Função
	 *      PGTO</a>
	 */
	private static BigDecimal calcularValorParcela(final BigDecimal taxaJuros, final int prazoEmMeses,
			final BigDecimal valorPresente) {
		final BigDecimal dividendo = taxaJuros.add(BigDecimal.ONE).pow(prazoEmMeses).multiply(taxaJuros).setScale(10,
				RoundingMode.FLOOR);
		final BigDecimal divisor = taxaJuros.add(BigDecimal.ONE).pow(prazoEmMeses).subtract(BigDecimal.ONE).setScale(10,
				RoundingMode.FLOOR);

		return valorPresente.multiply(dividendo.divide(divisor, 10, RoundingMode.FLOOR)).setScale(4,
				RoundingMode.FLOOR);
	}

	/**
	 * Calcula a evolução do saldo devedor e retornar o valor consolidado base de
	 * capital segurado.
	 *
	 * @param taxaJuros     A taxa de juros por período <code>[taxa]</code>;
	 * @param prazoEmMeses  O número total de períodos de pagamento em uma anuidade
	 *                      <code>[nper]</code>;
	 * @param valorPresente Valor total correspondente ao valor atual de uma série
	 *                      de pagamentos futuros <code>[vp]</code>;
	 * @param valorParcela  Valor de parcela base;
	 * @param margem        Margem percentual de carregamento.
	 *
	 * @return Valor de saldo devedor acumulado com margem.
	 */
	private static BigDecimal calcularSaldoDevedor(final BigDecimal taxaJuros, final int prazoEmMeses,
			final BigDecimal valorPresente, final BigDecimal valorParcela, final BigDecimal margem) {
		final AtomicReference<BigDecimal> valorSaldoDevedor = new AtomicReference<>(valorPresente);
		final AtomicReference<BigDecimal> valorSaldoDevedorMargem = new AtomicReference<>(
				valorPresente.multiply(BigDecimal.ONE.add(margem)).setScale(4, RoundingMode.FLOOR));

		IntStream.rangeClosed(1, prazoEmMeses).forEach(parcela -> {
			final Triple<BigDecimal, BigDecimal, BigDecimal> saldoDevedor = calcularAmortizacao(taxaJuros, parcela,
					valorSaldoDevedor.get(), valorParcela, margem);

			valorSaldoDevedor.set(saldoDevedor.getMiddle());
			valorSaldoDevedorMargem.getAndAccumulate(saldoDevedor.getRight(), BigDecimal::add);
		});

		return valorSaldoDevedorMargem.get();
	}

	/**
	 * Retorna o pagamento de capital para determinado período de investimento de
	 * acordo com pagamentos constantes e periódicos e uma taxa de juros constante.
	 * <p>
	 * <code>
	 * <table>
	 * <tr>
	 * <td>valorJuros</td>
	 * <td>=</td>
	 * <td>sd * taxa</td>
	 * </tr>
	 * <tr>
	 * <td>valorAmortizacao</td>
	 * <td>=</td>
	 * <td>parc - valorJuros</td>
	 * </tr>
	 * <tr>
	 * <td>valorSaldoDevedorAtualizado</td>
	 * <td>=</td>
	 * <td>sd - valorAmortizacao</td>
	 * </tr>
	 * <tr>
	 * <td>valorSaldoDevedorAtualizadoMargem</td>
	 * <td>=</td>
	 * <td>valorSaldoDevedorAtualizado * p</td>
	 * </tr>
	 * <tr>
	 * <td>valorAntecipacao [vp]</td>
	 * <td>=</td>
	 * <td><center>parc</center>
	 * <hr>
	 * <center>(1 + taxa)<sup>periodo</sup></center></td>
	 * </tr>
	 * </table>
	 * </code>
	 *
	 * @param taxaJuros         A taxa de juros por período <code>[taxa]</code>;
	 * @param parcela           Especifica o período e deve estar entre 1 e nper
	 *                          <code>[periodo]</code>;
	 * @param valorSaldoDevedor Valor total correspondente ao valor atual de uma
	 *                          série de pagamentos futuros <code>[sd]</code>;
	 * @param valorParcela      Valor de parcela base <code>[parc]</code>;
	 * @param margem            Margem percentual de carregamento <code>[p]</code>.
	 *
	 * @return {@link Triple} com o valor de antecipação da parcela
	 *         ({@link Triple#getLeft() à esquerda}), o valor de saldo devedor
	 *         atualizado sem ({@link Triple#getMiddle() ao centro}) e com margem
	 *         ({@link Triple#getRight() à direita}).
	 *
	 * @see <a href=
	 *      "https://support.microsoft.com/pt-br/office/fun%C3%A7%C3%A3o-ppgto-c370d9e3-7749-4ca4-beea-b06c6ac95e1b">Função
	 *      PPGTO</a>
	 * @see <a href=
	 *      "https://support.microsoft.com/pt-br/office/fun%C3%A7%C3%A3o-vp-23879d31-0e02-4321-be01-da16e8168cbd">Função
	 *      VP</a>
	 */
	private static Triple<BigDecimal, BigDecimal, BigDecimal> calcularAmortizacao(final BigDecimal taxaJuros,
			final int parcela, final BigDecimal valorSaldoDevedor, final BigDecimal valorParcela,
			final BigDecimal margem) {
		final BigDecimal valorJuros = valorSaldoDevedor.multiply(taxaJuros).setScale(4, RoundingMode.FLOOR);
		final BigDecimal valorAmortizacao = valorParcela.subtract(valorJuros).setScale(4, RoundingMode.FLOOR);
		final BigDecimal valorSaldoDevedorAtualizado = valorSaldoDevedor.subtract(valorAmortizacao).setScale(4,
				RoundingMode.FLOOR);
		final BigDecimal valorSaldoDevedorAtualizadoMargem = valorSaldoDevedorAtualizado
				.multiply(margem.add(BigDecimal.ONE)).setScale(4, RoundingMode.FLOOR);
		final BigDecimal valorAntecipacao = valorParcela.divide(taxaJuros.add(BigDecimal.ONE).pow(parcela), 4,
				RoundingMode.FLOOR);

		return Triple.of(valorAntecipacao, valorSaldoDevedorAtualizado, valorSaldoDevedorAtualizadoMargem);
	}

	/**
	 * Realiza o cálculo das coberturas a partir de uma taxa e um prêmio NET
	 * previamente calculados.
	 *
	 * @param taxaNet     Taxa NET diária da cobertura [tn];
	 * @param premioNet   Prêmio NET calculado a partir da regra da cobertura;
	 * @param aliquotaIof Alíquota de IOF da cobertura.
	 *
	 * @return Resultado financeiro da cobertura.
	 */
	private static ResultadoFinanceiro calcularCoberturaPrestamista(final BigDecimal taxaNet,
			final BigDecimal premioNet, final BigDecimal aliquotaIof) {
		final BigDecimal premioLiquido = calcularPremioLiquido(premioNet);
		final BigDecimal valorTotalComissao = premioLiquido.subtract(premioNet);
		final BigDecimal premioBruto = calcularPremioBruto(premioLiquido, aliquotaIof);
		final BigDecimal valorIof = premioBruto.subtract(premioLiquido);

		return new CalculoPrestamista().new ResultadoFinanceiro(taxaNet, premioNet, valorTotalComissao, premioLiquido,
				valorIof, premioBruto);
	}

	/**
	 * Calcula o prêmio líquido aplicando o percentual de comissões do produto.
	 *
	 * @param premioNet Valor de prêmio NET.
	 *
	 * @return Valor de prêmio líquido.
	 */
	private static BigDecimal calcularPremioLiquido(final BigDecimal premioNet) {
		final Map<Integer, BigDecimal> mapComissao = new HashMap<>();
		mapComissao.put(1, BigDecimal.valueOf(0.3334)); // Corretagem: 33,34%
		mapComissao.put(5, BigDecimal.valueOf(0.0166)); // Remuneração: 1,66%

		final BigDecimal pcComissao = mapComissao.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

		return premioNet.divide(BigDecimal.ONE.subtract(pcComissao), 4, RoundingMode.FLOOR);
	}

	/**
	 * Calcula o valor de prêmio bruto aplicando o percentual de IOF do produto.
	 *
	 * @param premioLiquido Valor de prêmio líquido;
	 * @param aliquotaIof   Alíquota de IOF da cobertura.
	 *
	 * @return Valor de prêmio bruto.
	 */
	private static BigDecimal calcularPremioBruto(final BigDecimal premioLiquido, final BigDecimal aliquotaIof) {
		return premioLiquido.multiply(aliquotaIof.add(BigDecimal.ONE)).setScale(4, RoundingMode.FLOOR);
	}

	/**
	 * Recupera o último dia do mês de cálculo. Esta deverá ser uma nova função
	 * mapeada no motor para calcular um seguro de emissão mensal.
	 *
	 * @param dataReferencia Data de referência.
	 *
	 * @return Número do último dia do mês da data de referência.
	 */
	private static int recuperarUltimoDiaMes(final LocalDate dataReferencia) {
		return dataReferencia.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
	}

	/**
	 * Sumariza os atributos calculados das coberturas agrupados em uma estrutura de
	 * mapa.
	 *
	 * @param mapCalculoCobertura Mapa com os resultados de cálculo das coberturas.
	 *
	 * @return Resultado financeiro sumarizado.
	 */
	private static ResultadoFinanceiro sumarizarCotacao(
			final Map<CoberturaPrestamistaEnum, ResultadoFinanceiro> mapCalculoCobertura) {
		final BigDecimal premioNetTotal = mapCalculoCobertura.values().stream().map(ResultadoFinanceiro::getPremioNet)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		final BigDecimal premioLiquidoTotal = mapCalculoCobertura.values().stream()
				.map(ResultadoFinanceiro::getPremioLiquido).reduce(BigDecimal.ZERO, BigDecimal::add);
		final BigDecimal valorTotalComissao = mapCalculoCobertura.values().stream()
				.map(ResultadoFinanceiro::getValorTotalComissao).reduce(BigDecimal.ZERO, BigDecimal::add);
		final BigDecimal premioBrutoTotal = mapCalculoCobertura.values().stream()
				.map(ResultadoFinanceiro::getPremioBruto).reduce(BigDecimal.ZERO, BigDecimal::add);
		final BigDecimal valorIofTotal = mapCalculoCobertura.values().stream().map(ResultadoFinanceiro::getValorIof)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new CalculoPrestamista().new ResultadoFinanceiro(valorIofTotal, premioNetTotal, valorTotalComissao,
				premioLiquidoTotal, valorIofTotal, premioBrutoTotal);
	}

	@Getter
	@AllArgsConstructor
	public enum CoberturaPrestamistaEnum {
		// @formatter:off
		MNA    (310, "Morte natural ou acidental"                                           , Boolean.TRUE , Boolean.FALSE, BigDecimal.valueOf(0.0038)),
		IPTA   (311, "Invalidez permanente total por acidente"                              , Boolean.FALSE, Boolean.FALSE, BigDecimal.valueOf(0.0038)),
		DI_IFTA(312, "Desemprego involuntário | Incapacidade física temporária por acidente", Boolean.FALSE, Boolean.TRUE , BigDecimal.valueOf(0.0038));
		// @formatter:on

		private final long id;
		private final String nome;
		private final boolean basica;
		private final boolean temporaria;
		private final BigDecimal aliquotaIof;
	}

	@Getter
	@AllArgsConstructor
	public class ResultadoFinanceiro {
		private final BigDecimal taxaNet;
		private final BigDecimal premioNet;
		private final BigDecimal valorTotalComissao;
		private final BigDecimal premioLiquido;
		private final BigDecimal valorIof;
		private final BigDecimal premioBruto;

		@Override
		public String toString() {
			return "Prêmio NET    : ".concat(FORMATTER.format(premioNet)).concat(System.lineSeparator())
					+ "Comissões     : ".concat(FORMATTER.format(valorTotalComissao)).concat(System.lineSeparator())
					+ "Prêmio líquido: ".concat(FORMATTER.format(premioLiquido)).concat(System.lineSeparator())
					+ "IOF           : ".concat(FORMATTER.format(valorIof)).concat(System.lineSeparator())
					+ "Prêmio bruto  : ".concat(FORMATTER.format(premioBruto));
		}
	}
}