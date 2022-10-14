package br.com.confitec.teste.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.confitec.teste.domain.Erro;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionResourceAdvice {

	@ResponseBody
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public Erro exceptionHandler(final HttpRequestMethodNotSupportedException ex) {
		log.error(ex.getMessage());

		return new Erro("Método não permitido", ex.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ IllegalArgumentException.class, MethodArgumentNotValidException.class })
	public Erro exceptionHandler(final Exception ex) {
		log.error(ex.getMessage());

		return new Erro("Requisição inválida", ex.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public Erro exceptionHandler(final HttpMessageNotReadableException ex) {
		log.error(ex.getMessage());

		return new Erro("Ocorreu um erro ao desserializar o objeto enviado", ex.getMessage());
	}
}