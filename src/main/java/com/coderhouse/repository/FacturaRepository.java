package com.coderhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Factura;

/**
 * Interfaz que se comunica directamente con la db
 */
public interface FacturaRepository extends JpaRepository<Factura, Long> {

}
