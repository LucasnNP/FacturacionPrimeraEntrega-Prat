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
import com.coderhouse.service.ClienteService;

/**
 * Invoca los métodos del servicio
 */

@RestController
@RequestMapping("/api/Clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> getAllClientes() {
		try {
			List<Cliente> clientes = clienteService.findAll();
			return ResponseEntity.ok(clientes); // 200 OK
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable Long clienteId){
		try {
			Cliente cliente = clienteService.findById(clienteId);
			if (cliente != null)
				return ResponseEntity.ok(cliente); // 200 OK
			else
				return ResponseEntity.notFound().build(); // Error 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
		try {
			Cliente nuevoCliente = clienteService.save(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente); // 201
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable Long clienteId, @RequestBody Cliente clienteActualizado) {
		try {
			Cliente cliente = clienteService.update(clienteId, clienteActualizado);
			if (cliente != null)
				return ResponseEntity.ok(cliente); // 200 OK
			else
				return ResponseEntity.notFound().build(); // Error 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); //Error 500
		}
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> deleteCliente(@PathVariable Long clienteId) {
		try {
			clienteService.deleteById(clienteId);
			return ResponseEntity.noContent().build(); // 204 No Content
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); //Error 500
		}
	}

}
