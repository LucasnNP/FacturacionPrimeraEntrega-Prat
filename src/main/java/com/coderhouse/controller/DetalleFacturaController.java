package com.coderhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.models.DetalleFactura;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.service.DetalleFacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Invoca los métodos del servicio
 */

@RestController
@RequestMapping("/api/detalles")
@Tag(name = "Gestión de Detalles de Factura", description = "Endpoints de solo lectura para consultar detalles de facturas")
public class DetalleFacturaController {
	
	@Autowired
	private DetalleFacturaService detalleService;
	
	@Operation(summary = "Obtener lista de datos de todos los detalles", description = "Devuelve la lista completa de detalles. No se pueden crear, modificar ni borrar.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listado obtenido correctamente", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = DetalleFactura.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	@GetMapping
	public ResponseEntity<?> getAllDetalles() {
		try {
			List<DetalleFactura> detalles = detalleService.findAll();
			return ResponseEntity.ok(detalles); // 200 OK
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor", e.getMessage())); //Error 500
		}
	}
	
	@Operation(summary = "Obtener un detalle por ID", description = "Devuelve un único detalle. No se pueden crear, modificar ni borrar.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Detalle encontrado correctamente", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = DetalleFactura.class))),
			@ApiResponse(responseCode = "404", description = "Detalle no encontrado", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
			@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{detalleId}")
	public ResponseEntity<?> getDetalleById(
			@Parameter(description = "ID del detalle a buscar", example = "1")
			@PathVariable Long detalleId) {
		try {
			DetalleFactura detalle = detalleService.findById(detalleId);
			return ResponseEntity.ok(detalle); // 200 OK
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Detalle no encontrado", e.getMessage())); // Error 404
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor", e.getMessage())); // Error 500
		}
	}
}
	