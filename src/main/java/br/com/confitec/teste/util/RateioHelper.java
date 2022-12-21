package br.com.confitec.teste.util;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.FLOOR;

import java.math.BigDecimal;
import java.util.Map;

import br.com.confitec.teste.domain.ultron.TipoRateioEnum;

public class RateioHelper {

	private RateioHelper() {
		super();
	}

	public static Map<Integer, BigDecimal> calcularRateio(final BigDecimal valorRateio, final int nrQuantidadeParcelas,
			final int ultimaParcela) {
		return calcularRateio(valorRateio, nrQuantidadeParcelas, ultimaParcela,
				TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA);
	}

	/**
	 * Método auxiliar para realizar o rateio de acordo com o tipo desejado.
	 *
	 * @param valorRateio
	 * @param quantidade
	 * @param ultimaParcela
	 * @param tipoRateio
	 * @return
	 */
	public static Map<Integer, BigDecimal> calcularRateio(BigDecimal valorRateio, final int quantidade,
			final int ultimaParcela, final TipoRateioEnum tipoRateio) {
		if (quantidade == 0)
			throw new IllegalArgumentException("Quantidade de parcelas inválida");

		if (valorRateio == null)
			valorRateio = BigDecimal.ZERO;

		final BigDecimal valorParcelaBase = valorRateio.divide(valueOf(quantidade), 2, FLOOR);
		final BigDecimal resto = valorRateio.subtract(valorParcelaBase.multiply(BigDecimal.valueOf(quantidade)));

		if (TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA.equals(tipoRateio))
			return FinanceiroUtils.calcularRateioConvencional(valorParcelaBase, resto, quantidade, ultimaParcela);
		if (TipoRateioEnum.DISTRIBUIDO.equals(tipoRateio))
			return FinanceiroUtils.calcularRateioDistribuido(valorParcelaBase, resto, quantidade, ultimaParcela);
		if (TipoRateioEnum.CONVENCIONAL_ULTIMA_PARCELA.equals(tipoRateio))
			return FinanceiroUtils.calcularRateioConvencionalUltimaParcela(valorParcelaBase, resto, quantidade);

		throw new IllegalArgumentException("Tipo de rateio inválido: " + tipoRateio.getCodigo());
	}
}