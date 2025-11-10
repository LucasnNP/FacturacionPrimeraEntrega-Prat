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
import com.coderhouse.service.ProductoService;

/**
 * Invoca los métodos del servicio
 */

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping
	public ResponseEntity<List<Producto>> getAllProductos() {
		try {
			List<Producto> productos = productoService.findAll();
			return ResponseEntity.ok(productos); // 200 OK
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@GetMapping("/{productoId}")
	public ResponseEntity<Producto> getProductoById(@PathVariable Long productoId) {
		try {
			Producto producto = productoService.findById(productoId);
			if (producto != null)
				return ResponseEntity.ok(producto); // 200 OK
			else
				return ResponseEntity.notFound().build(); // Error 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); //Error 500
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
		try {
			Producto nuevo = productoService.save(producto);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // 201
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@PutMapping("/{productoId}")
	public ResponseEntity<Producto> updateProducto(@PathVariable Long productoId, @RequestBody Producto actualizado) {
		try {
			Producto producto = productoService.update(productoId, actualizado);
			if (producto != null)
				return ResponseEntity.ok(producto); // 200 OK
			else
				return ResponseEntity.notFound().build(); // Error 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@DeleteMapping("/{productoId}")
	public ResponseEntity<Void> deleteProducto(@PathVariable Long productoId) {
		try {
			productoService.deleteById(productoId);
			return ResponseEntity.noContent().build(); // 204
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}

}
