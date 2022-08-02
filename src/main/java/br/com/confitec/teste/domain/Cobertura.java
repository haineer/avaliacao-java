package br.com.confitec.teste.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Cobertura implements Serializable {
	private static final long serialVersionUID = 5471298120683095697L;

	@NotNull
	private int cobertura;

	@NotNull
	@Positive
	private BigDecimal valor;
}