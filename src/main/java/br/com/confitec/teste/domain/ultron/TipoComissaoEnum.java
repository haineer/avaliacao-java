package br.com.confitec.teste.domain.ultron;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum TipoComissaoEnum {

	//// @formatter:off
	CORRETAGEM		  (1, "enum.comissao.corretagem"	 , "valorCorretagem"	   , 2,  "E1",	"D1"),
	AGENCIAMENTO	  (2, "enum.comissao.agenciamento"	 , "valorAgenciamento"	   , 32, "E5",	"D5"),
	PROLABORE		  (3, "enum.comissao.prolabore"		 , "valorProlabore"		   , 32, "E4",	"D4"),
	REPRESENTANTE	  (4, "enum.comissao.representante"	 , "valorRepresentante"	   , 32, "ER",	"DR"),
	SERVICO_PRESTADO  (5, "enum.comissao.servicoPrestado", "valorRemuneracao"	   , 32, "E0",	"D0"),
	COMISSAO_COSSEGURO(6, "enum.comissao.cosseguro"		 , "valorComissaoCosseguro", 32, "E7",	"D7"),
	ANGARIACAO		  (7, "enum.comissao.angariacao"	 , "valorAngariacao"	   , 32, "E6",	"D6");
	// @formatter:on

	private int codigo;
	private String nome;
	private String nomeAtributo;
	private Integer codigoAtividadeSap;
	private String nomeCdTipoComissaoSAPPagamento;
	private String nomeCdTipoComissaoSAPRemuneracao;

	private TipoComissaoEnum(final int codigo, final String nome, final String nomeAtributo,
			final int codigoAtividadeSap, final String nomeCdTipoComissaoSAPPagamento,
			final String nomeCdTipoComissaoSAPRemuneracao) {
		this.codigo = codigo;
		this.nome = nome;
		this.nomeAtributo = nomeAtributo;
		this.codigoAtividadeSap = codigoAtividadeSap;
		this.nomeCdTipoComissaoSAPPagamento = nomeCdTipoComissaoSAPPagamento;
		this.nomeCdTipoComissaoSAPRemuneracao = nomeCdTipoComissaoSAPRemuneracao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNomeAtributo() {
		return nomeAtributo;
	}

	public Integer getCodigoAtividadeSap() {
		return codigoAtividadeSap;
	}

	public String getNomeCdTipoComissaoSAPPagamento() {
		return nomeCdTipoComissaoSAPPagamento;
	}

	public String getNomeCdTipoComissaoSAPRemuneracao() {
		return nomeCdTipoComissaoSAPRemuneracao;
	}

	@JsonFormat
	public String getNome() {
		return nome;
	}

	public static TipoComissaoEnum parse(final Integer codigo) {
		return Arrays.stream(TipoComissaoEnum.values()).filter(e -> e.codigo == codigo).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("exception.enum.tipoComissao.codigoInvalido"));
	}
}