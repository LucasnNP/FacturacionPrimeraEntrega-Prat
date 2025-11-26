package com.coderhouse.dto;

import java.util.List;

import lombok.Data;

@Data
public class FacturaRequestDTO {
	
	private ClienteDTO cliente;
	private List<LineaDTO> lineas;
	
	@Data
	public static class ClienteDTO {
		private Long clienteid;
		}
	
	@Data
	public static class LineaDTO {
		private int cantidad;
		private ProductoDTO producto;
	}
	
	@Data
	public static class ProductoDTO {
		private Long productoid;
	}
}
