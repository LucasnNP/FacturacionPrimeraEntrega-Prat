package com.coderhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Producto;
import com.coderhouse.repository.ProductoRepository;

import jakarta.transaction.Transactional;

/**
 * Cada servicio implementa la interface CRUD usando su repositorio correspondiente
 */

@Service
public class ProductoService implements CRUDInterface<Producto, Long> {
	
	private final String message = "Producto no encontrado";
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}
	
	@Override
	public Producto findById(Long id) {
		return productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(message));
	}
	
	@Override
	public Producto save(Producto nuevoProducto) {
		return productoRepository.save(nuevoProducto);
	}
	
	@Override
	@Transactional
	public Producto update(Long id, Producto productoActualizado) {
		Producto producto =findById(id);
		
		if (productoActualizado.getDescripcion() != null && !productoActualizado.getDescripcion().isEmpty()) {
			producto.setDescripcion(productoActualizado.getDescripcion());
		}
		
		if (productoActualizado.getCodigo() != null && !productoActualizado.getCodigo().isEmpty()) {
			producto.setCodigo(productoActualizado.getCodigo());
		}
		
		if (productoActualizado.getPrecio() != 0) {
			producto.setPrecio(productoActualizado.getPrecio());
		}
		
		if (productoActualizado.getStock() != 0) {
			productoActualizado.setStock(productoActualizado.getStock());
		}
		
		return productoRepository.save(producto);
	}
	
	@Override
	public void deleteById(Long id) {
		if (!productoRepository.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		productoRepository.deleteById(id);
	}

}
