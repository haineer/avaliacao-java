package br.com.confitec.teste;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.confitec.teste.domain.Cobertura;
import br.com.confitec.teste.domain.OpcaoParcelamentoEntrada;
import br.com.confitec.teste.domain.OpcaoParcelamentoSaida;
import br.com.confitec.teste.domain.ParcelamentoEntrada;
import br.com.confitec.teste.domain.ParcelamentoSaida;
import br.com.confitec.teste.service.ParcelamentoService;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParcelamentoControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private ParcelamentoService service;

	private static final String URL = "/confitec/teste/parcelamento";

	@Test
	@DisplayName("Sucesso - Parcelamento OK")
	void parcelamentoSucesso() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));
		listCobertura.add(new Cobertura(2, BigDecimal.valueOf(345.45)));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(1, 6));
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(7, 9, 0.01));
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(10, 12, 0.02));

		Mockito.when(service.parcelar(Mockito.any(ParcelamentoEntrada.class))).thenReturn(getParcelamentoExemplo());

		final ResponseEntity<ParcelamentoSaida> response = restTemplate.postForEntity(URL,
				new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento), ParcelamentoSaida.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@DisplayName("Erro - Parcelamento sem cobertura")
	void parcelamentoSemCobertura() {
		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(1, 12));

		final ResponseEntity<ParcelamentoSaida> response = restTemplate.postForEntity(URL,
				new ParcelamentoEntrada(Collections.emptyList(), listOpcaoParcelamento), ParcelamentoSaida.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Erro - Parcelamento com valor de cobertura nulo")
	void parcelamentoValorCoberturaNulo() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));
		listCobertura.add(new Cobertura(2, null));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(1, 12));

		final ResponseEntity<ParcelamentoSaida> response = restTemplate.postForEntity(URL,
				new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento), ParcelamentoSaida.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Erro - Parcelamento com valor de cobertura zerado")
	void parcelamentoValorCoberturaZerado() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));
		listCobertura.add(new Cobertura(2, BigDecimal.ZERO));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(1, 12));

		final ResponseEntity<ParcelamentoSaida> response = restTemplate.postForEntity(URL,
				new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento), ParcelamentoSaida.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Erro - Parcelamento com valor de cobertura negativo")
	void parcelamentoValorCoberturaNegativo() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(-123.12)));
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(345.45)));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(1, 12));

		final ResponseEntity<ParcelamentoSaida> response = restTemplate.postForEntity(URL,
				new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento), ParcelamentoSaida.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Erro - Parcelamento sem opções")
	void parcelamentoOpcaoVazia() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));

		final ResponseEntity<ParcelamentoSaida> response = restTemplate.postForEntity(URL,
				new ParcelamentoEntrada(listCobertura, Collections.emptyList()), ParcelamentoSaida.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Erro - Parcelamento com quantidade mínima de parcelas zerada")
	void parcelamentoOpcaoMinimoZerado() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(0, 12));

		final ResponseEntity<ParcelamentoSaida> response = restTemplate.postForEntity(URL,
				new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento), ParcelamentoSaida.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Erro - Parcelamento com quantidade máxima de parcelas zerada")
	void parcelamentoOpcaoMaximoZerado() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(1, 0));

		final ResponseEntity<ParcelamentoSaida> response = restTemplate.postForEntity(URL,
				new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento), ParcelamentoSaida.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Erro - Parcelamento com juros negativo")
	void parcelamentoOpcaoJurosNegativo() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(1, 12, -0.01));

		final ResponseEntity<ParcelamentoSaida> response = restTemplate.postForEntity(URL,
				new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento), ParcelamentoSaida.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private ParcelamentoSaida getParcelamentoExemplo() {
		final List<OpcaoParcelamentoSaida> listOpcaoParcelamento = new ArrayList<>();

		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(BigDecimal.valueOf(468.57)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(2, BigDecimal.valueOf(234.29), BigDecimal.valueOf(234.28),
				BigDecimal.valueOf(468.57)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(3, BigDecimal.valueOf(156.19), BigDecimal.valueOf(156.19),
				BigDecimal.valueOf(468.57)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(4, BigDecimal.valueOf(117.15), BigDecimal.valueOf(117.14),
				BigDecimal.valueOf(468.57)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(5, BigDecimal.valueOf(93.73), BigDecimal.valueOf(93.71),
				BigDecimal.valueOf(468.57)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(6, BigDecimal.valueOf(78.12), BigDecimal.valueOf(78.09),
				BigDecimal.valueOf(468.57)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(7, BigDecimal.valueOf(71.81), BigDecimal.valueOf(71.76),
				BigDecimal.valueOf(502.37)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(8, BigDecimal.valueOf(63.45), BigDecimal.valueOf(63.42),
				BigDecimal.valueOf(507.39)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(9, BigDecimal.valueOf(56.95), BigDecimal.valueOf(56.94),
				BigDecimal.valueOf(512.47)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(10, BigDecimal.valueOf(57.19), BigDecimal.valueOf(57.11),
				BigDecimal.valueOf(571.18)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(11, BigDecimal.valueOf(53.01), BigDecimal.valueOf(52.96),
				BigDecimal.valueOf(582.61)));
		listOpcaoParcelamento.add(new OpcaoParcelamentoSaida(12, BigDecimal.valueOf(49.54), BigDecimal.valueOf(49.52),
				BigDecimal.valueOf(594.26)));

		return new ParcelamentoSaida(listOpcaoParcelamento);
	}
}