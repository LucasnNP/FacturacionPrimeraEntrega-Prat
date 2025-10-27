# 🛒 EcommerceProject - Coderhouse

## 📘 Descripción general

**EcommerceProject** es una aplicación Java desarrollada con **Spring Boot**, **JPA (Hibernate)** y **MySQL**, como parte del curso de **Java** en **Coderhouse**.  
El objetivo es construir un sistema que permita **administrar las ventas de un comercio**, aplicando los conceptos aprendidos durante el curso:  
control de flujos, objetos y métodos, herencia, excepciones, clases abstractas, uso de bases de datos, y persistencia con JPA.

La aplicación está pensada para:  
manejar **clientes**, **productos**, **facturas** y **detalles de factura**, modelando sus relaciones con precisión a nivel de base de datos y en el código Java.

---

## 🧠 Lógica del modelo de datos

El proyecto se centra en **cuatro entidades principales** según sugerencia del curso, interconectadas entre sí:

### 1️⃣ Cliente
Representa a la persona que realiza compras en el comercio.  
Cada cliente puede tener **una o más facturas** asociadas.

```java
@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
private List<Factura> facturas;
```
📌Relación: **Un cliente ➡️ muchas facturas (OneToMany)**

### 2️⃣ Factura
Representa una venta realizada a un cliente.
Cada factura pertenece a **un único cliente**, pero contiene **muchos productos** a través de sus detalles.

```java
@ManyToOne
@JoinColumn(name = "cliente_id")
private Cliente cliente;

@OneToMany(mappedBy = "factura", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
private List<DetalleFactura> detalles;
```
📌Relaciones: 
- **Muchas facturas ➡️ un cliente (ManyToOne)**
- **Una factura ➡️ muchos detalles (OneToMany)**

### 3️⃣ Producto
Representa un artículo que el comercio tiene disponible para la venta.
Un producto puede estar presente en muchas facturas diferentes, por lo que su relación se define a través de DetalleFactura.

```java
@OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
private List<DetalleFactura> detalles;
```
📌Relación: **Un producto ➡️ muchos detalles (OneToMany)**

### 4️⃣ DetalleFactura
Es la **entidad intermedia** entre Factura y Producto.
Cada DetalleFactura representa una línea de la factura, indicando:
- Qué producto se vendió
- Cuantas unidades
- A qué precio unitario

```java
@ManyToOne
@JoinColumn(name = "factura_id")
private Factura factura;

@ManyToOne
@JoinColumn(name = "producto_id")
private Producto producto;
```
📌Relaciones: 
- **Muchos detalles ➡️ una factura (ManyToOne)**
- **Muchos detalles ➡️ un producto (ManyToOne)**
De esta manera, se crea la relación **muchos a muchos** entre Factura y Producto.

---

## 🚀 Ejecución
1. Cloná o descargá el repositorio.
2. Abrí el proyecto enEclipse como Maven Project.
3. Asegurate de tener MySQL en ejecución.
4. Ejcutá la aplicación con: Run As → Spring Boot App.

---

## 🧩 Tecnologías utilizadas
- Java 21
- Spring Boot
- JPA / Hibernate
- MySQL
- Maven

---

## 🧠 Conceptos aplicados
- Control de flujos y estructuras iterativas.
- Programación orientada a objetos.
- Clases e instancias, atributos y métodos.
- Relaciones entre clases.
- Manejo de excepciones.
- Persistencia con JPA u Hibernate.
- Configuración de base de datos.
- Integración de DAO y modelo de negocio.

---

## ✍️ Autor
**Lucas Nicolás Prat**
- 📘 Curso de Java - Coderhouse
- 📅 Año: 2025
