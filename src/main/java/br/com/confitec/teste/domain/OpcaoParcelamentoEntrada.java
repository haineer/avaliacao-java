package br.com.confitec.teste.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OpcaoParcelamentoEntrada implements Serializable {
	private static final long serialVersionUID = -6082804209861464324L;

	@NotNull
	@Positive
	private int quantidadeMinimaParcelas;

	@NotNull
	@Positive
	private int quantidadeMaximaParcelas;

	@PositiveOrZero
	private double juros = 0d;

	/**
	 * Construtor utilizado quando não há incidência de juros na opção de
	 * parcelamento.
	 *
	 * @param quantidadeMinimaParcelas
	 * @param quantidadeMaximaParcelas
	 */
	public OpcaoParcelamentoEntrada(final int quantidadeMinimaParcelas, final int quantidadeMaximaParcelas) {
		this.quantidadeMinimaParcelas = quantidadeMinimaParcelas;
		this.quantidadeMaximaParcelas = quantidadeMaximaParcelas;
	}
}