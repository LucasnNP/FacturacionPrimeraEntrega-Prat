package com.coderhouse.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Modelo de Error para respuestas personalizadas")
public class ErrorResponse {
	
	@Schema(description = "Mensaje principal del error", example = "Error 404")
	private String message;
	
	@Schema(description = "Detalles del error", example = "Factura no encontrada")
	private String detail;

}
