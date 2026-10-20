
DROP DATABASE IF EXISTS tecnostore_SaraSandoval;
CREATE DATABASE tecnostore_SaraSandoval;
USE tecnostore_SaraSandoval;

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE celular (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE SET NULL
);

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cedula VARCHAR(20) UNIQUE NOT NULL,
    correo VARCHAR(100)
);

CREATE TABLE vendedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    cliente_id INT,
    vendedor_id INT,
    total DOUBLE DEFAULT 0.0,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE,
    FOREIGN KEY (vendedor_id) REFERENCES vendedor(id) ON DELETE SET NULL
);

CREATE TABLE detalle_venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    celular_id INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
    FOREIGN KEY (celular_id) REFERENCES celular(id) ON DELETE RESTRICT
);

INSERT INTO categoria (nombre) VALUES 
('Gama Alta'),
('Gama Media'),
('Gama Entrada');

INSERT INTO celular (marca, modelo, precio, stock, categoria_id) VALUES 
('Samsung', 'Galaxy S25 Ultra', 4999900.0, 10, 1),
('Apple', 'iPhone 14', 3500000.0, 5, 1),
('Xiaomi', 'Redmi Note 13', 1200000.0, 8, 2),
('Motorola', 'Moto G67 5G', 987900.0, 12, 2),
('Infinix', 'Note 60 5G', 1149900.0, 15, 2),
('Realme', '12 Pro+', 1850000.0, 7, 2);

INSERT INTO cliente (nombre, cedula, correo) VALUES 
('Carlos Mendoza', '1098765432', 'carlos@gmail.com'),
('María Rodríguez', '63542189', 'maria@hotmail.com'),
('Juan Esteban Pérez', '1098123456', 'juan.perez@gmail.com');

INSERT INTO vendedor (nombre, codigo) VALUES 
('Sara Julieth Sandoval Orellan', 'DEV001'),
('Jeysson Antonio Herrera Forero', 'VEND002'),
('Ingrid Johana Guarín Niz', 'VEND003'),
('Andrés Felipe Gómez', 'VEND004'),
('Laura Daniela Martínez', 'VEND005'),
('Camilo Esteban Rodríguez', 'VEND006');

INSERT INTO pedido (cliente_id, vendedor_id, total) VALUES 
(1, 1, 4700000.0),  
(2, 2, 1975800.0),  
(3, 1, 4999900.0);  

INSERT INTO detalle_venta (pedido_id, celular_id, cantidad, subtotal) VALUES 
(1, 2, 1, 3500000.0),  
(1, 3, 1, 1200000.0),  
(2, 4, 2, 1975800.0),  
(3, 1, 1, 4999900.0);

SELECT * FROM categoria;
SELECT * FROM celular;
SELECT * FROM cliente;
SELECT * FROM vendedor;
SELECT * FROM pedido;
SELECT * FROM detalle_venta;

SELECT 
    p.id AS pedido_id,
    p.fecha,
    c.nombre AS cliente,
    v.nombre AS vendedor,
    p.total
FROM pedido p
INNER JOIN cliente c ON p.cliente_id = c.id
INNER JOIN vendedor v ON p.vendedor_id = v.id;

SELECT 
    dv.id AS detalle_id,
    dv.pedido_id,
    cel.marca,
    cel.modelo,
    dv.cantidad,
    dv.subtotal
FROM detalle_venta dv
INNER JOIN celular cel ON dv.celular_id = cel.id;
