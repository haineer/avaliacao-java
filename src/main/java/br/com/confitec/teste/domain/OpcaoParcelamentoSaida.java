package br.com.confitec.teste.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class OpcaoParcelamentoSaida implements Serializable {
	private static final long serialVersionUID = -6082804209861464324L;

	private int quantidadeParcelas;
	private BigDecimal valorPrimeiraParcela;
	private BigDecimal valorDemaisParcelas;
	private BigDecimal valorParcelamentoTotal;

	/**
	 * Construtor utilizado quando a quantidade de parcelas é igual a 1.
	 *
	 * @param valorParcelamentoTotal
	 */
	public OpcaoParcelamentoSaida(final BigDecimal valorParcelamentoTotal) {
		this.quantidadeParcelas = 1;
		this.valorPrimeiraParcela = valorParcelamentoTotal;
		this.valorParcelamentoTotal = valorParcelamentoTotal;
	}
}