package br.com.confitec.teste.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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

	private double juros = 0d;
}