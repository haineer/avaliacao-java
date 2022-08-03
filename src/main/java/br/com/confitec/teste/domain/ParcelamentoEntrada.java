package br.com.confitec.teste.domain;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParcelamentoEntrada implements Serializable {
	private static final long serialVersionUID = -4902582066244479920L;

	@Valid
	@NotEmpty
	private List<Cobertura> listCobertura;

	@Valid
	@NotEmpty
	private List<OpcaoParcelamentoEntrada> listOpcaoParcelamento;
}