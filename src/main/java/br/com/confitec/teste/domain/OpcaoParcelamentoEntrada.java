package br.com.confitec.teste.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Opção parcelamento entrada", description = "Informações da opção de parcelamento (entrada)")
public class OpcaoParcelamentoEntrada implements Serializable {
	private static final long serialVersionUID = -6082804209861464324L;

	@NotNull
	@Positive
	@Schema(required = true, description = "Quantidade mínima de parcelas (positivas)", example = "1")
	private int quantidadeMinimaParcelas;

	@NotNull
	@Positive
	@Schema(required = true, description = "Quantidade máxima de parcelas (positivas)", example = "99")
	private int quantidadeMaximaParcelas;

	@PositiveOrZero
	@Schema(required = true, description = "Valor de juros (positivo ou zero)", example = "0")
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