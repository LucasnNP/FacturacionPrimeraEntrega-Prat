package com.coderhouse.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.dto.FacturaRequestDTO;
import com.coderhouse.dto.FacturaResponseDTO;
import com.coderhouse.models.Factura;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.service.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Invoca los métodos del servicio
 */

@RestController
@RequestMapping("/api/facturas")
@Tag(name = "Gestión de Facturas", description = "Endpoints para gestionar Facturas")
public class FacturaController {
	
	@Autowired
	private FacturaService facturaService;
	
	@Operation(summary = "Obtener todas las facturas")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Listado obtebnido correctamente", content = {
				@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Factura.class)))
		}),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
				@Content(mediaType="application/json", schema = @Schema(implementation = ErrorResponse.class)))
		})
	@GetMapping
	public ResponseEntity<?> getAllFacturas() {
		try {
			List<Factura> facturas = facturaService.findAll();
			return ResponseEntity.ok(facturas); // 200 OK
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse(e.getMessage(), "Error interno del servicor");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}
	
	@Operation(summary = "Obtener factura por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Factura Encontrada", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = Factura.class))),
		@ApiResponse(responseCode = "404", description = "Factura no encontrada", content = 
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{facturaId}")
	public ResponseEntity<?> getFacturaById(
			@Parameter(description = "Identificador de la factrua", example = "3", required = true)
			@PathVariable Long facturaId) {
		try {
			Factura factura = facturaService.findById(facturaId);
			return ResponseEntity.ok(factura); // 200 OK
		} catch (IllegalArgumentException e) {
			ErrorResponse error = new ErrorResponse("404", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // Error 404
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse(e.getMessage(), "Error interno del servidor");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error); //Error 500
		}
	}
	
	@Operation(summary = "Crear una factura con sus líneas y actualizar stock")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Factura creada correctamente", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = Factura.class))),
			@ApiResponse(responseCode = "400", description = "Datos inválidos o errores comerciales", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error Interno del Servidor", content =
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/create")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Datos necesarios para crear una factura con sus detalles",
			required = true,
			content = @Content(
					mediaType = "application/json",
					examples = @ExampleObject(
							name = "Factura ejemplo",
							value = """
										{
											"cliente": {"clienteid":1},
											"lineas":[
												{"producto":{"productoid":2},"cantidad":3},
												{"producto":{"productoid":1},"cantidad":1}
											]
										}
									"""
						),
					schema = @Schema(implementation = FacturaResponseDTO.class)
				)
	)
	public ResponseEntity<?> createFactura(@RequestBody FacturaRequestDTO dto) {
		try {

			List<String> errores = new ArrayList<>();

			Factura factura = facturaService.crearFacturaConDetalles(dto, errores);

			if(!errores.isEmpty() || factura == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
			}

			return ResponseEntity.status(HttpStatus.CREATED).body(factura);

		} catch (Exception e) {

			ErrorResponse error = new ErrorResponse(e.getMessage(), "Error interno del servidor");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}
}
