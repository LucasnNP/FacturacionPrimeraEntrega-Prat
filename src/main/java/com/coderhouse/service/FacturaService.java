package com.coderhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.DetalleFactura;
import com.coderhouse.models.Factura;
import com.coderhouse.models.Producto;
import com.coderhouse.repository.ClienteRepository;
import com.coderhouse.repository.FacturaRepository;
import com.coderhouse.repository.ProductoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Cada servicio implementa la interface CRUD usando su repositorio correspondiente
 * ademas este servicio se encarga de validar cliente, descontar stock, calcular total
 * y manejar los detalles en cascada.
 */
@Service
public class FacturaService implements CRUDInterface<Factura, Long> {
	
	private final String message = "Factura no encontrada";
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Factura> findAll() {
		return facturaRepository.findAll();
	}
	
	@Override
	public Factura findById(Long id) {
		return facturaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(message));
	}
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	@Transactional
	public Factura save(Factura nuevaFactura) {
	    // Validar cliente
	    if (nuevaFactura.getCliente() != null && nuevaFactura.getCliente().getId() != null) {
	        Cliente clienteExistente = clienteRepository.findById(nuevaFactura.getCliente().getId())
	                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
	        nuevaFactura.setCliente(clienteExistente);
	    } else {
	        throw new IllegalArgumentException("Debe indicar un cliente válido para la factura");
	    }

	    // Validar que tenga detalles
	    if (nuevaFactura.getDetalles() == null || nuevaFactura.getDetalles().isEmpty()) {
	        throw new IllegalArgumentException("La factura debe tener al menos un detalle");
	    }

	    // Guardar factura base (sin total todavía)
	    nuevaFactura.setFecha(java.time.LocalDateTime.now());
	    nuevaFactura.setTotal(0.0);
	    facturaRepository.save(nuevaFactura);

	    double totalFactura = 0.0;

	    // Procesar cada detalle
	    for (DetalleFactura detalle : nuevaFactura.getDetalles()) {
	        System.out.println("Procesando detalle: " + detalle);

	        if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
	            throw new IllegalArgumentException("El detalle no tiene producto asignado o el id es nulo");
	        }

	        Producto producto = productoRepository.findById(detalle.getProducto().getId())
	            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id: " + detalle.getProducto().getId()));

	        if (producto.getStock() < detalle.getCantidad()) {
	            throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getDescripcion());
	        }

	        double subtotal = producto.getPrecio() * detalle.getCantidad();
	        totalFactura += subtotal;

	        detalle.setPrecioUnitario(producto.getPrecio());
	        detalle.setProducto(producto);
	        detalle.setFactura(nuevaFactura);

	        producto.setStock(producto.getStock() - detalle.getCantidad());
	        productoRepository.save(producto);
	    }

	    // Actualizar total y guardar la factura final
	    nuevaFactura.setTotal(totalFactura);
	    return facturaRepository.save(nuevaFactura);
	}
		
	@Override
	@Transactional
	public Factura update(Long id, Factura facturaActualizada) {
		Factura factura = findById(id);
		//Actualiza cliente si corresponde
		if (facturaActualizada.getCliente() !=null && facturaActualizada.getCliente().getId() != null) {
			Cliente cliente = clienteRepository.findById(facturaActualizada.getCliente().getId()).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
			factura.setCliente(cliente);
		}
		//Si vienen detalles nuevos, los reemplazamos
		if (facturaActualizada.getDetalles() != null && !facturaActualizada.getDetalles().isEmpty()) {
			factura.getDetalles().clear(); // Eliminamos los anteriores
			double totalNuevo = 0.0;
			
			for (DetalleFactura nuevoDetalle : facturaActualizada.getDetalles()) {
				Producto producto = productoRepository.findById(nuevoDetalle.getProducto().getId()).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
				if (producto.getStock() < nuevoDetalle.getCantidad()) {
					throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getDescripcion());
				}
				double subtotal = producto.getPrecio() * nuevoDetalle.getCantidad();
				totalNuevo += subtotal;
				
				nuevoDetalle.setPrecioUnitario(producto.getPrecio());
				nuevoDetalle.setFactura(factura);
				
				// Descontar Stock Actualizado
				producto.setStock(producto.getStock() - nuevoDetalle.getCantidad());
				productoRepository.save(producto);
			}
			
			factura.setTotal(totalNuevo);
		}
		
		facturaRepository.save(factura);
		
		// Refrecamos el estado de la factura
		em.refresh(factura);
		
		return factura;
		
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		if (!facturaRepository.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		facturaRepository.deleteById(id);
	}

}
