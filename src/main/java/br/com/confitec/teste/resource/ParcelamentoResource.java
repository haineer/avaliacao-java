package br.com.confitec.teste.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.confitec.teste.domain.ParcelamentoEntrada;
import br.com.confitec.teste.domain.ParcelamentoSaida;
import br.com.confitec.teste.service.ParcelamentoService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/confitec/teste/parcelamento")
@Api(value = "parcelamento", tags = { "parcelamento" })
public class ParcelamentoResource {

	@Autowired
	private ParcelamentoService service;

	@PostMapping
	public ParcelamentoSaida parcelar(@Valid @RequestBody final ParcelamentoEntrada entrada) {
		log.debug("Início ParcelamentoSaida#parcelar | entrada: {}", entrada);

		return service.parcelar(entrada);
	}
}