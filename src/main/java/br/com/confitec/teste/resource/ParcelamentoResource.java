package br.com.confitec.teste.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.confitec.teste.domain.Erro;
import br.com.confitec.teste.domain.ParcelamentoEntrada;
import br.com.confitec.teste.domain.ParcelamentoSaida;
import br.com.confitec.teste.service.ParcelamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/confitec/teste/parcelamento")
@Tag(name = "Parcelamento", description = "Cálculos de parcelamento")
public class ParcelamentoResource {

	@Autowired
	private ParcelamentoService service;

	@Operation(summary = "Calculo de parcelamento", description = "Realiza o cálculo de parcelamento dado as opções de parcelamento e coberturas ", tags = {
			"Parcelamento" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParcelamentoSaida.class)), description = "Informações calculadas para parcelamento"),
			@ApiResponse(responseCode = "405", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Erro.class)), description = "Método não suportado"),
			@ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Erro.class)), description = "Ocorreu um erro na API") })
	@PostMapping(produces = "application/json")
	public ResponseEntity<ParcelamentoSaida> parcelar(@Valid @RequestBody final ParcelamentoEntrada entrada) {
		log.debug("ParcelamentoSaida#parcelar | entrada: {}", entrada);

		return new ResponseEntity<>(service.parcelar(entrada), HttpStatus.OK);
	}
}