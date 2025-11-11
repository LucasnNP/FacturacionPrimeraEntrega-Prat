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

import com.coderhouse.models.Factura;
import com.coderhouse.service.FacturaService;

/**
 * Invoca los métodos del servicio
 */

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {
	
	@Autowired
	private FacturaService facturaService;
	
	@GetMapping
	public ResponseEntity<List<Factura>> getAllFacturas() {
		try {
			List<Factura> facturas = facturaService.findAll();
			return ResponseEntity.ok(facturas); // 200 OK
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); //Error 500
		}
	}
	
	@GetMapping("/{facturaId}")
	public ResponseEntity<Factura> getFacturaById(@PathVariable Long facturaId) {
		try {
			Factura factura = facturaService.findById(facturaId);
			if (factura != null)
				return ResponseEntity.ok(factura); // 200 OK
			else
				return ResponseEntity.notFound().build(); // Error 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); //Error 500
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createFactura(@RequestBody Factura factura) {
	    try {
	        Factura nueva = facturaService.save(factura);
	        return ResponseEntity.status(HttpStatus.CREATED).body(nueva); // 201
	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("Error al crear factura: " + e.getMessage());
	    }
	}
	
	@PutMapping("/{facturaId}")
	public ResponseEntity<Factura> updateFactura(@PathVariable Long facturaId, @RequestBody Factura actualizada) {
		try {
			Factura factura = facturaService.update(facturaId, actualizada);
			if (factura != null)
				return ResponseEntity.ok(factura); // 200 OK
			else
				return ResponseEntity.notFound().build(); // Error 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@DeleteMapping("/{facturaId}")
	public ResponseEntity<Void> deleteFactura(@PathVariable Long facturaId) {
		try {
			facturaService.deleteById(facturaId);
			return ResponseEntity.noContent().build(); // 204 No Content
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}

}
