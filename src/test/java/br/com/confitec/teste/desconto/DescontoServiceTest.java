package br.com.confitec.teste.desconto;

import java.math.BigDecimal;
import java.util.Random;

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

	@Test
	@DisplayName("Sucesso - Parcelamento padrão")
	public void calcularDesconto() {
		final BigDecimal valorCompra = BigDecimal.ZERO
				.add(new BigDecimal(Math.random()).multiply(BigDecimal.valueOf(1000).subtract(BigDecimal.ZERO)));

		final int quantidadeParcelas = new Random().nextInt(6);
	}
}