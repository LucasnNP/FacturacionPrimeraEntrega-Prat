package com.coderhouse.models;

import java.util.ArrayList;
import java.util.List;

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
	
	@OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
	private List<DetalleFactura> detalles = new ArrayList<>();

	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Producto(String descripcion, String codigo, int stock, double precio) {
		super();
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.stock = stock;
		this.precio = precio;
	}

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

	@Override
	public String toString() {
		return "Producto [id=" + id + ", descripcion=" + descripcion + ", codigo=" + codigo + ", stock=" + stock
				+ ", precio=" + precio + "]";
	}

	
}
