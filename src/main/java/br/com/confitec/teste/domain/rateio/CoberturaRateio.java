package br.com.confitec.teste.domain.rateio;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoberturaRateio implements Serializable, Comparable<CoberturaRateio> {
	private static final long serialVersionUID = -1971357587457266706L;

	private int nrCobertura;
	private String nmCobertura;
	private boolean inCoberturaBasica;
	private String nrGrupoRamo;

	@Positive
	private BigDecimal vlPremioLiquido;
	@PositiveOrZero
	private BigDecimal vlIof;
	@Positive
	private BigDecimal vlPremioTotal;

	@Override
	public int compareTo(final CoberturaRateio cobertura) {
		if (cobertura.isInCoberturaBasica())
			return 1;
		return cobertura.getVlPremioLiquido().compareTo(vlPremioLiquido);
	}

	@Override
	public String toString() {
		return nmCobertura + " - " + nrGrupoRamo;
	}
}