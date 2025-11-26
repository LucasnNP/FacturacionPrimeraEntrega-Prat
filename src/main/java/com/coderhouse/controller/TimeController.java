package com.coderhouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.dto.TimeResponseDTO;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.service.TimeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/fecha")
@Tag(name = "Fecha y Hora", description = "Operaciones relacionadas con la obtención de fecha y hora del servidor")
public class TimeController {
	
	@Autowired
	private TimeService timeService;
	
	private int contadorDeInvocaciones = 0;
	
	private String ultimaFechaMostrada = "N/A";
	
	@Operation(summary = "Obtener fecha y hora actuales", description = "Devuelve la fecha y hora actual del servidor junto con información de estado interna")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Fecha obtebida correctamente", content = @Content(mediaType = "text/plain")),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			
	})
	@GetMapping
	public ResponseEntity<?> obtenerFechaActual() {
		
		try {
			contadorDeInvocaciones++;
		
			TimeResponseDTO fechaActual = timeService.obtenerFechaYHoraActual();
			
			String mensaje = String.format(
					"Fecha Actual: %s, %d-%02d-%02d %02d:%02d:%02d\n" +
			        "Número de Invocaciones: %d\n" +
			        "Última Fecha Mostrada: %s",
					fechaActual.getDayOfWeek(),
					fechaActual.getYear(),
					fechaActual.getMonth(),
					fechaActual.getDay(),
					fechaActual.getHour(),
					fechaActual.getMinute(),
					fechaActual.getSeconds(),
					contadorDeInvocaciones,
					ultimaFechaMostrada
			);
			
			ultimaFechaMostrada = String.format(
					"%s, %d-%02d-%02d %02d:%02d:%02d",
					fechaActual.getDayOfWeek(),
					fechaActual.getYear(),
					fechaActual.getMonth(),
					fechaActual.getDay(),
					fechaActual.getHour(),
					fechaActual.getMinute(),
					fechaActual.getSeconds()
			);
		
			return ResponseEntity.ok(mensaje);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error obteniendo fecha",  e.getMessage()));
		}
	}
	
}
