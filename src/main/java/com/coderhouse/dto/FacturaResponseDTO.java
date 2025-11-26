package com.coderhouse.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Para la success response
 */
@Getter
@Setter
public class FacturaResponseDTO {
	private Long facturaId;
	private LocalDateTime fecha;
	private int totalProductos; // Cantidad total vendida
	private double totalMonto; // Monto total

}
