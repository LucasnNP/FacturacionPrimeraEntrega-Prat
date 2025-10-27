package com.coderhouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coderhouse.dao.DaoFactory;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.Producto;



@SpringBootApplication
public class EcommerceProjectApplication implements CommandLineRunner {
	
	@Autowired
	private DaoFactory dao;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceProjectApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		try {
			Producto prod1 = new Producto("Yerba Mate", "YB001", 50, 1500);
			Producto prod2 = new Producto("Café", "CF001", 100, 2000);
			Producto prod3 = new Producto("Azúcar", "AZ003", 70, 1200);
			
			Cliente cli1 = new Cliente("Lucas", "Prat", 32978097, "lucas@email.com");
			Cliente cli2 = new Cliente("Ana", "González", 23456789, "ana@email.com");
			
			dao.persistirProducto(prod1);
			dao.persistirProducto(prod2);
			dao.persistirProducto(prod3);
			dao.persistirCliente(cli1);
			dao.persistirCliente(cli2);
			
			System.out.println("✅ Datos cargados correctamente en la base de datos.");
			
		} catch (Exception err) {
			System.out.println("❌ Error al iniciar: " + err.getMessage());
		}
		
	}

}
