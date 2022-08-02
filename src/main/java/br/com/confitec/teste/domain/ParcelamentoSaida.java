package br.com.confitec.teste.domain;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParcelamentoSaida implements Serializable {
	private static final long serialVersionUID = 8023409366542368131L;

	private final List<OpcaoParcelamentoSaida> dados;
}