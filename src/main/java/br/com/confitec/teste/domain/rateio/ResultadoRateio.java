package br.com.confitec.teste.domain.rateio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ResultadoRateio implements Serializable {
	private static final long serialVersionUID = 4132337200638068743L;

	private int qtParcela;

	private BigDecimal vlPremioLiquido;
	private BigDecimal vlIof;
	private BigDecimal vlPremioTotal;

	private List<ParcelaRateio> lsParcela;

	public ResultadoRateio(final List<ParcelaRateio> lsParcela) {
		this.qtParcela = lsParcela.size();

		this.vlPremioLiquido = lsParcela.stream().map(ParcelaRateio::getVlPremioLiquido).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		this.vlIof = lsParcela.stream().map(ParcelaRateio::getVlIof).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.vlPremioTotal = lsParcela.stream().map(ParcelaRateio::getVlPremioTotal).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		this.lsParcela = lsParcela;
	}
}