package br.com.confitec.teste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.ProfileValueSourceConfiguration;

import br.com.confitec.teste.domain.rateio.CoberturaRateio;
import br.com.confitec.teste.domain.rateio.ResultadoRateio;
import br.com.confitec.teste.domain.rateio.SolicitacaoRateio;
import br.com.confitec.teste.service.impl.RateioServiceImpl;

@SpringBootTest
@ProfileValueSourceConfiguration
public class RateioServiceTest {

	@InjectMocks
	private RateioServiceImpl service;

	@Test
	@DisplayName("Sucesso - Itens pessoais")
	public void executarRateioItensPessoais() {
		final int quantidadeParcela = 12;
		final SolicitacaoRateio request = new SolicitacaoRateio(quantidadeParcela, criarListaCoberturaItensPessoais());
		final ResultadoRateio response = service.executarRateio(request);

		final BigDecimal valorMensal = BigDecimal.valueOf(9.9);
		final BigDecimal valorAnual = valorMensal.multiply(BigDecimal.valueOf(12));

		assertEquals(response.getLsParcela().size(), quantidadeParcela);
		response.getLsParcela().forEach(parcela -> assertTrue(parcela.getVlPremioTotal().compareTo(valorMensal) == 0));
		assertTrue(response.getVlPremioTotal().compareTo(valorAnual) == 0);
	}

	@Test
	@DisplayName("Sucesso - Rateio vida (1 parcela)")
	public void executarRateioVidaUmaParcela() {
		final int qtParcela = 1;
		final SolicitacaoRateio request = new SolicitacaoRateio(qtParcela, criarListaCoberturaVida());
		final ResultadoRateio response = service.executarRateio(request);

		final BigDecimal valorAnual = BigDecimal.valueOf(508);

		assertEquals(response.getLsParcela().size(), qtParcela);
		assertTrue(response.getVlPremioTotal().compareTo(valorAnual) == 0);
	}

	@Test
	@DisplayName("Sucesso - Rateio vida (12 parcelas)")
	public void executarRateioVidaDozeParcelas() {
		final int qtParcela = 12;
		final SolicitacaoRateio request = new SolicitacaoRateio(qtParcela, criarListaCoberturaVida());
		final ResultadoRateio response = service.executarRateio(request);

		final BigDecimal valorAnual = BigDecimal.valueOf(508);

		assertEquals(response.getLsParcela().size(), qtParcela);
		assertTrue(response.getVlPremioTotal().compareTo(valorAnual) == 0);
	}

	private List<CoberturaRateio> criarListaCoberturaItensPessoais() {
		final List<CoberturaRateio> lsCobertura = new ArrayList<>();

		// @formatter:off
		lsCobertura.add(new CoberturaRateio(1, "Bolsa protegida"          , Boolean.TRUE , "0171", BigDecimal.valueOf(48.96), BigDecimal.valueOf(3.61), BigDecimal.valueOf(52.57)));
		lsCobertura.add(new CoberturaRateio(2, "Saque e compra sob coação", Boolean.FALSE, "0171", BigDecimal.valueOf(3.58) , BigDecimal.valueOf(0.26), BigDecimal.valueOf(3.84)));
		lsCobertura.add(new CoberturaRateio(3, "Roubo em caixa eletrônico", Boolean.FALSE, "0171", BigDecimal.valueOf(58.10), BigDecimal.valueOf(4.29), BigDecimal.valueOf(62.39)));
		// @formatter:on

		return lsCobertura;
	}

	private List<CoberturaRateio> criarListaCoberturaVida() {
		final List<CoberturaRateio> lsCobertura = new ArrayList<>();

		// @formatter:off
		lsCobertura.add(new CoberturaRateio(1, "MNA"  , Boolean.TRUE , "1391", BigDecimal.valueOf(106.6) , BigDecimal.valueOf(0.41), BigDecimal.valueOf(107.01)));
		lsCobertura.add(new CoberturaRateio(2, "IPA"  , Boolean.FALSE, "1381", BigDecimal.valueOf(15.94) , BigDecimal.valueOf(0.06), BigDecimal.valueOf(16.00)));
		lsCobertura.add(new CoberturaRateio(3, "AF-T" , Boolean.FALSE, "1329", BigDecimal.valueOf(21.32) , BigDecimal.valueOf(0.08), BigDecimal.valueOf(21.40)));
		lsCobertura.add(new CoberturaRateio(4, "DIH-A", Boolean.FALSE, "1390", BigDecimal.valueOf(51.99) , BigDecimal.valueOf(0.20), BigDecimal.valueOf(52.19)));
		lsCobertura.add(new CoberturaRateio(5, "AF-C" , Boolean.FALSE, "1329", BigDecimal.valueOf(17.03) , BigDecimal.valueOf(0.06), BigDecimal.valueOf(17.09)));
		lsCobertura.add(new CoberturaRateio(6, "AF-F" , Boolean.FALSE, "1329", BigDecimal.valueOf(7.07)  , BigDecimal.valueOf(0.03), BigDecimal.valueOf(7.10)));
		lsCobertura.add(new CoberturaRateio(7, "AF-P" , Boolean.FALSE, "1329", BigDecimal.valueOf(286.12), BigDecimal.valueOf(1.09), BigDecimal.valueOf(287.21)));
		// @formatter:on

		return lsCobertura;
	}
}