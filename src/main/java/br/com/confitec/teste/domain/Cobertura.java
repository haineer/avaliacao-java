package br.com.confitec.teste.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
@Schema(name = "Cobertura", description = "Informações de cobertura")
public class Cobertura implements Serializable {
	private static final long serialVersionUID = 5471298120683095697L;

	@NotNull
	@Schema(required = true, description = "Identificador da cobertura", example = "1")
	private int cobertura;

	@NotNull
	@Positive
	@Schema(required = true, description = "Valor da cobertura", example = "100.00")
	private BigDecimal valor;
}