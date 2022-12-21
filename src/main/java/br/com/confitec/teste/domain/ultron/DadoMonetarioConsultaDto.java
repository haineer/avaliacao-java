package br.com.confitec.teste.domain.ultron;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DadoMonetarioConsultaDto extends UltronDto {
	private static final long serialVersionUID = -1723219217080940146L;

	private BigDecimal vlPremioNet = BigDecimal.ZERO;
	private BigDecimal vlTotalComissao = BigDecimal.ZERO;
	private List<DadoMonetarioComissaoConsultaDto> lsComissao = new ArrayList<>();
	private BigDecimal vlPremioTarifa = BigDecimal.ZERO;
	private BigDecimal vlTotalDesconto = BigDecimal.ZERO;
	private List<DadoMonetarioDescontoConsultaDto> lsDesconto;
	private BigDecimal vlPremioLiquido = BigDecimal.ZERO;
	private BigDecimal vlAdicionalFracionamento = BigDecimal.ZERO;
	private BigDecimal vlIof = BigDecimal.ZERO;
	private BigDecimal vlPremioTotal = BigDecimal.ZERO;

	private BigDecimal pcAdicionalFracionamento;
	private BigDecimal pcDesconto;
	private BigDecimal pcIof;
	private BigDecimal pcTaxaNet;

	private BigDecimal vlAssistenciaFacultativa;
	private BigDecimal vlCobranca;
	private BigDecimal vlImportanciaSegurada;
	private BigDecimal vlPremioNetAdicional;
	private BigDecimal vlTotalComissaoAdicional;
	private BigDecimal vlPremioTarifaAdicional;
	private BigDecimal vlTotalDescontoAdicional;
	private BigDecimal vlPremioLiquidoAdicional;
	private BigDecimal vlIofAdicional;
	private BigDecimal vlAdicionalFracionamentoAdicional;
	private BigDecimal vlPremioTotalAdicional;
	private BigDecimal vlPremioNetCancelado;
	private BigDecimal vlTotalComissaoCancelado;
	private BigDecimal vlPremioTarifaCancelado;
	private BigDecimal vlTotalDescontoCancelado;
	private BigDecimal vlPremioLiquidoCancelado;
	private BigDecimal vlIofCancelado;
	private BigDecimal vlAdicionalFracionamentoCancelado;
	private BigDecimal vlPremioTotalCancelado;
	private BigDecimal vlPremioTotalConsolidado;
	private BigDecimal vlPremioLiquidoConsolidado;
	private BigDecimal vlPremioNetConsolidado;
	private BigDecimal vlTotalComissaoConsolidado;
	private BigDecimal vlPremioTarifaConsolidado;
	private BigDecimal vlTotalDescontoConsolidado;
	private BigDecimal vlIofConsolidado;
	private BigDecimal vlAdicionalFracionamentoConsolidado;
	private BigDecimal vlIofRestituicao;
	private BigDecimal vlAdicionalFracionamentoRestituicao;

	private Long idCobertura;
	private Boolean houveAdiantamentoPremio;
	private Boolean houveDevolucaoPremio;
	private LocalDateTime dtDevolucao;
	private BigDecimal vlDevolvido;

	public LocalDateTime getDtDevolucao() {
		return dtDevolucao;
	}

	public void setDtDevolucao(final LocalDateTime dtDevolucao) {
		this.dtDevolucao = dtDevolucao;
	}

	public BigDecimal getVlDevolvido() {
		return vlDevolvido;
	}

	public void setVlDevolvido(final BigDecimal vlDevolvido) {
		this.vlDevolvido = vlDevolvido;
	}

	public Boolean getHouveAdiantamentoPremio() {
		return houveAdiantamentoPremio;
	}

	public void setHouveAdiantamentoPremio(final Boolean houveAdiantamentoPremio) {
		this.houveAdiantamentoPremio = houveAdiantamentoPremio;
	}

	public Boolean getHouveDevolucaoPremio() {
		return houveDevolucaoPremio;
	}

	public void setHouveDevolucaoPremio(final Boolean houveDevolucaoPremio) {
		this.houveDevolucaoPremio = houveDevolucaoPremio;
	}

	public BigDecimal getVlPremioNetAdicional() {
		return vlPremioNetAdicional;
	}

	public void setVlPremioNetAdicional(final BigDecimal vlPremioNetAdicional) {
		this.vlPremioNetAdicional = vlPremioNetAdicional;
	}

	public BigDecimal getVlTotalComissaoAdicional() {
		return vlTotalComissaoAdicional;
	}

	public void setVlTotalComissaoAdicional(final BigDecimal vlTotalComissaoAdicional) {
		this.vlTotalComissaoAdicional = vlTotalComissaoAdicional;
	}

	public BigDecimal getVlPremioTarifaAdicional() {
		return vlPremioTarifaAdicional;
	}

	public void setVlPremioTarifaAdicional(final BigDecimal vlPremioTarifaAdicional) {
		this.vlPremioTarifaAdicional = vlPremioTarifaAdicional;
	}

	public BigDecimal getVlTotalDescontoAdicional() {
		return vlTotalDescontoAdicional;
	}

	public void setVlTotalDescontoAdicional(final BigDecimal vlTotalDescontoAdicional) {
		this.vlTotalDescontoAdicional = vlTotalDescontoAdicional;
	}

	public BigDecimal getVlPremioLiquidoAdicional() {
		return vlPremioLiquidoAdicional;
	}

	public void setVlPremioLiquidoAdicional(final BigDecimal vlPremioLiquidoAdicional) {
		this.vlPremioLiquidoAdicional = vlPremioLiquidoAdicional;
	}

	public BigDecimal getVlIofAdicional() {
		return vlIofAdicional;
	}

	public void setVlIofAdicional(final BigDecimal vlIofAdicional) {
		this.vlIofAdicional = vlIofAdicional;
	}

	public BigDecimal getVlAdicionalFracionamentoAdicional() {
		return vlAdicionalFracionamentoAdicional;
	}

	public void setVlAdicionalFracionamentoAdicional(final BigDecimal vlAdicionalFracionamentoAdicional) {
		this.vlAdicionalFracionamentoAdicional = vlAdicionalFracionamentoAdicional;
	}

	public BigDecimal getVlPremioTotalAdicional() {
		return vlPremioTotalAdicional;
	}

	public void setVlPremioTotalAdicional(final BigDecimal vlPremioTotalAdicional) {
		this.vlPremioTotalAdicional = vlPremioTotalAdicional;
	}

	public BigDecimal getVlPremioNetCancelado() {
		return vlPremioNetCancelado;
	}

	public void setVlPremioNetCancelado(final BigDecimal vlPremioNetCancelado) {
		this.vlPremioNetCancelado = vlPremioNetCancelado;
	}

	public BigDecimal getVlTotalComissaoCancelado() {
		return vlTotalComissaoCancelado;
	}

	public void setVlTotalComissaoCancelado(final BigDecimal vlTotalComissaoCancelado) {
		this.vlTotalComissaoCancelado = vlTotalComissaoCancelado;
	}

	public BigDecimal getVlPremioTarifaCancelado() {
		return vlPremioTarifaCancelado;
	}

	public void setVlPremioTarifaCancelado(final BigDecimal vlPremioTarifaCancelado) {
		this.vlPremioTarifaCancelado = vlPremioTarifaCancelado;
	}

	public BigDecimal getVlTotalDescontoCancelado() {
		return vlTotalDescontoCancelado;
	}

	public void setVlTotalDescontoCancelado(final BigDecimal vlTotalDescontoCancelado) {
		this.vlTotalDescontoCancelado = vlTotalDescontoCancelado;
	}

	public BigDecimal getVlPremioLiquidoCancelado() {
		return vlPremioLiquidoCancelado;
	}

	public void setVlPremioLiquidoCancelado(final BigDecimal vlPremioLiquidoCancelado) {
		this.vlPremioLiquidoCancelado = vlPremioLiquidoCancelado;
	}

	public BigDecimal getVlIofCancelado() {
		return vlIofCancelado;
	}

	public void setVlIofCancelado(final BigDecimal vlIofCancelado) {
		this.vlIofCancelado = vlIofCancelado;
	}

	public BigDecimal getVlAdicionalFracionamentoCancelado() {
		return vlAdicionalFracionamentoCancelado;
	}

	public void setVlAdicionalFracionamentoCancelado(final BigDecimal vlAdicionalFracionamentoCancelado) {
		this.vlAdicionalFracionamentoCancelado = vlAdicionalFracionamentoCancelado;
	}

	public BigDecimal getVlPremioTotalCancelado() {
		return vlPremioTotalCancelado;
	}

	public void setVlPremioTotalCancelado(final BigDecimal vlPremioTotalCancelado) {
		this.vlPremioTotalCancelado = vlPremioTotalCancelado;
	}

	public BigDecimal getVlPremioTotalConsolidado() {
		return vlPremioTotalConsolidado;
	}

	public void setVlPremioTotalConsolidado(final BigDecimal vlPremioTotalConsolidado) {
		this.vlPremioTotalConsolidado = vlPremioTotalConsolidado;
	}

	public BigDecimal getVlPremioLiquidoConsolidado() {
		return vlPremioLiquidoConsolidado;
	}

	public void setVlPremioLiquidoConsolidado(final BigDecimal vlPremioLiquidoConsolidado) {
		this.vlPremioLiquidoConsolidado = vlPremioLiquidoConsolidado;
	}

	public BigDecimal getVlPremioNetConsolidado() {
		return vlPremioNetConsolidado;
	}

	public void setVlPremioNetConsolidado(final BigDecimal vlPremioNetConsolidado) {
		this.vlPremioNetConsolidado = vlPremioNetConsolidado;
	}

	public BigDecimal getVlTotalComissaoConsolidado() {
		return vlTotalComissaoConsolidado;
	}

	public void setVlTotalComissaoConsolidado(final BigDecimal vlTotalComissaoConsolidado) {
		this.vlTotalComissaoConsolidado = vlTotalComissaoConsolidado;
	}

	public BigDecimal getVlPremioTarifaConsolidado() {
		return vlPremioTarifaConsolidado;
	}

	public void setVlPremioTarifaConsolidado(final BigDecimal vlPremioTarifaConsolidado) {
		this.vlPremioTarifaConsolidado = vlPremioTarifaConsolidado;
	}

	public BigDecimal getVlTotalDescontoConsolidado() {
		return vlTotalDescontoConsolidado;
	}

	public void setVlTotalDescontoConsolidado(final BigDecimal vlTotalDescontoConsolidado) {
		this.vlTotalDescontoConsolidado = vlTotalDescontoConsolidado;
	}

	public BigDecimal getVlIofConsolidado() {
		return vlIofConsolidado;
	}

	public void setVlIofConsolidado(final BigDecimal vlIofConsolidado) {
		this.vlIofConsolidado = vlIofConsolidado;
	}

	public BigDecimal getVlAdicionalFracionamentoConsolidado() {
		return vlAdicionalFracionamentoConsolidado;
	}

	public void setVlAdicionalFracionamentoConsolidado(final BigDecimal vlAdicionalFracionamentoConsolidado) {
		this.vlAdicionalFracionamentoConsolidado = vlAdicionalFracionamentoConsolidado;
	}

	public BigDecimal getVlIofRestituicao() {
		return vlIofRestituicao;
	}

	public void setVlIofRestituicao(final BigDecimal vlIofRestituicao) {
		this.vlIofRestituicao = vlIofRestituicao;
	}

	public BigDecimal getVlAdicionalFracionamentoRestituicao() {
		return vlAdicionalFracionamentoRestituicao;
	}

	public void setVlAdicionalFracionamentoRestituicao(final BigDecimal vlAdicionalFracionamentoRestituicao) {
		this.vlAdicionalFracionamentoRestituicao = vlAdicionalFracionamentoRestituicao;
	}

	public BigDecimal getVlPremioNet() {
		return vlPremioNet;
	}

	public void setVlPremioNet(final BigDecimal vlPremioNet) {
		this.vlPremioNet = vlPremioNet;
	}

	public BigDecimal getVlTotalComissao() {
		return vlTotalComissao;
	}

	public void setVlTotalComissao(final BigDecimal vlTotalComissao) {
		this.vlTotalComissao = vlTotalComissao;
	}

	public List<DadoMonetarioComissaoConsultaDto> getLsComissao() {
		return lsComissao;
	}

	public void setLsComissao(final List<DadoMonetarioComissaoConsultaDto> lsComissao) {
		this.lsComissao = lsComissao;
	}

	public BigDecimal getVlPremioTarifa() {
		return vlPremioTarifa;
	}

	public void setVlPremioTarifa(final BigDecimal vlPremioTarifa) {
		this.vlPremioTarifa = vlPremioTarifa;
	}

	public BigDecimal getVlTotalDesconto() {
		return vlTotalDesconto;
	}

	public void setVlTotalDesconto(final BigDecimal vlTotalDesconto) {
		this.vlTotalDesconto = vlTotalDesconto;
	}

	public List<DadoMonetarioDescontoConsultaDto> getLsDesconto() {
		return lsDesconto;
	}

	public void setLsDesconto(final List<DadoMonetarioDescontoConsultaDto> lsDesconto) {
		this.lsDesconto = lsDesconto;
	}

	public BigDecimal getVlPremioLiquido() {
		return vlPremioLiquido;
	}

	public void setVlPremioLiquido(final BigDecimal vlPremioLiquido) {
		this.vlPremioLiquido = vlPremioLiquido;
	}

	public BigDecimal getVlAdicionalFracionamento() {
		return vlAdicionalFracionamento;
	}

	public void setVlAdicionalFracionamento(final BigDecimal vlAdicionalFracionamento) {
		this.vlAdicionalFracionamento = vlAdicionalFracionamento;
	}

	public BigDecimal getVlIof() {
		return vlIof;
	}

	public void setVlIof(final BigDecimal vlIof) {
		this.vlIof = vlIof;
	}

	public BigDecimal getVlPremioTotal() {
		return vlPremioTotal;
	}

	public void setVlPremioTotal(final BigDecimal vlPremioTotal) {
		this.vlPremioTotal = vlPremioTotal;
	}

	public BigDecimal getPcAdicionalFracionamento() {
		return pcAdicionalFracionamento;
	}

	public void setPcAdicionalFracionamento(final BigDecimal pcAdicionalFracionamento) {
		this.pcAdicionalFracionamento = pcAdicionalFracionamento;
	}

	public BigDecimal getPcDesconto() {
		return pcDesconto;
	}

	public void setPcDesconto(final BigDecimal pcDesconto) {
		this.pcDesconto = pcDesconto;
	}

	public BigDecimal getPcIof() {
		return pcIof;
	}

	public void setPcIof(final BigDecimal pcIof) {
		this.pcIof = pcIof;
	}

	public BigDecimal getPcTaxaNet() {
		return pcTaxaNet;
	}

	public void setPcTaxaNet(final BigDecimal pcTaxaNet) {
		this.pcTaxaNet = pcTaxaNet;
	}

	public BigDecimal getVlAssistenciaFacultativa() {
		return vlAssistenciaFacultativa;
	}

	public void setVlAssistenciaFacultativa(final BigDecimal vlAssistenciaFacultativa) {
		this.vlAssistenciaFacultativa = vlAssistenciaFacultativa;
	}

	public BigDecimal getVlCobranca() {
		return vlCobranca;
	}

	public void setVlCobranca(final BigDecimal vlCobranca) {
		this.vlCobranca = vlCobranca;
	}

	public BigDecimal getVlImportanciaSegurada() {
		return vlImportanciaSegurada;
	}

	public void setVlImportanciaSegurada(final BigDecimal vlImportanciaSegurada) {
		this.vlImportanciaSegurada = vlImportanciaSegurada;
	}

	public Long getIdCobertura() {
		return idCobertura;
	}

	public void setIdCobertura(final Long idCobertura) {
		this.idCobertura = idCobertura;
	}

	@Override
	public String toString() {
		return "DadoMonetarioConsultaDto [vlPremioNet=" + vlPremioNet + ", vlTotalComissao=" + vlTotalComissao
				+ ", lsComissao=" + lsComissao + ", vlPremioTarifa=" + vlPremioTarifa + ", vlTotalDesconto="
				+ vlTotalDesconto + ", lsDesconto=" + lsDesconto + ", vlPremioLiquido=" + vlPremioLiquido
				+ ", vlAdicionalFracionamento=" + vlAdicionalFracionamento + ", vlIof=" + vlIof + ", vlPremioTotal="
				+ vlPremioTotal + ", pcAdicionalFracionamento=" + pcAdicionalFracionamento + ", pcDesconto="
				+ pcDesconto + ", pcIof=" + pcIof + ", pcTaxaNet=" + pcTaxaNet + ", vlAssistenciaFacultativa="
				+ vlAssistenciaFacultativa + ", vlCobranca=" + vlCobranca + ", vlImportanciaSegurada="
				+ vlImportanciaSegurada + "]";
	}
}