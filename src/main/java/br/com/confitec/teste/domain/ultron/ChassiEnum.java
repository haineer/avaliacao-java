package br.com.confitec.teste.domain.ultron;

import java.util.Arrays;
import java.util.List;

public enum ChassiEnum {

	// @formatter:off
	RESIDENCIAL(),
	EMPRESARIAL(),
	ITENS_PESSOAIS(),
	VIDA(3525, 3020),
	RURAL();
	// @formatter:on

	private List<Integer> nrChassi;

	/**
	 * Como o número do chassi parece mudar de um ambiente para outro, este
	 * parâmetro foi mapeado como varargs para configurar uma vez só.
	 *
	 * @param nrChassi - Números que o chassi pode ter (independente do ambiente)
	 */
	private ChassiEnum(final Integer... nrChassi) {
		this.nrChassi = Arrays.asList(nrChassi);
	}

	public static ChassiEnum get(final int nrChassi) {
		return Arrays.asList(values()).stream().filter(rateio -> rateio.nrChassi.contains(nrChassi)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("exception.implementacaoNaoMapeada"));
	}
}