# 🛒 EcommerceProject - Coderhouse

## 📘 Descripción general

**EcommerceProject** es una aplicación Java desarrollada con **Spring Boot**, **JPA (Hibernate)** y **MySQL**, como parte del curso de **Java** en **Coderhouse**.  
El objetivo es construir un sistema que permita **administrar las ventas de un comercio**, aplicando los conceptos aprendidos durante el curso:  
control de flujos, objetos y métodos, herencia, excepciones, clases abstractas, uso de bases de datos, y persistencia con JPA.

El objetivo del sistema es administrar el ciclo completo de venta de un comercio, incorporando:

- Manejo de clientes
- Gestión de productos
- Generación de facturas
- Creación de detalles de factura
- Reglas comerciales reales (validación de stock, cliente, precios históricos)
- Uso de una API externa para obtener la fecha de emisión de la factura

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

## 📏 Reglas de negocio implementadas

El proyecto incluye validaciones reales para simular un sistema profesional.

### ✔ Validaciones de factura
- El cliente debe existir.
- Debe tener al menos una línea.
- Cada línea debe incluir:
  - Un producto válido
  - Cantidad mayor a 0
  - Stock suficiente

### ✔ Reglas sobre facturas
- No se pueden modificar una vez creadas.
- No se pueden eliminar (motivos fiscales).
- Los detalles se generan automáticamente.

### ✔ Reglas sobre productos
- Si se crea una factura:
  - El stock del producto se descuenta.
  - El precio del producto se congela en el detalle.

### ✔ Integración con API externa
Para obtener la fecha de emisión, se consulta:
👉 http://worldclockapi.com/api/json/utc/now
Si falla, se usa LocalDateTime.now() como fallback.

---

## 🌐 Documentación Swagger
El proyecto incluye documentación automática usando SpringDoc + OpenAPI.
Disponible en:

```bash
http://localhost:8080/swagger-ui/index.html
```
Incluye:
- Endpoints de clientes, productos y facturas.
- Ejemplos JSON.
- Errores personalizados (ErrorResponse).
- Respuestas detalladas y modelos.

---

## 📦 Ejemplo de request para crear factura
```json
{
  "cliente": { "clienteid": 1 },
  "lineas": [
    {
      "producto": { "productoid": 1 },
      "cantidad": 2
    },
    {
      "producto": { "productoid": 3 },
      "cantidad": 1
    }
  ]
}
```

---

## 📄 Ejemplo de respuesta correcta
```json
{
  "id": 5,
  "cliente": {
    "id": 1,
    "nombre": "Lucas",
    "apellido": "Prat",
    "dni": 40555111
  },
  "fecha": "2025-01-20T15:34:10",
  "total": 5000.0,
  "detalles": [
    {
      "id": 12,
      "cantidad": 2,
      "precioUnitario": 1500,
      "producto": { "id": 1, "nombre": "Monitor" }
    }
  ]
}
```
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
- Swagger / OpenAPI
- Lombok

---

## 🧠 Conceptos aplicados
- POO (clases, objetos, encapsulamiento)
- Relaciones entre entidades
- Herencia e interfaces (CRUDInterface)
- Manejo de excepciones
- Persistencia con JPA + Hibernate
- Servicios, controladores y repositorios
- Validaciones de negocio
- Consumo de API externa (WorldClockAPI)
- Documentación con OpenAPI

---

## ✍️ Autor
**Lucas Nicolás Prat**
- 📘 Curso de Java - Coderhouse
- 📅 Año: 2025
