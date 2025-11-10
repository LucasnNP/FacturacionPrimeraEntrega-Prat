/* Acá se deja el Script para cargar datos iniciales , cabe destacar que una es solo de pruba
* Ya que, una vez que se corta el servidor estos datos quedan guardados en la base de datos
ecommerce_db, cuando se vuelve a levantar el servidor la api intentará cargar los datos estos
pero el proceso se cortará cuando vuelva a querer cargar el primer dni duplicado, ya que son
de tipo único
*/

-- 🔹 Insertar clientes
INSERT INTO Clientes (nombre, apellido, dni, email) VALUES
('Lucas', 'Prat', 40555111, 'lucas@email.com'),
('Ana', 'Torres', 42888999, 'ana@email.com');

-- 🔹 Insertar productos
INSERT INTO productos (descripcion, codigo, stock, precio) VALUES
('Yerba Mate', 'YRB001', 100, 1500),
('Azúcar', 'AZC002', 50, 1200),
('Café', 'CAF003', 75, 2000);

-- 🔹 Insertar facturas
INSERT INTO Facturas (cliente_id, fecha, total) VALUES
(1, NOW(), 3000),
(2, NOW(), 2000);

-- 🔹 Insertar detalles
INSERT INTO detalle_factura (factura_id, producto_id, cantidad, precio_unitario) VALUES
(1, 1, 2, 1500),
(2, 3, 1, 2000);
