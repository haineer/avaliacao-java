package br.com.confitec.teste.domain.ultron;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DadoMonetarioDto extends UltronDto {
	private static final long serialVersionUID = -1723219217080940146L;

	private BigDecimal vlPremioNet = BigDecimal.ZERO;
	private BigDecimal vlTotalComissao = BigDecimal.ZERO;
	private List<DadoMonetarioComissaoDto> lsComissao = new ArrayList<>();
	private BigDecimal vlPremioTarifa = BigDecimal.ZERO;
	private BigDecimal vlTotalDesconto = BigDecimal.ZERO;
	private List<DadoMonetarioDescontoDto> lsDesconto;
	private BigDecimal vlPremioLiquido = BigDecimal.ZERO;
	private BigDecimal vlAdicionalFracionamento = BigDecimal.ZERO;
	private BigDecimal vlIof = BigDecimal.ZERO;
	private BigDecimal vlPremioTotal = BigDecimal.ZERO;

	public DadoMonetarioDto() {
		super();
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

	public List<DadoMonetarioComissaoDto> getLsComissao() {
		return lsComissao;
	}

	public void setLsComissao(final List<DadoMonetarioComissaoDto> lsComissao) {
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

	public List<DadoMonetarioDescontoDto> getLsDesconto() {
		return lsDesconto;
	}

	public void setLsDesconto(final List<DadoMonetarioDescontoDto> lsDesconto) {
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

	public BigDecimal recuperarValorTotalComissao() {
		return lsComissao.stream().map(DadoMonetarioComissaoDto::getVlComissao).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	}

	private BigDecimal recuperarComissao(final TipoComissaoEnum tipoComissao) {
		return lsComissao.stream().filter(comissao -> comissao.getIdTipoComissao().equals(tipoComissao.getCodigo()))
				.map(DadoMonetarioComissaoDto::getVlComissao).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void adicionarComissao(final TipoComissaoEnum tipoComissao, final BigDecimal vlComissao) {
		if (vlComissao != null)
			lsComissao.add(new DadoMonetarioComissaoDto(tipoComissao, vlComissao));
	}

	public BigDecimal recuperarVlCorretagem() {
		return recuperarComissao(TipoComissaoEnum.CORRETAGEM);
	}

	public void setVlCorretagem(final BigDecimal vlCorretagem) {
		adicionarComissao(TipoComissaoEnum.CORRETAGEM, vlCorretagem);
	}

	public BigDecimal recuperarVlAgencimento() {
		return recuperarComissao(TipoComissaoEnum.AGENCIAMENTO);
	}

	public void setVlAgencimento(final BigDecimal vlAgenciamento) {
		adicionarComissao(TipoComissaoEnum.AGENCIAMENTO, vlAgenciamento);
	}

	public BigDecimal recuperarVlProlabore() {
		return recuperarComissao(TipoComissaoEnum.PROLABORE);
	}

	public void setVlProlabore(final BigDecimal vlProlabore) {
		adicionarComissao(TipoComissaoEnum.PROLABORE, vlProlabore);
	}

	public BigDecimal recuperarVlRemuneracao() {
		return recuperarComissao(TipoComissaoEnum.SERVICO_PRESTADO);
	}

	public void setVlRemuneracao(final BigDecimal vlRemuneracao) {
		adicionarComissao(TipoComissaoEnum.SERVICO_PRESTADO, vlRemuneracao);
	}

	public BigDecimal recuperarVlRepresentante() {
		return recuperarComissao(TipoComissaoEnum.REPRESENTANTE);
	}

	public void setVlRepresentante(final BigDecimal vlRepresentante) {
		adicionarComissao(TipoComissaoEnum.REPRESENTANTE, vlRepresentante);
	}

	public BigDecimal recuperarVlComissaoCosseguro() {
		return recuperarComissao(TipoComissaoEnum.COMISSAO_COSSEGURO);
	}

	public void setVlComissaoCosseguro(final BigDecimal vlComissaoCosseguro) {
		adicionarComissao(TipoComissaoEnum.COMISSAO_COSSEGURO, vlComissaoCosseguro);
	}

	public BigDecimal recuperarVlAngariacao() {
		return recuperarComissao(TipoComissaoEnum.ANGARIACAO);
	}

	public void setVlAngariacao(final BigDecimal vlAngariacao) {
		adicionarComissao(TipoComissaoEnum.ANGARIACAO, vlAngariacao);
	}

	@Override
	public String toString() {
		return "DadoMonetarioDto [vlPremioNet=" + vlPremioNet + ", vlTotalComissao=" + vlTotalComissao + ", lsComissao="
				+ lsComissao + ", vlPremioTarifa=" + vlPremioTarifa + ", vlTotalDesconto=" + vlTotalDesconto
				+ ", lsDesconto=" + lsDesconto + ", vlPremioLiquido=" + vlPremioLiquido + ", vlAdicionalFracionamento="
				+ vlAdicionalFracionamento + ", vlIof=" + vlIof + ", vlPremioTotal=" + vlPremioTotal + "]";
	}

	public static class Builder {
		private BigDecimal vlPremioNet = BigDecimal.ZERO;
		private BigDecimal vlTotalComissao = BigDecimal.ZERO;
		private final List<DadoMonetarioComissaoDto> lsComissao = new ArrayList<>();
		private BigDecimal vlPremioTarifa = BigDecimal.ZERO;
		private BigDecimal vlTotalDesconto = BigDecimal.ZERO;
		private final List<DadoMonetarioDescontoDto> lsDesconto = new ArrayList<>();
		private BigDecimal vlPremioLiquido = BigDecimal.ZERO;
		private BigDecimal vlAdicionalFracionamento = BigDecimal.ZERO;
		private BigDecimal vlIof = BigDecimal.ZERO;
		private BigDecimal vlPremioTotal = BigDecimal.ZERO;

		public Builder vlPremioNet(final BigDecimal vlPremioNet) {
			this.vlPremioNet = vlPremioNet;
			return this;
		}

		public Builder vlTotalComissao(final BigDecimal vlTotalComissao) {
			this.vlTotalComissao = vlTotalComissao;
			return this;
		}

		public Builder lsComissao(final List<DadoMonetarioComissaoConsultaDto> lsComissaoConsulta) {
			lsComissao.clear();
			lsComissaoConsulta.forEach(comissaoConsulta -> lsComissao
					.add(new DadoMonetarioComissaoDto(TipoComissaoEnum.parse(comissaoConsulta.getNrTipoComissao()),
							comissaoConsulta.getPcComissao(), comissaoConsulta.getVlComissao())));
			return this;
		}

		public Builder vlPremioTarifa(final BigDecimal vlPremioTarifa) {
			this.vlPremioTarifa = vlPremioTarifa;
			return this;
		}

		public Builder vlTotalDesconto(final BigDecimal vlTotalDesconto) {
			this.vlTotalDesconto = vlTotalDesconto;
			return this;
		}

		public Builder lsDesconto(final List<DadoMonetarioDescontoConsultaDto> lsDescontoConsulta) {
			lsDesconto.clear();
			lsDescontoConsulta.forEach(descontoConsulta -> lsDesconto.add(new DadoMonetarioDescontoDto(
					descontoConsulta.getNrTipoDesconto().intValue(), descontoConsulta.getNmTipoDesconto(),
					descontoConsulta.getPcDesconto(), descontoConsulta.getVlDesconto())));
			return this;
		}

		public Builder vlPremioLiquido(final BigDecimal vlPremioLiquido) {
			this.vlPremioLiquido = vlPremioLiquido;
			return this;
		}

		public Builder vlAdicionalFracionamento(final BigDecimal vlAdicionalFracionamento) {
			this.vlAdicionalFracionamento = vlAdicionalFracionamento;
			return this;
		}

		public Builder vlIof(final BigDecimal vlIof) {
			this.vlIof = vlIof;
			return this;
		}

		public Builder vlPremioTotal(final BigDecimal vlPremioTotal) {
			this.vlPremioTotal = vlPremioTotal;
			return this;
		}

		public DadoMonetarioDto build() {
			return new DadoMonetarioDto(this);
		}
	}

	public DadoMonetarioDto(final Builder builder) {
		vlPremioNet = builder.vlPremioNet;
		vlTotalComissao = builder.vlTotalComissao;
		lsComissao = builder.lsComissao;
		vlPremioTarifa = builder.vlPremioTarifa;
		vlTotalDesconto = builder.vlTotalDesconto;
		lsDesconto = builder.lsDesconto;
		vlPremioLiquido = builder.vlPremioLiquido;
		vlAdicionalFracionamento = builder.vlAdicionalFracionamento;
		vlIof = builder.vlIof;
		vlPremioTotal = builder.vlPremioTotal;
	}
}