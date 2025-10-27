package com.coderhouse.dao;

/*
 * Es el encargado de manejar la información y trasladarla a la db.
 */

import org.springframework.stereotype.Service;

import com.coderhouse.models.Cliente;
import com.coderhouse.models.Factura;
import com.coderhouse.models.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service //Este objeto es el servicio que me permite el manejo de la información
public class DaoFactory {
	
	@PersistenceContext
	private EntityManager em; // entidad encargda de manejar las entidades dandole el contexto de persistencia
	
	@Transactional // Método par obtener feedback de la base de datos
	public void persistirCliente(Cliente cliente) {
		em.persist(cliente);
	}
	
	@Transactional
	public void persistirProducto(Producto producto) {
		em.persist(producto);
	}
	
	@Transactional
	public void persistirFactura(Factura factura) {
		em.persist(factura);
	}

}
