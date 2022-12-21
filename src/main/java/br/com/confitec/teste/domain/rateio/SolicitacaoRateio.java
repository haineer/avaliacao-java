package br.com.confitec.teste.domain.rateio;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolicitacaoRateio implements Serializable {
	private static final long serialVersionUID = 5921861548174672746L;

	@Positive
	private int qtParcela;

	@NotEmpty
	private List<CoberturaRateio> lsCobertura;
}