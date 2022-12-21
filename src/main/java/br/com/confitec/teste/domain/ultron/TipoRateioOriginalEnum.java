package br.com.confitec.teste.domain.ultron;

import br.com.confitec.teste.util.ComposicaoFaturamentoConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoRateioOriginalEnum implements ConfiguracaoRateio {

	// @formatter:off
	PREMIO_NET(ComposicaoFaturamentoConstants.PREMIO_NET, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	CORRETAGEM(ComposicaoFaturamentoConstants.CORRETAGEM, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	AGENCIAMENTO(ComposicaoFaturamentoConstants.AGENCIAMENTO, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	PROLABORE(ComposicaoFaturamentoConstants.PROLABORE, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	REPRESENTANTE(ComposicaoFaturamentoConstants.REPRESENTANTE, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	REMUNERACAO(ComposicaoFaturamentoConstants.REMUNERACAO, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	COSSEGURO(ComposicaoFaturamentoConstants.COSSEGURO, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	ANGARIACAO(ComposicaoFaturamentoConstants.ANGARIACAO, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	PREMIO_TARIFA(ComposicaoFaturamentoConstants.PREMIO_TARIFA, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	DESCONTO(ComposicaoFaturamentoConstants.DESCONTO, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	PREMIO_LIQUIDO(ComposicaoFaturamentoConstants.PREMIO_LIQUIDO, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	ADICIONAL_FRACIONAMENTO(ComposicaoFaturamentoConstants.ADICIONAL_FRACIONAMENTO, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	IOF(ComposicaoFaturamentoConstants.IOF, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA),
	PREMIO_TOTAL(ComposicaoFaturamentoConstants.PREMIO_TOTAL, TipoRateioEnum.CONVENCIONAL_PRIMEIRA_PARCELA);
	// @formatter:on

	private String tipoComposicaoPremio;
	private TipoRateioEnum tipoRateio;

	public static TipoRateioEnum getTipoRateio(final String tipoComposicaoPremio) {
		for (final TipoRateioOriginalEnum tipoRateioPremio : values())
			if (tipoRateioPremio.tipoComposicaoPremio == tipoComposicaoPremio)
				return tipoRateioPremio.tipoRateio;

		throw new IllegalArgumentException("exception.enum.codigoInvalido");
	}
}