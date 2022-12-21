package br.com.confitec.teste.domain.ultron;

import java.math.BigDecimal;

public class DadoMonetarioDescontoDto extends UltronDto {
	private static final long serialVersionUID = -4093817902573493531L;

	private Integer idTipoDesconto;
	private String nmTipoDesconto;
	private BigDecimal pcDesconto;
	private BigDecimal vlDesconto;

	public DadoMonetarioDescontoDto() {
		super();
	}

	public DadoMonetarioDescontoDto(final Integer idTipoDesconto, final String nmTipoDesconto,
			final BigDecimal pcDesconto, final BigDecimal vlDesconto) {
		this();
		this.idTipoDesconto = idTipoDesconto;
		this.nmTipoDesconto = nmTipoDesconto;
		this.pcDesconto = pcDesconto;
		this.vlDesconto = vlDesconto;
	}

	public Integer getIdTipoDesconto() {
		return idTipoDesconto;
	}

	public void setIdTipoDesconto(final Integer idTipoDesconto) {
		this.idTipoDesconto = idTipoDesconto;
	}

	public String getNmTipoDesconto() {
		return nmTipoDesconto;
	}

	public void setNmTipoDesconto(final String nmTipoDesconto) {
		this.nmTipoDesconto = nmTipoDesconto;
	}

	public BigDecimal getPcDesconto() {
		return pcDesconto;
	}

	public void setPcDesconto(final BigDecimal pcDesconto) {
		this.pcDesconto = pcDesconto;
	}

	public BigDecimal getVlDesconto() {
		return vlDesconto;
	}

	public void setVlDesconto(final BigDecimal vlDesconto) {
		this.vlDesconto = vlDesconto;
	}

	@Override
	public String toString() {
		return "DadoMonetarioDescontoDto [idTipoDesconto=" + idTipoDesconto + ", nmTipoDesconto=" + nmTipoDesconto
				+ ", pcDesconto=" + pcDesconto + ", vlDesconto=" + vlDesconto + "]";
	}
}