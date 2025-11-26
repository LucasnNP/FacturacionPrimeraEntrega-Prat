package com.coderhouse.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dto.FacturaRequestDTO;
import com.coderhouse.dto.TimeResponseDTO;
import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.DetalleFactura;
import com.coderhouse.models.Factura;
import com.coderhouse.models.Producto;
import com.coderhouse.repository.ClienteRepository;
import com.coderhouse.repository.DetalleFacturaRepository;
import com.coderhouse.repository.FacturaRepository;
import com.coderhouse.repository.ProductoRepository;

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
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private DetalleFacturaRepository detalleRepository;
	
	@Autowired
	private TimeService timeService;
	
	@Override
	public List<Factura> findAll() {
		return facturaRepository.findAll();
	}
	
	@Override
	public Factura findById(Long id) {
		return facturaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(message));
	}
	
	@Override
	public Factura save(Factura entity) {
		throw new UnsupportedOperationException("Usar crearFacturaConDetalles() para crear facturas válidas");
	}
	
	/**
	 * Las facturas no se actualzan una vez emitidas
	 */
	@Override
	public Factura update(Long id, Factura entity) {
		throw new UnsupportedOperationException("Una factura no puede actualizarse una vez creada");
	}
	
	/**
	 * Por motivos fiscales no esta permitido borrar facturas
	 */
	@Override
	public void deleteById(Long id) {
		throw new UnsupportedOperationException("Una factura no puede eliminarse");
	}
	
	/**
	 * Obtener fecha desde API
	 */
	private LocalDateTime obtenerFechaDesdeTimeService() {
		
		TimeResponseDTO time = timeService.obtenerFechaYHoraActual();
		
		if (time == null) {
			return LocalDateTime.now();
		}
		
		try {
			return LocalDateTime.of(
					time.getYear(),
					Month.of(time.getMonth()),
					time.getDay(),
					time.getHour(),
					time.getMinute(),
					time.getSeconds()
			);
		} catch (Exception e) {
			return LocalDateTime.now();
		}
	}
	
	@Transactional
	public Factura crearFacturaConDetalles(FacturaRequestDTO req, List<String> errores) {
		
		// Validación request
		if (req == null|| req.getCliente() == null) {
			errores.add("La factura debe tener al menos una línea");
			return null;
		}
		
		// Validación cliente
		Long clienteId = req.getCliente().getClienteid();
		Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
		
		if (cliente == null) {
			errores.add("El cliente con ID " + clienteId + " no existe.");
			return null;
		}
		
		// crear factura base
		Factura factura = new Factura();
		factura.setCliente(cliente);
		factura.setFecha(obtenerFechaDesdeTimeService());
		factura.setTotal(0.0);
		
		double totalFactura = 0.0;
		
		List<DetalleFactura> detallesAGuardar = new ArrayList<>();
		
		// Procesar líneas
		for (FacturaRequestDTO.LineaDTO linea : req.getLineas()) {
			
			if (linea == null || linea.getProducto() == null) {
				errores.add("Línea inválida: falta el producto");
				continue;
			}
			
			Long productoId = linea.getProducto().getProductoid();
			int cantidad = linea.getCantidad();
			
			Producto producto = productoRepository.findById(productoId).orElse(null);
			
			if (producto == null) {
				errores.add("El producto con ID "+ productoId + " noexiste.");
				continue;
			}
			
			if (cantidad <= 0) {
				errores.add("Cantidad incválida para el producto ID " + productoId);
				continue;
			}
			
			if (producto.getStock() < cantidad) {
				errores.add("Stock insuficiente para el producto ID" + productoId);
				continue;
			}
			
			// Descuento Stock (regla de negocio)
			producto.setStock(producto.getStock() - cantidad);
			productoRepository.save(producto);
			
			// Congelar precio histórico
			double precioUnitario = producto.getPrecio();
			double subtotal = precioUnitario * cantidad;
			totalFactura += subtotal;
			
			// Crear detalle
			DetalleFactura detalle = new DetalleFactura();
			detalle.setFactura(factura);
			detalle.setProducto(producto);
			detalle.setCantidad(cantidad);
			detalle.setPrecioUnitario(precioUnitario);
			
			detallesAGuardar.add(detalle);
		}
		
		// Si hay errores, abortar transacción
		if (!errores.isEmpty()) {
			return null;
		}
		
		// Guardar factura
		factura.setTotal(totalFactura);
		Factura facturaGuardada = facturaRepository.save(factura);
		
		// Guardar detalles
		for (DetalleFactura d : detallesAGuardar) {
			d.setFactura(facturaGuardada);
			detalleRepository.save(d);
		}
		
		facturaGuardada.setDetalles(detallesAGuardar);
		
		return facturaGuardada;
	}
	
}
