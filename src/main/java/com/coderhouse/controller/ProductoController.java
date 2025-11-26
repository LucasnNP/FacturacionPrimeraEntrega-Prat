package com.coderhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.models.Producto;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.service.ProductoService;

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
@RequestMapping("/api/productos")
@Tag(name = "Gestión de Productos", description = "Operaciones relacionadas a la gestión de productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@Operation(summary = "Obtener lista de productos", description = "Devuelve una lista completa de productos registrados")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listado obtenido correctamente", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping
	public ResponseEntity<?> getAllProductos() {
		try {
			List<Producto> productos = productoService.findAll();
			return ResponseEntity.ok(productos); // 200 OK
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error Interno", e.getMessage())); // Error 500
		}
	}
	
	@Operation(summary = "Obtener producto por ID", description = "Busca un producto por su identificador único")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto encontrado", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado", content = 
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Error interno del servidor", content = 
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	@GetMapping("/{productoId}")
	public ResponseEntity<?> getProductoById(
			@Parameter(description = "ID del producto a buscar", example = "1")
			@PathVariable Long productoId) {
		try {
			Producto producto = productoService.findById(productoId);
			return ResponseEntity.ok(producto); // 200 OK
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Producto no encontrado", e.getMessage())); // Error 404
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor", e.getMessage())); //Error 500
		}
	}
	
	@Operation(summary = "Crear un producto nuevo", description = "Registra un nuevo producto en el sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Producto creado correctamente", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
			@ApiResponse(responseCode = "409", description = "Conflicto al crear el producto", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	@PostMapping("/create")
	public ResponseEntity<?> createProducto(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Datos del nuevo producto",
					required = true,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
			)
			@RequestBody Producto producto) {
		
		try {
			Producto nuevo = productoService.save(producto);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // 201
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Conflicto", e.getMessage())); // Error 409
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor", e.getMessage())); // Error 500
		}
	}
	
	@Operation(summary = "Actualizar un producto existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto actualizado correctamente", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "409", description = "Conflicto al actualizar el producto", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
			@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PutMapping("/{productoId}")
	public ResponseEntity<?> updateProducto(
			@Parameter(description = "ID del producto a actualizar", example = "1")
			@PathVariable Long productoId,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Datos actualizados del producto",
					required = true,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
			)
			@RequestBody Producto actualizado) {
		try {
			Producto producto = productoService.update(productoId, actualizado);
			return ResponseEntity.ok(producto); // 200 OK
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Conflicto", e.getMessage())); // Error 409
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Producto no encontrado", e.getMessage())); // Error 404
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor", e.getMessage())); // Error 500
		}
	}
	
	@Operation(summary = "Eliminar un producto por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/{productoId}")
	public ResponseEntity<?> deleteProducto(
			@Parameter(description = "ID del producto a eliminar", example = "1")
			@PathVariable Long productoId) {
		try {
			productoService.deleteById(productoId);
			return ResponseEntity.noContent().build(); // 204
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Producto np encontrado", e.getMessage())); // Error 404
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor", e.getMessage())); // Error 500
		}
	}

}
