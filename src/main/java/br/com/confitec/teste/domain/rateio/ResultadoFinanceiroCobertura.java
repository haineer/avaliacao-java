package br.com.confitec.teste.domain.rateio;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadoFinanceiroCobertura implements Serializable {
	private static final long serialVersionUID = 5921861548174672746L;

	private BigDecimal vlPremioLiquido;
	private BigDecimal vlIof;
	private BigDecimal vlPremioTotal;
}