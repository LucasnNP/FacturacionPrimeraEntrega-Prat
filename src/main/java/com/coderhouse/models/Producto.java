package com.coderhouse.models;

/*
 * Representa un producto disponible para la venta.
 */

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Descripcion", nullable = false)
	private String descripcion;
	
	@Column(name = "Codigo", nullable = false, unique = true)
	private String codigo;
	
	@Column(name = "Stock", nullable = false)
	private int stock;
	
	@Column(name = "Precio", nullable = false)
	private double precio;
	
	/*
	 * Un producto puede aparecer en muchos detalles.
	 * La lista de detalles estará mapeada por el campo producto dentro de la clase DetalleFactura.
	 */
	@OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"producto"}) // Evitar reursividad inversa
	private List<DetalleFactura> detalles = new ArrayList<>();

	/*
	 * se genera el constructor de la superclaseclase.
	 */
	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * se genera el constructor usando los campos de interés.
	 */
	public Producto(String descripcion, String codigo, int stock, double precio) {
		super();
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.stock = stock;
		this.precio = precio;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public List<DetalleFactura> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFactura> detalles) {
		this.detalles = detalles;
	}

	/*
	 * Se genera el toString.
	 */
	@Override
	public String toString() {
		return "Producto [id=" + id + ", descripcion=" + descripcion + ", codigo=" + codigo + ", stock=" + stock
				+ ", precio=" + precio + "]";
	}

	
}
