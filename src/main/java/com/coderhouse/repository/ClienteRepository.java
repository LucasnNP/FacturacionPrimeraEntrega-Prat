package com.coderhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Cliente;

/**
 * Interfaz que se comunica directamente con la db
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
