package br.com.confitec.teste.domain.ultron;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import br.com.confitec.teste.util.FinanceiroUtils;
import br.com.confitec.teste.util.RateioHelper;

public class RateioBo {

	private Map<Integer, BigDecimal> mapRateioPremioTarifa = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioPremioNet = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioCorretagem = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioAgenciamento = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioProlabore = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioRemuneracao = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioRepresentante = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioCosseguro = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioAngariacao = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioDesconto = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioPremioLiquido = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioAdicionalFracionamento = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioIof = new HashMap<>();
	private Map<Integer, BigDecimal> mapRateioPremioTotal = new HashMap<>();

	public RateioBo() {
		super();
	}

	public RateioBo(final DadoMonetarioConsultaDto dadoMonetarioEmissaoDto, final Integer nrQuantidadeParcela,
			final int ultimaParcela) {
		this(converterDadoMonetarioEmissao(dadoMonetarioEmissaoDto), nrQuantidadeParcela, ultimaParcela);
	}

	public RateioBo(final DadoMonetarioDto dadoMonetarioDto, final Integer nrQuantidadeParcela,
			final int ultimaParcela) {
		mapRateioPremioTarifa = RateioHelper.calcularRateio(dadoMonetarioDto.getVlPremioTarifa(), nrQuantidadeParcela,
				ultimaParcela);
		mapRateioPremioNet = RateioHelper.calcularRateio(dadoMonetarioDto.getVlPremioNet(), nrQuantidadeParcela,
				ultimaParcela);
		mapRateioCorretagem = RateioHelper.calcularRateio(dadoMonetarioDto.recuperarVlCorretagem(), nrQuantidadeParcela,
				ultimaParcela);
		mapRateioAgenciamento = RateioHelper.calcularRateio(dadoMonetarioDto.recuperarVlAgencimento(),
				nrQuantidadeParcela, ultimaParcela);
		mapRateioProlabore = RateioHelper.calcularRateio(dadoMonetarioDto.recuperarVlProlabore(), nrQuantidadeParcela,
				ultimaParcela);
		mapRateioRemuneracao = RateioHelper.calcularRateio(dadoMonetarioDto.recuperarVlRemuneracao(),
				nrQuantidadeParcela, ultimaParcela);
		mapRateioRepresentante = RateioHelper.calcularRateio(dadoMonetarioDto.recuperarVlRepresentante(),
				nrQuantidadeParcela, ultimaParcela);
		mapRateioCosseguro = RateioHelper.calcularRateio(dadoMonetarioDto.recuperarVlComissaoCosseguro(),
				nrQuantidadeParcela, ultimaParcela);
		mapRateioAngariacao = RateioHelper.calcularRateio(dadoMonetarioDto.recuperarVlAngariacao(), nrQuantidadeParcela,
				ultimaParcela);
		mapRateioDesconto = RateioHelper.calcularRateio(dadoMonetarioDto.getVlTotalDesconto(), nrQuantidadeParcela,
				ultimaParcela);
		mapRateioPremioLiquido = RateioHelper.calcularRateio(dadoMonetarioDto.getVlPremioLiquido(), nrQuantidadeParcela,
				ultimaParcela);
		mapRateioAdicionalFracionamento = RateioHelper.calcularRateio(dadoMonetarioDto.getVlAdicionalFracionamento(),
				nrQuantidadeParcela, ultimaParcela);
		mapRateioIof = RateioHelper.calcularRateio(dadoMonetarioDto.getVlIof(), nrQuantidadeParcela, ultimaParcela);
		mapRateioPremioTotal = RateioHelper.calcularRateio(dadoMonetarioDto.getVlPremioTotal(), nrQuantidadeParcela,
				ultimaParcela);
	}

	private static DadoMonetarioDto converterDadoMonetarioEmissao(
			final DadoMonetarioConsultaDto dadoMonetarioEmissaoDto) {
		return new DadoMonetarioDto.Builder().vlPremioNet(dadoMonetarioEmissaoDto.getVlPremioNet())
				.vlTotalComissao(dadoMonetarioEmissaoDto.getVlTotalComissao())
				.lsComissao(dadoMonetarioEmissaoDto.getLsComissao())
				.vlPremioTarifa(dadoMonetarioEmissaoDto.getVlPremioTarifa())
				.vlTotalDesconto(dadoMonetarioEmissaoDto.getVlTotalDesconto())
				.lsDesconto(dadoMonetarioEmissaoDto.getLsDesconto())
				.vlPremioLiquido(FinanceiroUtils.subtrair(dadoMonetarioEmissaoDto.getVlPremioTotal(),
						dadoMonetarioEmissaoDto.getVlIof(), dadoMonetarioEmissaoDto.getVlAdicionalFracionamento()))
				.vlAdicionalFracionamento(dadoMonetarioEmissaoDto.getVlAdicionalFracionamento())
				.vlIof(dadoMonetarioEmissaoDto.getVlIof()).vlPremioTotal(dadoMonetarioEmissaoDto.getVlPremioTotal())
				.build();
	}

	public Map<Integer, BigDecimal> getMapRateioPremioTarifa() {
		return mapRateioPremioTarifa;
	}

	public void setMapRateioPremioTarifa(final Map<Integer, BigDecimal> mapRateioPremioTarifa) {
		this.mapRateioPremioTarifa = mapRateioPremioTarifa;
	}

	public Map<Integer, BigDecimal> getMapRateioPremioNet() {
		return mapRateioPremioNet;
	}

	public void setMapRateioPremioNet(final Map<Integer, BigDecimal> mapRateioPremioNet) {
		this.mapRateioPremioNet = mapRateioPremioNet;
	}

	public Map<Integer, BigDecimal> getMapRateioCorretagem() {
		return mapRateioCorretagem;
	}

	public void setMapRateioCorretagem(final Map<Integer, BigDecimal> mapRateioCorretagem) {
		this.mapRateioCorretagem = mapRateioCorretagem;
	}

	public Map<Integer, BigDecimal> getMapRateioAgenciamento() {
		return mapRateioAgenciamento;
	}

	public void setMapRateioAgenciamento(final Map<Integer, BigDecimal> mapRateioAgenciamento) {
		this.mapRateioAgenciamento = mapRateioAgenciamento;
	}

	public Map<Integer, BigDecimal> getMapRateioProlabore() {
		return mapRateioProlabore;
	}

	public void setMapRateioProlabore(final Map<Integer, BigDecimal> mapRateioProlabore) {
		this.mapRateioProlabore = mapRateioProlabore;
	}

	public Map<Integer, BigDecimal> getMapRateioRemuneracao() {
		return mapRateioRemuneracao;
	}

	public void setMapRateioRemuneracao(final Map<Integer, BigDecimal> mapRateioRemuneracao) {
		this.mapRateioRemuneracao = mapRateioRemuneracao;
	}

	public Map<Integer, BigDecimal> getMapRateioRepresentante() {
		return mapRateioRepresentante;
	}

	public void setMapRateioRepresentante(final Map<Integer, BigDecimal> mapRateioRepresentante) {
		this.mapRateioRepresentante = mapRateioRepresentante;
	}

	public Map<Integer, BigDecimal> getMapRateioCosseguro() {
		return mapRateioCosseguro;
	}

	public void setMapRateioCosseguro(final Map<Integer, BigDecimal> mapRateioCosseguro) {
		this.mapRateioCosseguro = mapRateioCosseguro;
	}

	public Map<Integer, BigDecimal> getMapRateioAngariacao() {
		return mapRateioAngariacao;
	}

	public void setMapRateioAngariacao(final Map<Integer, BigDecimal> mapRateioAngariacao) {
		this.mapRateioAngariacao = mapRateioAngariacao;
	}

	public Map<Integer, BigDecimal> getMapRateioDesconto() {
		return mapRateioDesconto;
	}

	public void setMapRateioDesconto(final Map<Integer, BigDecimal> mapRateioDesconto) {
		this.mapRateioDesconto = mapRateioDesconto;
	}

	public Map<Integer, BigDecimal> getMapRateioPremioLiquido() {
		return mapRateioPremioLiquido;
	}

	public void setMapRateioLiquido(final Map<Integer, BigDecimal> mapRateioPremioLiquido) {
		this.mapRateioPremioLiquido = mapRateioPremioLiquido;
	}

	public Map<Integer, BigDecimal> getMapRateioAdicionalFracionamento() {
		return mapRateioAdicionalFracionamento;
	}

	public void setMapRateioAdicionalFracionamento(final Map<Integer, BigDecimal> mapRateioAdicionalFracionamento) {
		this.mapRateioAdicionalFracionamento = mapRateioAdicionalFracionamento;
	}

	public Map<Integer, BigDecimal> getMapRateioIof() {
		return mapRateioIof;
	}

	public void setMapRateioIof(final Map<Integer, BigDecimal> mapRateioIof) {
		this.mapRateioIof = mapRateioIof;
	}

	public Map<Integer, BigDecimal> getMapRateioPremioTotal() {
		return mapRateioPremioTotal;
	}

	public void setMapRateioPremioTotal(final Map<Integer, BigDecimal> mapRateioPremioTotal) {
		this.mapRateioPremioTotal = mapRateioPremioTotal;
	}

	@Override
	public String toString() {
		return "RateioBo [mapRateioPremioTarifa=" + mapRateioPremioTarifa + ", mapRateioPremioNet=" + mapRateioPremioNet
				+ ", mapRateioCorretagem=" + mapRateioCorretagem + ", mapRateioAgenciamento=" + mapRateioAgenciamento
				+ ", mapRateioProlabore=" + mapRateioProlabore + ", mapRateioRemuneracao=" + mapRateioRemuneracao
				+ ", mapRateioRepresentante=" + mapRateioRepresentante + ", mapRateioCosseguro=" + mapRateioCosseguro
				+ ", mapRateioAngariacao=" + mapRateioAngariacao + ", mapRateioDesconto=" + mapRateioDesconto
				+ ", mapRateioAdicionalFracionamento=" + mapRateioAdicionalFracionamento + ", mapRateioIof="
				+ mapRateioIof + ", mapRateioPremioTotal=" + mapRateioPremioTotal + "]";
	}
}