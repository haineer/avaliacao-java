package br.com.confitec.teste.desconto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.ProfileValueSourceConfiguration;

import br.com.confitec.teste.service.impl.DescontoServiceImpl;

@SpringBootTest
@ProfileValueSourceConfiguration
public class DescontoServiceTest {

	@InjectMocks
	private DescontoServiceImpl service;

	private final Map<Integer, Double> mapParcelamento = Map.of(1, 0.9, 2, 0.92, 3, 0.94, 4, 0.96, 5, 0.98);

	@Test
	@DisplayName("Sucesso - Cálculo aleatório de desconto")
	public void calcularDesconto() {
		final BigDecimal valorCompra = BigDecimal.valueOf(100);
		final int quantidadeParcelas = ThreadLocalRandom.current().nextInt(1, 7);

		final BigDecimal valorFinal = service.calcularDesconto(valorCompra, quantidadeParcelas);

		Assertions.assertEquals(valorFinal.divide(valorCompra, RoundingMode.HALF_UP)
				.compareTo(BigDecimal.valueOf(mapParcelamento.getOrDefault(quantidadeParcelas, 1d))), 0);
	}
}