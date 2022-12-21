package br.com.confitec.teste.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FinanceiroUtils {
	private static final BigDecimal UM_CENTAVO = new BigDecimal("0.01");
	private static final String MSG_PARAMETRO_INVALIDO = "exception.parametro.invalido";

	private FinanceiroUtils() {
		super();
	}

	/**
	 * <p>
	 * Subtrai uma lista de valores e retorna seu resultado, caso contrário retorna
	 * zero;
	 *
	 * @param valores
	 * @return
	 */
	public static BigDecimal subtrair(final BigDecimal... valores) {
		if (valores.length < 1)
			throw new IllegalArgumentException(MSG_PARAMETRO_INVALIDO);

		BigDecimal resultante = valores[0];

		for (final BigDecimal valor : extrairFatores(valores))
			resultante = resultante.subtract(valor == null ? BigDecimal.ZERO : valor);

		return resultante;
	}

	private static BigDecimal[] extrairFatores(final BigDecimal[] valores) {
		final BigDecimal[] subtraentes = new BigDecimal[valores.length - 1];

		System.arraycopy(valores, 1, subtraentes, 0, valores.length - 1);

		return subtraentes;
	}

	/**
	 * Realiza o rateio do valor informado, aplicando a diferença na primeira
	 * parcela.
	 *
	 * @param valorRateio
	 * @param quantidadeParcela
	 * @param ultimaParcela     - Última parcela gerada para a proposta
	 * @return Map onde a key corresponde ao número da parcela, e o value
	 *         corresponde ao valor da parcela.
	 */
	public static Map<Integer, BigDecimal> calcularRateioConvencional(final BigDecimal valorParcelaBase,
			final BigDecimal resto, final int quantidadeParcela, final int ultimaParcela) {
		final Map<Integer, BigDecimal> parcelamento = new HashMap<>(quantidadeParcela);
		BigDecimal valorParcela;

		for (int indiceParcela = 1 + ultimaParcela; indiceParcela <= quantidadeParcela
				+ ultimaParcela; indiceParcela++) {
			valorParcela = valorParcelaBase;

			if (indiceParcela == 1 + ultimaParcela)
				valorParcela = valorParcela.add(resto);

			parcelamento.put(indiceParcela, valorParcela);
		}

		return parcelamento;
	}

	/**
	 * Realiza o rateio do valor informado, aplicando a diferença na última parcela.
	 *
	 * @param valorRateio
	 * @param quantidade
	 * @param ultimaParcela - Última parcela gerada para a proposta
	 * @return Map onde a key corresponde ao número da parcela, e o value
	 *         corresponde ao valor da parcela.
	 */
	public static Map<Integer, BigDecimal> calcularRateioConvencionalUltimaParcela(final BigDecimal valorBase,
			final BigDecimal resto, final int quantidade) {
		final Map<Integer, BigDecimal> parcelamento = new HashMap<>(quantidade);
		BigDecimal valorParcela;

		for (int indice = 1; indice <= quantidade; indice++) {
			valorParcela = valorBase;

			if (indice == quantidade)
				valorParcela = valorParcela.add(resto);

			parcelamento.put(indice, valorParcela);
		}

		return parcelamento;
	}

	/**
	 * Realiza o rateio do valor informado, distribuindo a diferença igualmente nas
	 * parcelas.
	 *
	 * @param valorRateio
	 * @param quantidadeParcela
	 * @param ultimaParcela
	 * @return Map onde a key corresponde ao número da parcela, e o value
	 *         corresponde ao valor da parcela.
	 */
	public static Map<Integer, BigDecimal> calcularRateioDistribuido(final BigDecimal valorParcelaBase,
			final BigDecimal resto, final int quantidadeParcela, final int ultimaParcela) {
		final Map<Integer, BigDecimal> parcelamento = new HashMap<>(quantidadeParcela);
		BigDecimal valorParcela;

		for (int indiceParcela = 1 + ultimaParcela; indiceParcela <= quantidadeParcela
				+ ultimaParcela; indiceParcela++) {
			valorParcela = valorParcelaBase;

			if (indiceParcela <= resto.multiply(BigDecimal.valueOf(100)).intValue())
				valorParcela = valorParcela.add(UM_CENTAVO);

			parcelamento.put(indiceParcela, valorParcela);
		}

		return parcelamento;
	}

//	/**
//	 * Método responsável por calcular o valor da primeira e demais parcelas de um
//	 * faturamento. Também utilizado no débito antecipado para calcular o valor de
//	 * comissões e coberturas da primeira parcela.
//	 *
//	 * @param quantidadeParcela
//	 * @param vlParcelamento
//	 * @param vlAdicionalFracionamento
//	 * @return
//	 */
//	// TODO: Refatorar para que o financeiro-faturamento-backend use esse método
//	public static SimulacaoParcelamentoRetornoDto simularParcelamento(final int quantidadeParcela,
//			final BigDecimal vlParcelamento, final BigDecimal vlAdicionalFracionamento) {
//		final SimulacaoParcelamentoRetornoDto simulacaoParcelamento = new SimulacaoParcelamentoRetornoDto(
//				quantidadeParcela);
//
//		final BigDecimal vlParcelamentoTotal = vlAdicionalFracionamento == null ? vlParcelamento
//				: vlParcelamento.multiply(BigDecimal.ONE.add(vlAdicionalFracionamento).pow(quantidadeParcela))
//						.setScale(2, RoundingMode.HALF_UP);
//
//		if (quantidadeParcela == 1)
//			simulacaoParcelamento.setVlPrimeiraParcela(vlParcelamentoTotal);
//		else {
//			final BigDecimal vlDemaisParcelas = vlParcelamentoTotal.divide(BigDecimal.valueOf(quantidadeParcela), 2,
//					RoundingMode.FLOOR);
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
}