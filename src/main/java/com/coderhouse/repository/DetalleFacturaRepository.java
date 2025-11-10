package com.coderhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.DetalleFactura;

/**
 * Interfaz que se comunica directamente con la db
 */
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {

}
