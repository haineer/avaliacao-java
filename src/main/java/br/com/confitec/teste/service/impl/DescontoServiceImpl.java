package br.com.confitec.teste.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.confitec.teste.service.DescontoService;

@Service
public class DescontoServiceImpl implements DescontoService {

	@Override
	public BigDecimal calcularDesconto(final BigDecimal valorCompra, final int quantidadeParcela) {
		return valorCompra
				.multiply(BigDecimal.valueOf(
						Map.of(1, 0.9, 2, 0.92, 3, 0.94, 4, 0.96, 5, 0.98).getOrDefault(quantidadeParcela, 1d)))
				.setScale(2, RoundingMode.HALF_UP);
	}
}