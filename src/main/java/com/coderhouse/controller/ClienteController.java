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

import com.coderhouse.models.Cliente;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.service.ClienteService;

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
@RequestMapping("/api/Clientes")
@Tag(name = "Gestión de Clientes", description = "Operaciones relacionadas a la gestión de clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Operation(summary = "Obtener lista de todos los clientes", description = "Devuelve una lista con todos los clientes registrados")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Listado obtenido correctamente", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "500", description = "error interno del servidor", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping
	public ResponseEntity<?> getAllClientes() {
		try {
			List<Cliente> clientes = clienteService.findAll();
			return ResponseEntity.ok(clientes); // 200 OK
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error Interno", e.getMessage())); // Error 500
		}
	}
	
	@Operation(summary = "Obtener cliente por ID", description = "Busca un cliente por su identificador único")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Cliente encontrado", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "404", description = "cliente no encontrado", content = 
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{clienteId}")
	public ResponseEntity<?> getClienteById(
			@Parameter(description = "ID del cliente a buscar", example = "1")
			@PathVariable Long clienteId) {
		try {
			Cliente cliente = clienteService.findById(clienteId);
			return ResponseEntity.ok(cliente); // 200 OK
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Cliente no encontrado", e.getMessage())); // Error 404
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor", e.getMessage())); // Error 500
		}
	}
	
	@Operation(summary = "Crear un cliente nuevo", description = "Registra un nuevo cliente en el sistema")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Cliente creado correctamente", content = 
				@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "409", description = "Error al intentar crear el Alumno - CONFLICT", content = 
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/create")
	public ResponseEntity<?> createCliente(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Datos del nuevo cliente",
					required = true,
					content = @Content(schema = @Schema(implementation = Cliente.class)))
			@RequestBody Cliente cliente) {
		try {
			Cliente nuevoCliente = clienteService.save(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente); // 201
		} catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Conflicto", e.getMessage())); //error 409
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor", e.getMessage())); // Error 500
		}
	}
	
	@Operation(summary = "Actualizar cliente existente")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Cliente actualizado", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
		@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "409", description = "Error al actualizar el Cliente, conflicto de Datos", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor", content =
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	@PutMapping("/{clienteId}")
	public ResponseEntity<?> updateCliente(
			@Parameter(description = "ID del cliente a actualizar", example = "1")
			@PathVariable Long clienteId,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Datos actualizados del cliente",
					required = true,
					content = @Content(schema = @Schema(implementation = Cliente.class))
			)
			@RequestBody Cliente clienteActualizado) {
		try {
			Cliente cliente = clienteService.update(clienteId, clienteActualizado);
			return ResponseEntity.ok(cliente); // 200 OK	
		}catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Conflicto", e.getMessage()));	// 409
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Error 404", e.getMessage()));	// Error 404
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error Interno del Servidor", e.getMessage())); // Error 500
		}
	}
	
	@Operation(summary = "Eliminar un cliente por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = 
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<?> deleteCliente(
			@Parameter(description = "ID del cliente a eliminar", example = "1")
			@PathVariable Long clienteId) {
		try {
			clienteService.deleteById(clienteId);
			return ResponseEntity.noContent().build(); // 204 No Content
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Cliente no encontrado", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error interno del servidor", e.getMessage())); //Error 500
		}
	}

}
