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

	
}
