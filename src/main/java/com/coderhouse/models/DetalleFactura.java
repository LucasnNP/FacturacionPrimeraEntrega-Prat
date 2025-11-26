package com.coderhouse.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
	@JsonIgnoreProperties({"detalles", "cliente"}) // Para ignorar las relaciones internas
	private Factura factura;
	
	/*
	 * Muchos detalles pueden referirse al mismo producto. Es decir que el mismo producto puede aparecer en muchas facturas distintas, pero cada detalle hace referencia a un único producto.
	 */
	@ManyToOne
	@JoinColumn(name = "producto_id")
	@JsonIgnoreProperties({"detalles"})
	private Producto producto;
	
	@Column(name = "Cantidad")
	private int cantidad;
	
	@Column(name = "PrecioUnitario")
	private double precioUnitario;

	
}
