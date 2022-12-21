package br.com.confitec.teste.domain.rateio;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlocacaoRateioParcela implements Serializable {
	private static final long serialVersionUID = 3808249432159703484L;

	private BigDecimal vlDistribuicaoPrimeira;
	private BigDecimal vlDistribuicaoDemais;

	public AlocacaoRateioParcela() {
		this.vlDistribuicaoPrimeira = BigDecimal.ZERO;
		this.vlDistribuicaoDemais = BigDecimal.ZERO;
	}
}