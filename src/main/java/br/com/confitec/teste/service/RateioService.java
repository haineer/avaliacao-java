package br.com.confitec.teste.service;

import br.com.confitec.teste.domain.rateio.ResultadoRateio;
import br.com.confitec.teste.domain.rateio.SolicitacaoRateio;

public interface RateioService {

	ResultadoRateio executarRateio(final SolicitacaoRateio solicitacao);
}