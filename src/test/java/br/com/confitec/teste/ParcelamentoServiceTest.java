package br.com.confitec.teste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.ProfileValueSourceConfiguration;

import br.com.confitec.teste.domain.Cobertura;
import br.com.confitec.teste.domain.OpcaoParcelamentoEntrada;
import br.com.confitec.teste.domain.ParcelamentoEntrada;
import br.com.confitec.teste.domain.ParcelamentoSaida;
import br.com.confitec.teste.service.impl.ParcelamentoServiceImpl;

@SpringBootTest
@ProfileValueSourceConfiguration
public class ParcelamentoServiceTest {

	@InjectMocks
	private ParcelamentoServiceImpl service;

	@Test
	public void parcelar() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));
		listCobertura.add(new Cobertura(2, BigDecimal.valueOf(345.45)));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(1, 6));
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(7, 9, 0.01));
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(10, 12, 0.03));

		final ParcelamentoSaida result = service
				.parcelar(new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento));

		assertEquals(12, result.getDados().size());
	}

	@Test
	public void parcelarUmaOpcao() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));
		listCobertura.add(new Cobertura(2, BigDecimal.valueOf(345.45)));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(1, 1, 0));

		final ParcelamentoSaida result = service
				.parcelar(new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento));

		assertEquals(1, result.getDados().size());
	}

	@Test
	public void parcelarQuantidadeMinimaMaiorQueMaxima() {
		final List<Cobertura> listCobertura = new ArrayList<>();
		listCobertura.add(new Cobertura(1, BigDecimal.valueOf(123.12)));
		listCobertura.add(new Cobertura(2, BigDecimal.valueOf(345.45)));

		final List<OpcaoParcelamentoEntrada> listOpcaoParcelamento = new ArrayList<>();
		listOpcaoParcelamento.add(new OpcaoParcelamentoEntrada(2, 1, 0));

		assertThrows(IllegalArgumentException.class,
				() -> service.parcelar(new ParcelamentoEntrada(listCobertura, listOpcaoParcelamento)));
	}
}