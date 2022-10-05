package br.com.confitec.teste.domain;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Parcelamento entrada", description = "Informações de parcelamento (entrada)")
public class ParcelamentoEntrada implements Serializable {
	private static final long serialVersionUID = -4902582066244479920L;

	@Valid
	@NotEmpty
	@Schema(required = true, description = "Lista de coberturas para cálculo")
	private List<Cobertura> listCobertura;

	@Valid
	@NotEmpty
	@Schema(required = true, description = "Lista de opções de parcelamento para cálculo")
	private List<OpcaoParcelamentoEntrada> listOpcaoParcelamento;
}