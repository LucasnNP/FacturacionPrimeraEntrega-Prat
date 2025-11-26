package com.coderhouse.models;

/*
 * Representa una factura de venta, vinculada a un cliente.
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	@OneToMany(mappedBy = "factura", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("factura")
	private List<DetalleFactura> detalles = new ArrayList<>();
	
	private LocalDateTime fecha = LocalDateTime.now();
	
	@Column(name= "Total", nullable = false)
	private double total;
	

}
