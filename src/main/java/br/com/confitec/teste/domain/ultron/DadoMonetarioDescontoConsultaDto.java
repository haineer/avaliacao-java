package br.com.confitec.teste.domain.ultron;

import java.math.BigDecimal;

public class DadoMonetarioDescontoConsultaDto extends UltronDto {
	private static final long serialVersionUID = -4093817902573493531L;

	private Long nrTipoDesconto;
	private String nmTipoDesconto;
	private BigDecimal pcDesconto;
	private BigDecimal vlDesconto;

	private Long idCobertura;

	public Long getNrTipoDesconto() {
		return nrTipoDesconto;
	}

	public void setNrTipoDesconto(final Long nrTipoDesconto) {
		this.nrTipoDesconto = nrTipoDesconto;
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

	public Long getIdCobertura() {
		return idCobertura;
	}

	public void setIdCobertura(final Long idCobertura) {
		this.idCobertura = idCobertura;
	}

	@Override
	public String toString() {
		return "DadoMonetarioDescontoDto [idTipoDesconto=" + nrTipoDesconto + ", nmTipoDesconto=" + nmTipoDesconto
				+ ", pcDesconto=" + pcDesconto + ", vlDesconto=" + vlDesconto + "]";
	}
}