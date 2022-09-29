package br.com.confitec.teste.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@Schema(name = "Opção Parcelamento Saida", description = "Informações calculadas para parcelamento")
public class OpcaoParcelamentoSaida implements Serializable {
	private static final long serialVersionUID = -6082804209861464324L;

	@Schema(required = false, description = "Quantidade de parcelas da opção de parcelamento", example = "1")
	private final int quantidadeParcelas;
	
	@Schema(required = false, description = "Valor calculado para a primeira parcela", example = "10.00")
	private final BigDecimal valorPrimeiraParcela;

	@Schema(required = false, description = "Valor calculado para as demais parcelas", example = "10.00")
	private BigDecimal valorDemaisParcelas;
	
	@Schema(required = false, description = "Valor total calculado", example = "100.00")
	private final BigDecimal valorParcelamentoTotal;

	/**
	 * Construtor utilizado quando a quantidade de parcelas é igual a 1.
	 *
	 * @param valorParcelamentoTotal
	 */
	public OpcaoParcelamentoSaida(final BigDecimal valorParcelamentoTotal) {
		this.quantidadeParcelas = 1;
		this.valorPrimeiraParcela = valorParcelamentoTotal;
		this.valorParcelamentoTotal = valorParcelamentoTotal;
	}
}