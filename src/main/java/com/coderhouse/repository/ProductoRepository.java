package com.coderhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Producto;

/**
 * Interfaz que se comunica directamente con la db
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
