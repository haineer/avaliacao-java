package br.com.confitec.teste.resource;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.confitec.teste.domain.Erro;
import br.com.confitec.teste.domain.ParcelamentoEntrada;
import br.com.confitec.teste.domain.ParcelamentoSaida;
import br.com.confitec.teste.service.DescontoService;
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
@RequestMapping("/confitec/teste")
@Tag(name = "Confitec", description = "Cálculos de parcelamento")
public class ParcelamentoResource {

	@Autowired
	private DescontoService descontoService;

	@Autowired
	private ParcelamentoService parcelamentoService;

	@Operation(summary = "Avaliação Java I", description = "Realiza o cálculo do valor final de uma compra, aplicando o valor de desconto adequado", tags = {
			"Confitec" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParcelamentoSaida.class)), description = "Valor da compra com desconto"),
			@ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Erro.class)), description = "Requisição inválida"),
			@ApiResponse(responseCode = "405", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Erro.class)), description = "Método não suportado"),
			@ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Erro.class)), description = "Ocorreu um erro na API") })
	@PostMapping(value = "/desconto", produces = "application/json")
	public ResponseEntity<BigDecimal> calcularValorCompra(
			@Valid @NotNull @Positive @RequestParam final BigDecimal valorCompra,
			@Valid @NotNull @Positive @RequestParam final int quantidadeParcela) {
		log.debug("ParcelamentoSaida#calcularValorCompra | valorCompra: {}, quantidadeParcela: {}", valorCompra,
				quantidadeParcela);

		return new ResponseEntity<>(descontoService.calcularDesconto(valorCompra, quantidadeParcela), HttpStatus.OK);
	}

	@Operation(summary = "Avaliação Java II", description = "Realiza o cálculo de parcelamento dado as opções de parcelamento e coberturas", tags = {
			"Confitec" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParcelamentoSaida.class)), description = "Informações calculadas para parcelamento"),
			@ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Erro.class)), description = "Requisição inválida"),
			@ApiResponse(responseCode = "405", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Erro.class)), description = "Método não suportado"),
			@ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Erro.class)), description = "Ocorreu um erro na API") })
	@PostMapping(value = "/parcelamento", produces = "application/json")
	public ResponseEntity<ParcelamentoSaida> parcelar(@Valid @RequestBody final ParcelamentoEntrada entrada) {
		log.debug("ParcelamentoSaida#parcelar | entrada: {}", entrada);

		return new ResponseEntity<>(parcelamentoService.parcelar(entrada), HttpStatus.OK);
	}
}