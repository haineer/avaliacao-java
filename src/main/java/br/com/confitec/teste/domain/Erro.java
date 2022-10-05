package br.com.confitec.teste.domain;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Erro", description = "Informações sobre erro encontrado na execução de cálculo")
public class Erro implements Serializable {

	private static final long serialVersionUID = -7883551671320769072L;

	@Schema(required = false, description = "Mensagem de erro definida pelo desenvolvedor", example = "Método não permitido")
	private String mensagemDesenvolvedor;

	@Schema(required = false, description = "Mensagem de erro apresentada pela exceção", example = "Method 'GET' not allowed")
	private String mensagemExcecao;
}
