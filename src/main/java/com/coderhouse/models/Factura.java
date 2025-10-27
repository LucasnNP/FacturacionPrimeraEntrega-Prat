package com.coderhouse.models;

/*
 * Representa una factura de venta, vinculada a un cliente.
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Facturas")
public class Factura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * En la entidad Factura tenemos la relación de muchas facturas -> un cliente, representada en la base de datos con la columna cliente_id.
	 * Muchas facturas pueden pertencer al mismo cliente, pero cada factura pertenece a un solo cliente.
	 */
	@ManyToOne 
	@JoinColumn(name = "cliente_id") 
	private Cliente cliente; //Cada factura está asociada a un cliente específico.
	
	/*
	 * UnaFactura puede tener muchos detalles.
	 * La lista de detalles estará mapeada por el campo factura dentro de la clase DetalleFactura.
	 */
	@OneToMany(mappedBy = "factura", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<DetalleFactura> detalles = new ArrayList<>();
	
	private LocalDateTime fecha = LocalDateTime.now();
	
	@Column(name= "Total", nullable = false)
	private double total;

	/*
	 * se declara el constructor de la superclaseclase.
	 */
	public Factura() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * se genera el constructor usando los campos de interés.
	 */
	public Factura(Cliente cliente, double total) {
		super();
		this.cliente = cliente;
		this.total = total;
	}

	/*
	 * Se generan los Getters y Setters.
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<DetalleFactura> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFactura> detalles) {
		this.detalles = detalles;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	/*
	 * Se genera el toString.
	 */
	@Override
	public String toString() {
		return "Factura [id=" + id + ", cliente=" + cliente.getNombre() + ", total=" + total + ", fecha=" + fecha + "]";
	}
	
	

}
