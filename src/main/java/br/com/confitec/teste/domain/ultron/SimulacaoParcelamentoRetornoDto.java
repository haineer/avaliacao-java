package br.com.confitec.teste.domain.ultron;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SimulacaoParcelamentoRetornoDto extends UltronDto {
	private static final long serialVersionUID = 4889777908282641004L;

	private Integer nrQuantidadeParcela;
	private BigDecimal vlPrimeiraParcela;
	private BigDecimal vlDemaisParcelas;
	private BigDecimal vlParcelamentoTotal;
	private BigDecimal vlJurosTotal;
	private BigDecimal vlParcelamentoSemJuros;
	private BigDecimal vlPrimeiraParcelaSemJuros;
	private BigDecimal vlDemaisParcelasSemJuros;

	public SimulacaoParcelamentoRetornoDto(final Integer nrQuantidadeParcela) {
		this();
		this.nrQuantidadeParcela = nrQuantidadeParcela;
	}

	public SimulacaoParcelamentoRetornoDto(final Integer nrQuantidadeParcela, final BigDecimal vlPrimeiraParcela,
			final BigDecimal vlDemaisParcelas) {
		this.nrQuantidadeParcela = nrQuantidadeParcela;
		this.vlPrimeiraParcela = vlPrimeiraParcela;
		this.vlDemaisParcelas = vlDemaisParcelas;
		calcularValorParcelamentoTotal();
		this.vlParcelamentoSemJuros = this.vlParcelamentoTotal;
		this.vlJurosTotal = BigDecimal.ZERO;
		this.vlPrimeiraParcelaSemJuros = vlPrimeiraParcela;
		this.vlDemaisParcelasSemJuros = vlDemaisParcelas;
	}

	public BigDecimal recuperarValorParcela(final int numeroParcela) {
		return numeroParcela == 1 ? vlPrimeiraParcela : vlDemaisParcelas;
	}

	public void somarPrimeiraParcela(final BigDecimal vlParcela) {
		if (this.vlPrimeiraParcela == null)
			this.vlPrimeiraParcela = BigDecimal.ZERO;

		this.vlPrimeiraParcela = this.vlPrimeiraParcela.add(vlParcela);
	}

	public void somarDemaisParcelas(final BigDecimal vlParcela) {
		if (this.vlDemaisParcelas == null)
			this.vlDemaisParcelas = BigDecimal.ZERO;

		this.vlDemaisParcelas = this.vlDemaisParcelas.add(vlParcela);
	}

	public void calcularValorParcelamentoTotal() {
		this.vlParcelamentoTotal = nrQuantidadeParcela == 1 ? vlPrimeiraParcela
				: vlDemaisParcelas.multiply(BigDecimal.valueOf(nrQuantidadeParcela - 1)).add(vlPrimeiraParcela);
	}
}