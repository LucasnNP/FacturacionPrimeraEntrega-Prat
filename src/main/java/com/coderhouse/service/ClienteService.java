package com.coderhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Cliente;
import com.coderhouse.repository.ClienteRepository;

import jakarta.transaction.Transactional;

/**
 * Cada servicio implementa la interface CRUD usando su repositorio correspondiente
 */

@Service
public class ClienteService implements CRUDInterface<Cliente, Long> {
	
	private final String message = "Cliente no encontrado";
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	@Override
	public Cliente findById(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(message));
	}
	
	@Override
	public Cliente save(Cliente nuevoCliente) {
		return clienteRepository.save(nuevoCliente);
	}
	
	@Override
	@Transactional
	public Cliente update(Long id, Cliente clienteActualizado){
		Cliente cliente = findById(id);
		
		if (clienteActualizado.getNombre() != null && clienteActualizado.getNombre().isEmpty()) {
			cliente.setNombre(clienteActualizado.getNombre());
		}
		
		if (clienteActualizado.getApellido() != null && !clienteActualizado.getApellido().isEmpty()) {
			cliente.setApellido(clienteActualizado.getApellido());
		}
		
		if (clienteActualizado.getDni() !=0) {
			cliente.setDni(clienteActualizado.getDni());
		}
		
		if (clienteActualizado.getEmail() != null && !clienteActualizado.getEmail().isEmpty()) {
			cliente.setEmail(clienteActualizado.getEmail());
		}
		
		return clienteRepository.save(cliente);
	}
	
	@Override
	public void deleteById(Long id) {
		if (!clienteRepository.existsById(id)) {
			throw new IllegalArgumentException(message);
			}
		clienteRepository.deleteById(id);
	}
	
}
