package com.coderhouse.models;

/*
 * Representa el detalle de una factura, relacionando producto y cantidad. Además es el punto de unión entre facturas y productos.
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DetalleFactura")
public class DetalleFactura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * Muchos detalles pertenecen a una sola factura. Es decir, una factura puede tener muchos ítems (detalles), pero cada detalle pertence a una sola factura.
	 */
	@ManyToOne
	@JoinColumn(name = "factura_id")
	private Factura factura;
	
	/*
	 * Muchos detalles pueden referirse al mismo producto. Es decir que el mismo producto puede aparecer en muchas facturas distintas, pero cada detalle hace referencia a un único producto.
	 */
	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Producto producto;
	
	@Column(name = "Cantidad")
	private int cantidad;
	
	@Column(name = "PrecioUnitario")
	private double precioUnitario;

	/*
	 * se declara el constructor de la superclaseclase.
	 */
	public DetalleFactura() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * se genera el constructor usando los campos de interés.
	 */
	public DetalleFactura(Producto producto, int cantidad, double precioUnitario) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}
	
	/*
	 * Se generan los Getters y Setters.
	 */
	public double calcularSubtotal() {
        return cantidad * precioUnitario;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
	
}
