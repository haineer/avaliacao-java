package br.com.confitec.teste.domain.ultron;

import java.math.BigDecimal;

public class DadoMonetarioComissaoConsultaDto extends UltronDto {
	private static final long serialVersionUID = 7358244700744021161L;

	private Integer nrTipoComissao;
	private String nmTipoComissao;
	private BigDecimal pcComissao;
	private BigDecimal vlComissao;

	private BigDecimal vlComissaoConsolidado;
	private BigDecimal vlRecuperacao;
	private BigDecimal vlRecuperacaoConsolidado;
	private BigDecimal vlComissaoCancelado;

	private Long idCobertura;

	public DadoMonetarioComissaoConsultaDto() {
		super();
	}

	public Integer getNrTipoComissao() {
		return nrTipoComissao;
	}

	public void setNrTipoComissao(final Integer nrTipoComissao) {
		this.nrTipoComissao = nrTipoComissao;
	}

	public String getNmTipoComissao() {
		return nmTipoComissao;
	}

	public void setNmTipoComissao(final String nmTipoComissao) {
		this.nmTipoComissao = nmTipoComissao;
	}

	public BigDecimal getPcComissao() {
		return pcComissao;
	}

	public void setPcComissao(final BigDecimal pcComissao) {
		this.pcComissao = pcComissao;
	}

	public BigDecimal getVlComissao() {
		return vlComissao;
	}

	public void setVlComissao(final BigDecimal vlComissao) {
		this.vlComissao = vlComissao;
	}

	public BigDecimal getVlComissaoConsolidado() {
		return vlComissaoConsolidado;
	}

	public void setVlComissaoConsolidado(final BigDecimal vlComissaoConsolidado) {
		this.vlComissaoConsolidado = vlComissaoConsolidado;
	}

	public BigDecimal getVlRecuperacao() {
		return vlRecuperacao;
	}

	public void setVlRecuperacao(final BigDecimal vlRecuperacao) {
		this.vlRecuperacao = vlRecuperacao;
	}

	public BigDecimal getVlRecuperacaoConsolidado() {
		return vlRecuperacaoConsolidado;
	}

	public void setVlRecuperacaoConsolidado(final BigDecimal vlRecuperacaoConsolidado) {
		this.vlRecuperacaoConsolidado = vlRecuperacaoConsolidado;
	}

	public BigDecimal getVlComissaoCancelado() {
		return vlComissaoCancelado;
	}

	public void setVlComissaoCancelado(final BigDecimal vlComissaoCancelado) {
		this.vlComissaoCancelado = vlComissaoCancelado;
	}

	public Long getIdCobertura() {
		return idCobertura;
	}

	public void setIdCobertura(final Long idCobertura) {
		this.idCobertura = idCobertura;
	}

	@Override
	public String toString() {
		return "DadoMonetarioComissaoConsultaDto [idTipoComissao=" + nrTipoComissao + ", nmTipoComissao="
				+ nmTipoComissao + ", pcComissao=" + pcComissao + ", vlComissao=" + vlComissao + "]";
	}
}