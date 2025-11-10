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

import com.coderhouse.models.DetalleFactura;
import com.coderhouse.service.DetalleFacturaService;

/**
 * Invoca los métodos del servicio
 */

@RestController
@RequestMapping("/api/detalles")
public class DetalleFacturaController {
	
	@Autowired
	private DetalleFacturaService detalleService;
	
	@GetMapping
	public ResponseEntity<List<DetalleFactura>> getAllDetalles() {
		try {
			List<DetalleFactura> detalles = detalleService.findAll();
			return ResponseEntity.ok(detalles); // 200 OK
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); //Error 500
		}
	}
	
	@GetMapping("/{detalleId}")
	public ResponseEntity<DetalleFactura> getDetalleById(@PathVariable Long detalleId) {
		try {
			DetalleFactura detalle = detalleService.findById(detalleId);
			if (detalle != null)
				return ResponseEntity.ok(detalle); // 200 OK
			else
				return ResponseEntity.notFound().build(); // Error 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<DetalleFactura> createDetalle(@RequestBody DetalleFactura detalle) {
		try {
			DetalleFactura nuevo = detalleService.save(detalle);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // 201
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); //Error 500
		}
	}
	
	@PutMapping("/{detalleID}")
	public ResponseEntity<DetalleFactura> updateDetalle(@PathVariable Long detalleId, @RequestBody DetalleFactura actualizado) {
		try {
			DetalleFactura detalle = detalleService.update(detalleId, actualizado);
			if (detalle != null)
				return ResponseEntity.ok(detalle); // 200 OK
			else
				return ResponseEntity.notFound().build(); //Error 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@DeleteMapping("/{detalleId}")
	public ResponseEntity<Void> deleteDEtalle(@PathVariable Long detalleId) {
		try {
			detalleService.deleteById(detalleId);
			return ResponseEntity.noContent().build(); // 204 No Content
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}

}
