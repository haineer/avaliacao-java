package br.com.confitec.teste.domain.rateio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ParcelaRateio implements Serializable {
	private static final long serialVersionUID = -383146210142108212L;

	private int nrParcela;

	private BigDecimal vlPremioLiquido;
	private BigDecimal vlIof;
	private BigDecimal vlPremioTotal;

	private Map<CoberturaRateio, ResultadoFinanceiroCobertura> mapCobertura = new HashMap<>();

	public ParcelaRateio(final int nrParcela, final BigDecimal vlPremioLiquido, final BigDecimal vlIof,
			final Map<CoberturaRateio, ResultadoRateioCobertura> mapCobertura) {
		this.nrParcela = nrParcela;
		this.vlPremioLiquido = vlPremioLiquido;
		this.vlIof = vlIof;
		this.vlPremioTotal = vlPremioLiquido.add(vlIof);
		preencherComposicaoCobertura(mapCobertura);
	}

	private void preencherComposicaoCobertura(final Map<CoberturaRateio, ResultadoRateioCobertura> mapCobertura) {
		mapCobertura.forEach((cobertura, rateio) -> {
			this.mapCobertura.put(cobertura,
					new ResultadoFinanceiroCobertura(rateio.getMapRateioPremioLiquido().get(nrParcela),
							rateio.getMapRateioIof().get(nrParcela), rateio.getMapRateioPremioTotal().get(nrParcela)));
		});
	}
}