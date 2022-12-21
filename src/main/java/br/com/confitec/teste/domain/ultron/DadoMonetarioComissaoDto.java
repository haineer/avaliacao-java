package br.com.confitec.teste.domain.ultron;

import java.math.BigDecimal;

public class DadoMonetarioComissaoDto extends UltronDto {
	private static final long serialVersionUID = 7358244700744021161L;

	private Integer idTipoComissao;
	private String nmTipoComissao;
	private BigDecimal pcComissao;
	private BigDecimal vlComissao;

	public DadoMonetarioComissaoDto() {
		super();
	}

	public DadoMonetarioComissaoDto(final TipoComissaoEnum tipoComissao, final BigDecimal vlComissao) {
		super();
		this.idTipoComissao = tipoComissao.getCodigo();
		this.nmTipoComissao = tipoComissao.getNome();
		this.vlComissao = vlComissao;
	}

	public DadoMonetarioComissaoDto(final TipoComissaoEnum tipoComissao, final BigDecimal pcComissao,
			final BigDecimal vlComissao) {
		super();
		this.idTipoComissao = tipoComissao.getCodigo();
		this.nmTipoComissao = tipoComissao.getNome();
		this.pcComissao = pcComissao;
		this.vlComissao = vlComissao;
	}

	public Integer getIdTipoComissao() {
		return idTipoComissao;
	}

	public void setIdTipoComissao(final Integer idTipoComissao) {
		this.idTipoComissao = idTipoComissao;
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

	@Override
	public String toString() {
		return "DadoMonetarioComissaoDto [idTipoComissao=" + idTipoComissao + ", nmTipoComissao=" + nmTipoComissao
				+ ", pcComissao=" + pcComissao + ", vlComissao=" + vlComissao + "]";
	}
}