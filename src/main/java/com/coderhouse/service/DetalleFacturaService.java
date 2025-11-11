package com.coderhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.DetalleFactura;
import com.coderhouse.repository.DetalleFacturaRepository;


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
	
	//No se permiten operaciones de escritura directos en los detalles
	@Override
	public DetalleFactura save(DetalleFactura entity) {
			throw new UnsupportedOperationException("Los detalles solo se crean mediante la factura.");
		}
		
	@Override
	public DetalleFactura update(Long id, DetalleFactura entity) {
		throw new UnsupportedOperationException("Los detalles solo se actualizan mediante la factura.");
	}
	
	@Override
	public void deleteById(Long id) {
		throw new UnsupportedOperationException("Los detalles solo se eliminan mediante la factura.");
	}
}
