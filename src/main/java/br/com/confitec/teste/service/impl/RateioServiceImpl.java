package br.com.confitec.teste.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.confitec.teste.domain.rateio.AlocacaoRateioParcela;
import br.com.confitec.teste.domain.rateio.CoberturaRateio;
import br.com.confitec.teste.domain.rateio.ParcelaRateio;
import br.com.confitec.teste.domain.rateio.ResultadoRateio;
import br.com.confitec.teste.domain.rateio.ResultadoRateioCobertura;
import br.com.confitec.teste.domain.rateio.SolicitacaoRateio;
import br.com.confitec.teste.service.RateioService;

@Service
public class RateioServiceImpl implements RateioService {
	private static final Map<String, BigDecimal> mapRateioIofGrupoRamo = new HashMap<>();
	private static final Map<String, AlocacaoRateioParcela> mapRateioDiferencaIofGrupoRamo = new HashMap<>();

	/**
	 * Calcula o rateio diferenciado para IOF. A regra deste novo cálculo é a
	 * seguinte:
	 * <ol>
	 * <li>Ultron deve inicialmente realizar o arredondamento do valor do IOF e
	 * alocar a diferença na primeira parcela;</li>
	 * <li>Identificar o que, como premissa, vamos chamar de “IOF base”. IOF base é
	 * o valor do IOF por cobertura, dividido pela quantidade de parcelas (truncado
	 * em 2 casas decimais);</li>
	 * <li>Calcular a diferença do valor do IOF da primeira parcela e do IOF base,
	 * considerando os respectivos grupos ramos de contabilização;</li>
	 * <li>Pegar o valor da diferença do IOF da primeira parcela, por grupo ramo e
	 * do IOF base, calculado no passo 3, e dividir pelo número total de
	 * parcelas;</li>
	 * <li>Verificar os valores em que não foi possível alocar em cada parcela, por
	 * grupo ramo, conforme passo 4 e alocá-los na 1ª parcela
	 * <ul>
	 * <li>Os valores de IOF por cobertura, por parcela que forem menores que R$
	 * 0,01 deverão ser alocados contabilmente somados aos valores de outras
	 * coberturas na mesma condição do mesmo grupo ramo, para que se obtenha “valor
	 * cheio”;</li>
	 * <li>Esta alocação deverá respeitar a seguinte ordem:
	 * <ol>
	 * <li>Cobertura básica (se houver);</li>
	 * <li>Se não existir cobertura básica ou se existir mais de uma, alocar na
	 * cobertura de maior valor (maior representatividade monetária).</li>
	 * </ol>
	 * </ul>
	 * </ul>
	 *
	 * @param solicitacao Dados de entrada para cálculo de rateio, contendo
	 *                    coberturas e quantidade de parcelas
	 *
	 * @return Resultado do rateio, com parcelas e totalizadores
	 */
	@Override
	public ResultadoRateio executarRateio(final SolicitacaoRateio solicitacao) {
		mapRateioIofGrupoRamo.clear();
		final Map<CoberturaRateio, ResultadoRateioCobertura> mapRateioCobertura = new HashMap<>(
				solicitacao.getLsCobertura().size());

		solicitacao.getLsCobertura().forEach(cobertura -> mapRateioCobertura.put(cobertura,
				calcularRateioNovo(solicitacao.getQtParcela(), cobertura)));

		calcularAlocacaoParcelaRateioIof(solicitacao.getQtParcela());
		alocarRestoRateioIof(mapRateioCobertura,
				extrairMapRateioBaseIof(solicitacao.getQtParcela(), mapRateioCobertura));

		return criarResultadoRateio(solicitacao.getQtParcela(), mapRateioCobertura);
	}

	private ResultadoRateioCobertura calcularRateioNovo(final int qtParcela, final CoberturaRateio cobertura) {
		final Map<Integer, BigDecimal> mapParcelamentoPremioLiquido = calcularRateioPrimeira(qtParcela,
				cobertura.getVlPremioLiquido(), RoundingMode.HALF_UP);
		final Map<Integer, BigDecimal> mapParcelamentoIof = calcularRateioIof(qtParcela, cobertura.getNrGrupoRamo(),
				cobertura.getVlIof());

		return new ResultadoRateioCobertura(mapParcelamentoPremioLiquido, mapParcelamentoIof);
	}

	private Map<Integer, BigDecimal> calcularRateioPrimeira(final int qtParcela, final BigDecimal vlRateio,
			final RoundingMode tipoArredondamento) {
		final BigDecimal vlParcelaBase = vlRateio.divide(BigDecimal.valueOf(qtParcela), 2, tipoArredondamento);
		final BigDecimal vlResto = vlRateio.subtract(vlParcelaBase.multiply(BigDecimal.valueOf(qtParcela)));

		return calcularRateioPrimeira(vlParcelaBase, vlResto, qtParcela);
	}

	private Map<Integer, BigDecimal> calcularRateioIof(final int qtParcela, final String nrGrupoRamo,
			final BigDecimal vlRateio) {
		final Map<Integer, BigDecimal> mapRateio = calcularRateioPrimeira(qtParcela, vlRateio, RoundingMode.FLOOR);
		final BigDecimal vlBaseIof = vlRateio.divide(BigDecimal.valueOf(qtParcela), 2, RoundingMode.FLOOR);

		calcularDiferencaRateioIof(nrGrupoRamo, mapRateio, vlBaseIof);

		return mapRateio;
	}

	private Map<Integer, BigDecimal> calcularRateioPrimeira(final BigDecimal vlParcelaBase, final BigDecimal vlResto,
			final int qtParcela) {
		final Map<Integer, BigDecimal> mapRateio = new HashMap<>(qtParcela);
		BigDecimal vlParcela;

		for (int nrParcela = 1; nrParcela <= qtParcela; nrParcela++) {
			vlParcela = vlParcelaBase;

			if (nrParcela == 1)
				vlParcela = vlParcela.add(vlResto);

			mapRateio.put(nrParcela, vlParcela);
		}

		return mapRateio;
	}

	private Map<Integer, BigDecimal> calcularSimulacaoParcelamento(final BigDecimal vlParcelaBase,
			final BigDecimal vlResto, final int qtParcela) {
		final Map<Integer, BigDecimal> mapRateio = new HashMap<>(qtParcela);
		BigDecimal vlParcela;

		for (int nrParcela = 1; nrParcela <= qtParcela; nrParcela++) {
			vlParcela = vlParcelaBase;

			if (nrParcela == 1)
				vlParcela = vlParcela.add(vlResto);

			mapRateio.put(nrParcela, vlParcela);
		}

		return mapRateio;
	}

	private void calcularDiferencaRateioIof(final String nrGrupoRamo, final Map<Integer, BigDecimal> mapRateio,
			final BigDecimal vlBaseIof) {
		final int nrParcela = 1;
		final BigDecimal vlDiferenca = mapRateio.get(nrParcela).subtract(vlBaseIof);

		if (mapRateioIofGrupoRamo.containsKey(nrGrupoRamo))
			mapRateioIofGrupoRamo.put(nrGrupoRamo, mapRateioIofGrupoRamo.get(nrGrupoRamo).add(vlDiferenca));
		else
			mapRateioIofGrupoRamo.put(nrGrupoRamo, vlDiferenca);
	}

	private void calcularAlocacaoParcelaRateioIof(final int qtParcela) {
		mapRateioDiferencaIofGrupoRamo.clear();

		mapRateioIofGrupoRamo.forEach((nrGrupoRamo, vlDiferenca) -> {
			final BigDecimal vlDiferencaDemaisParcelas = vlDiferenca.divide(BigDecimal.valueOf(qtParcela), 2,
					RoundingMode.FLOOR);
			final BigDecimal vlDiferencaPrimeiraParcela = vlDiferenca
					.subtract(vlDiferencaDemaisParcelas.multiply(BigDecimal.valueOf(qtParcela)));

			mapRateioDiferencaIofGrupoRamo.put(nrGrupoRamo,
					new AlocacaoRateioParcela(vlDiferencaPrimeiraParcela, vlDiferencaDemaisParcelas));
		});
	}

	private void alocarRestoRateioIof(final Map<CoberturaRateio, ResultadoRateioCobertura> result,
			final Map<CoberturaRateio, BigDecimal> mapRateioIof) {
		final List<CoberturaRateio> lsCoberturaOrdenada = new ArrayList<>(result.keySet());
		Collections.sort(lsCoberturaOrdenada);

		lsCoberturaOrdenada.forEach(cobertura -> {
			final ResultadoRateioCobertura rateioCobertura = result.get(cobertura);
			final AlocacaoRateioParcela alocacaoRateio = mapRateioDiferencaIofGrupoRamo.containsKey(
					cobertura.getNrGrupoRamo()) ? mapRateioDiferencaIofGrupoRamo.remove(cobertura.getNrGrupoRamo())
							: new AlocacaoRateioParcela();

			for (final Map.Entry<Integer, BigDecimal> parcela : rateioCobertura.getMapRateioIof().entrySet()) {
				BigDecimal vlIof;
				if (parcela.getKey() == 1)
					vlIof = mapRateioIof.get(cobertura).add(alocacaoRateio.getVlDistribuicaoPrimeira())
							.add(alocacaoRateio.getVlDistribuicaoDemais());
				else
					vlIof = mapRateioIof.get(cobertura).add(alocacaoRateio.getVlDistribuicaoDemais());

				rateioCobertura.getMapRateioIof().put(parcela.getKey(), vlIof);
			}

			result.put(cobertura, rateioCobertura.atualizarPremioTotal());
		});
	}

	private Map<CoberturaRateio, BigDecimal> extrairMapRateioBaseIof(final int qtParcela,
			final Map<CoberturaRateio, ResultadoRateioCobertura> mapRateioCobertura) {
		final Map<CoberturaRateio, BigDecimal> mapRateioIof = new HashMap<>();
		final int nrParcela = qtParcela > 1 ? 2 : 1;

		mapRateioCobertura
				.forEach((cobertura, rateio) -> mapRateioIof.put(cobertura, rateio.getMapRateioIof().get(nrParcela)));

		return mapRateioIof;
	}

	private ResultadoRateio criarResultadoRateio(final int qtParcela,
			final Map<CoberturaRateio, ResultadoRateioCobertura> mapRateioCobertura) {
		final List<ParcelaRateio> lsParcela = new ArrayList<>();

		for (int nrParcela = 1; nrParcela <= qtParcela; nrParcela++) {
			BigDecimal vlPremioLiquido = BigDecimal.ZERO;
			BigDecimal vlIof = BigDecimal.ZERO;

			for (final ResultadoRateioCobertura resultadoRateio : mapRateioCobertura.values()) {
				vlPremioLiquido = vlPremioLiquido.add(resultadoRateio.getMapRateioPremioLiquido().get(nrParcela));
				vlIof = vlIof.add(resultadoRateio.getMapRateioIof().get(nrParcela));
			}

			lsParcela.add(new ParcelaRateio(nrParcela, vlPremioLiquido, vlIof, mapRateioCobertura));
		}

		return new ResultadoRateio(lsParcela);
	}

//	private Map<String, SimulacaoParcelamentoRetornoDto> mapearParcelamento(final Integer quantidadeParcela,
//			final DadoMonetarioConsultaDto dadoMonetario) {
//		final List<DadoMonetarioComissaoConsultaDto> lsComissao = dadoMonetario.getLsComissao();
//		final Map<String, SimulacaoParcelamentoRetornoDto> mapParcelamento = new HashMap<>();
//
//		mapParcelamento.put(ComposicaoFaturamentoConstants.PREMIO_NET,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlPremioNet()));
//		mapParcelamento.put(ComposicaoFaturamentoConstants.PREMIO_TARIFA,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlPremioTarifa()));
//		mapParcelamento.put(ComposicaoFaturamentoConstants.DESCONTO,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlTotalDesconto()));
//		mapParcelamento.put(ComposicaoFaturamentoConstants.ADICIONAL_FRACIONAMENTO,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlAdicionalFracionamento()));
//		mapParcelamento.put(ComposicaoFaturamentoConstants.IOF,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlIof()));
//		mapParcelamento.put(ComposicaoFaturamentoConstants.PREMIO_TOTAL,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlPremioTotal()));
//
//		mapParcelamento.put(ComposicaoFaturamentoConstants.PREMIO_LIQUIDO,
//				simularParcelamento(quantidadeParcela, FinanceiroUtils.subtrair(dadoMonetario.getVlPremioTotal(),
//						dadoMonetario.getVlAdicionalFracionamento(), dadoMonetario.getVlIof())));
//
//		lsComissao.forEach(comissao -> {
//			final TipoComissaoEnum tipoComissao = TipoComissaoEnum.parse(comissao.getNrTipoComissao());
//
//			mapParcelamento.put(tipoComissao.getNomeAtributo(),
//					simularParcelamento(quantidadeParcela, comissao.getVlComissao()));
//		});
//
//		return mapParcelamento;
//	}
//
//	private Map<String, SimulacaoParcelamentoRetornoDto> mapearParcelamentoItensPessoais(
//			final Integer quantidadeParcela, final DadoMonetarioConsultaDto dadoMonetario) {
//		final List<DadoMonetarioComissaoConsultaDto> lsComissao = dadoMonetario.getLsComissao();
//		final Map<String, SimulacaoParcelamentoRetornoDto> mapParcelamento = new HashMap<>();
//
//		mapParcelamento.put(ComposicaoFaturamentoConstants.PREMIO_NET,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlPremioNet()), RoundingMode.HALF_UP);
//		mapParcelamento.put(ComposicaoFaturamentoConstants.PREMIO_TARIFA,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlPremioTarifa()), RoundingMode.HALF_UP);
//		mapParcelamento.put(ComposicaoFaturamentoConstants.DESCONTO,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlTotalDesconto()), RoundingMode.HALF_UP);
//		mapParcelamento.put(ComposicaoFaturamentoConstants.ADICIONAL_FRACIONAMENTO,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlAdicionalFracionamento()),
//				RoundingMode.HALF_UP);
//		mapParcelamento.put(ComposicaoFaturamentoConstants.IOF,
//				simularParcelamentoIof(quantidadeParcela, dadoMonetario.getVlIof()));
//		mapParcelamento.put(ComposicaoFaturamentoConstants.PREMIO_TOTAL,
//				simularParcelamento(quantidadeParcela, dadoMonetario.getVlPremioTotal()));
//
//		mapParcelamento.put(ComposicaoFaturamentoConstants.PREMIO_LIQUIDO,
//				simularParcelamento(quantidadeParcela, FinanceiroUtils.subtrair(dadoMonetario.getVlPremioTotal(),
//						dadoMonetario.getVlAdicionalFracionamento(), dadoMonetario.getVlIof())));
//
//		lsComissao.forEach(comissao -> {
//			final TipoComissaoEnum tipoComissao = TipoComissaoEnum.parse(comissao.getNrTipoComissao());
//
//			mapParcelamento.put(tipoComissao.getNomeAtributo(),
//					simularParcelamento(quantidadeParcela, comissao.getVlComissao()));
//		});
//
//		return mapParcelamento;
//	}
//
//	private SimulacaoParcelamentoRetornoDto simularParcelamento(final int quantidadeParcela,
//			final BigDecimal vlParcelamento, final RoundingMode tipoArredondamento) {
//		return simularParcelamento(quantidadeParcela, vlParcelamento, BigDecimal.ZERO, tipoArredondamento);
//	}
//
//	private SimulacaoParcelamentoRetornoDto simularParcelamentoIof(final int qtParcela, final String nrGrupoRamo,
//			final BigDecimal vlRateio) {
//		final Map<Integer, BigDecimal> mapRateio = calcularRateioConvencional(qtParcela, vlRateio);
//		final BigDecimal vlBaseIof = vlRateio.divide(BigDecimal.valueOf(qtParcela), 2, RoundingMode.FLOOR);
//
//		calcularDiferencaRateioIof(nrGrupoRamo, mapRateio, vlBaseIof);
//
//		return mapRateio;
//	}
//
//	private SimulacaoParcelamentoRetornoDto simularParcelamento(final int quantidadeParcela,
//			final BigDecimal vlParcelamento, final BigDecimal vlAdicionalFracionamento,
//			final RoundingMode tipoArredondamento) {
//		final SimulacaoParcelamentoRetornoDto simulacaoParcelamento = new SimulacaoParcelamentoRetornoDto(
//				quantidadeParcela);
//		final BigDecimal vlParcelamentoTotal = calcularValorParcelamento(quantidadeParcela, vlParcelamento,
//				vlAdicionalFracionamento);
//
//		if (quantidadeParcela == 1)
//			simulacaoParcelamento.setVlPrimeiraParcela(vlParcelamentoTotal);
//		else {
//			final BigDecimal vlDemaisParcelas = vlParcelamentoTotal.divide(BigDecimal.valueOf(quantidadeParcela), 2,
//					tipoArredondamento);
//			final BigDecimal vlPrimeiraParcela = vlParcelamentoTotal
//					.subtract(vlDemaisParcelas.multiply(new BigDecimal(quantidadeParcela - 1)));
//
//			simulacaoParcelamento.setVlPrimeiraParcela(vlPrimeiraParcela);
//			simulacaoParcelamento.setVlDemaisParcelas(vlDemaisParcelas);
//		}
//
//		simulacaoParcelamento.setVlParcelamentoTotal(vlParcelamentoTotal);
//
//		return simulacaoParcelamento;
//	}
//
//	private BigDecimal calcularValorParcelamento(final int quantidadeParcela, final BigDecimal vlParcelamento,
//			final BigDecimal vlAdicionalFracionamento) {
//		return vlAdicionalFracionamento.compareTo(BigDecimal.ZERO) == 0 ? vlParcelamento
//				: vlParcelamento.multiply(BigDecimal.ONE.add(vlAdicionalFracionamento).pow(quantidadeParcela))
//						.setScale(2, RoundingMode.HALF_UP);
//	}
}