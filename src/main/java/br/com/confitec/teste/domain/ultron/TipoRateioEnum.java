package br.com.confitec.teste.domain.ultron;

public enum TipoRateioEnum {

	// @formatter:off
	CONVENCIONAL_PRIMEIRA_PARCELA(1),
	DISTRIBUIDO(2),
	CONVENCIONAL_ULTIMA_PARCELA(3),
	CONVENCIONAL_PRIMEIRA_PARCELA_IOF(4);
	// @formatter:on

	private int codigo;

	private TipoRateioEnum(final int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public static TipoRateioEnum fromValue(final int codigo) {
		for (final TipoRateioEnum tipoRateio : values())
			if (tipoRateio.codigo == codigo)
				return tipoRateio;

		throw new IllegalArgumentException("exception.enum.codigoInvalido");
	}
}