package com.coderhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Factura;
import com.coderhouse.repository.FacturaRepository;

import jakarta.transaction.Transactional;

/**
 * Cada servicio implementa la interface CRUD usando su repositorio correspondiente
 */
@Service
public class FacturaService implements CRUDInterface<Factura, Long> {
	
	private final String message = "Factura no encontrada";
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	@Override
	public List<Factura> findAll() {
		return facturaRepository.findAll();
	}
	
	@Override
	public Factura findById(Long id) {
		return facturaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(message));
	}
	
	@Override
	public Factura save(Factura nuevaFactura) {
		return facturaRepository.save(nuevaFactura);
	}
	
	@Override
	@Transactional
	public Factura update(Long id, Factura facturaActualizada) {
		Factura factura = findById(id);
		
		if (facturaActualizada.getCliente() !=null) {
			factura.setCliente(facturaActualizada.getCliente());
		}
		
		if (facturaActualizada.getDetalles() != null && !facturaActualizada.getDetalles().isEmpty()) {
			factura.setDetalles(facturaActualizada.getDetalles());
		}
		
		if (facturaActualizada.getTotal() != 0) {
			factura.setTotal(facturaActualizada.getTotal());
		}
		
		return facturaRepository.save(factura);
	}
	
	@Override
	public void deleteById(Long id) {
		if (!facturaRepository.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		facturaRepository.deleteById(id);
	}

}
