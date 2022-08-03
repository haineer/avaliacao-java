package br.com.confitec.teste.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import br.com.confitec.teste.domain.Cobertura;
import br.com.confitec.teste.domain.OpcaoParcelamentoEntrada;
import br.com.confitec.teste.domain.OpcaoParcelamentoSaida;
import br.com.confitec.teste.domain.ParcelamentoEntrada;
import br.com.confitec.teste.domain.ParcelamentoSaida;
import br.com.confitec.teste.service.ParcelamentoService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ParcelamentoServiceImpl implements ParcelamentoService {

	@Override
	public ParcelamentoSaida parcelar(final ParcelamentoEntrada entrada) {
		log.debug("ParcelamentoServiceImpl#parcelar | entrada: {}", entrada);

		final List<OpcaoParcelamentoSaida> saida = new ArrayList<>();
		final BigDecimal subTotal = sumarizarValorCoberturas(entrada.getListCobertura());

		entrada.getListOpcaoParcelamento().forEach(opcaoParcelamento -> {
			validarParcelamento(opcaoParcelamento);

			IntStream.rangeClosed(opcaoParcelamento.getQuantidadeMinimaParcelas(),
					opcaoParcelamento.getQuantidadeMaximaParcelas()).forEach(quantidadeParcelas -> {
						final BigDecimal valorTotal = calcularJuros(subTotal, quantidadeParcelas,
								opcaoParcelamento.getJuros());

						saida.add(parcelar(valorTotal, quantidadeParcelas));
					});
		});

		return new ParcelamentoSaida(saida);
	}

	private OpcaoParcelamentoSaida parcelar(final BigDecimal valorTotal, final int quantidadeParcelas) {
		if (quantidadeParcelas == 1)
			return new OpcaoParcelamentoSaida(valorTotal);

		final BigDecimal valorParcelaBase = valorTotal.divide(BigDecimal.valueOf(quantidadeParcelas), 2,
				RoundingMode.FLOOR);
		final BigDecimal valorRestante = valorTotal
				.subtract(valorParcelaBase.multiply(BigDecimal.valueOf(quantidadeParcelas)));

		return new OpcaoParcelamentoSaida(quantidadeParcelas, valorParcelaBase.add(valorRestante), valorParcelaBase,
				valorTotal);
	}

	private BigDecimal calcularJuros(final BigDecimal subTotal, final int quantidadeParcelas, final double juros) {
		return juros == 0 ? subTotal
				: subTotal.multiply(BigDecimal.valueOf(1 + juros).pow(quantidadeParcelas)).setScale(2,
						RoundingMode.HALF_UP);
	}

	private void validarParcelamento(final OpcaoParcelamentoEntrada opcaoParcelamento) {
		if (opcaoParcelamento.getQuantidadeMinimaParcelas() > opcaoParcelamento.getQuantidadeMaximaParcelas())
			throw new IllegalArgumentException(
					"A quantidade mínima de parcelas não pode ser maior do que a máxima: " + opcaoParcelamento);
	}

	private BigDecimal sumarizarValorCoberturas(final List<Cobertura> listCobertura) {
		return listCobertura.stream().map(Cobertura::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}