package br.com.confitec.teste.domain;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Parcelamento Saída", description = "Lista de informações calculadas para parcelamento")
public class ParcelamentoSaida implements Serializable {
	private static final long serialVersionUID = 8023409366542368131L;

	@Schema(required = false, description = "Lista de informações calculadas para parcelamento")
	private List<OpcaoParcelamentoSaida> dados;
}