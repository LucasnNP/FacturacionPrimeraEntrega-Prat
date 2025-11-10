package com.coderhouse.interfaces;

import java.util.List;

/**
 * Itefaz genérica para operaciones CRUD básicas
 * 
 * @param <T> Tipo de entidad (por ejemplo Cliente, Producto,etc.)
 * @param <ID> Tipo de identificador (por ejemplo Long)
 */

public interface CRUDInterface<T,ID> {
	
	List<T> findAll();
	
	T findById(ID id);
	
	T save(T entity);
	
	T update(ID id, T entity);
	
	void deleteById(ID id);
}
