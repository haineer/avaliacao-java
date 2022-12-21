package br.com.confitec.teste.domain.rateio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ResultadoRateioCobertura implements Serializable {
	private static final long serialVersionUID = 6103728899428503013L;

	private Map<Integer, BigDecimal> mapRateioPremioLiquido;
	private Map<Integer, BigDecimal> mapRateioIof;
	private Map<Integer, BigDecimal> mapRateioPremioTotal = new HashMap<>();

	public ResultadoRateioCobertura(final Map<Integer, BigDecimal> mapRateioPremioLiquido,
			final Map<Integer, BigDecimal> mapRateioIof) {
		this.mapRateioPremioLiquido = mapRateioPremioLiquido;
		this.mapRateioIof = mapRateioIof;
		atualizarPremioTotal();
	}

	public ResultadoRateioCobertura atualizarPremioTotal() {
		this.mapRateioPremioLiquido.forEach((nrParcela, vlPremioLiquido) -> this.mapRateioPremioTotal.put(nrParcela,
				vlPremioLiquido.add(this.mapRateioIof.get(nrParcela))));
		return this;
	}
}