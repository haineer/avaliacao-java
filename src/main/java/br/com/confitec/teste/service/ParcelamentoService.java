package br.com.confitec.teste.service;

import br.com.confitec.teste.domain.ParcelamentoEntrada;
import br.com.confitec.teste.domain.ParcelamentoSaida;

public interface ParcelamentoService {

	/**
	 * Realiza o parcelamento da soma das coberturas informadas de acordo com os
	 * planos de parcelamento solicitados.
	 *
	 * @param entrada
	 * @return
	 */
	ParcelamentoSaida parcelar(final ParcelamentoEntrada entrada);
}