package com.coderhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.DetalleFactura;
import com.coderhouse.repository.DetalleFacturaRepository;

import jakarta.transaction.Transactional;

/**
 * Cada servicio implementa la interface CRUD usando su repositorio correspondiente
 */

@Service
public class DetalleFacturaService implements CRUDInterface<DetalleFactura, Long> {
	
	private final String message = "Detalle de factura no encontrado";
	
	@Autowired
	private DetalleFacturaRepository detalleFacturaRepository;
	
	@Override
	public List<DetalleFactura> findAll() {
		return detalleFacturaRepository.findAll();
	}
	
	@Override
	public DetalleFactura findById(Long id) {
		return detalleFacturaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(message));
	}
	
	@Override
	public DetalleFactura save(DetalleFactura nuevoDetalle) {
		return detalleFacturaRepository.save(nuevoDetalle);
	}
	
	@Override
	@Transactional
	public DetalleFactura update(Long id, DetalleFactura detalleActualizado) {
		DetalleFactura detalle = findById(id);
		
		if (detalleActualizado.getCantidad() != 0) {
			detalle.setCantidad(detalleActualizado.getCantidad());
		}
		
		if (detalleActualizado.getPrecioUnitario() != 0) {
			detalle.setPrecioUnitario(detalleActualizado.getPrecioUnitario());
		}
		
		if (detalleActualizado.getProducto() != null) {
			detalle.setProducto(detalleActualizado.getProducto());
		}
		
		if (detalleActualizado.getFactura() != null) {
			detalle.setFactura(detalleActualizado.getFactura());
		}
		
		return detalleFacturaRepository.save(detalle);
	}
	
	@Override
	public void deleteById(Long id) {
		if (!detalleFacturaRepository.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		detalleFacturaRepository.deleteById(id);
	}
}
