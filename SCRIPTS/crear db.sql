-- Tabla de roles
CREATE TABLE roles (
    idRol INT PRIMARY KEY AUTO_INCREMENT,
    nombreRol VARCHAR(50) NOT NULL
);

-- Tabla de usuarios
CREATE TABLE usuarios (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(25) NOT NULL,
    idRol INT NOT NULL,
    estatus INT NOT NULL DEFAULT 1,
    FOREIGN KEY (idRol) REFERENCES roles(idRol)
);

-- Tabla de productos
CREATE TABLE productos (
    idProducto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    cantidad INT DEFAULT 0,
    estatus BOOLEAN DEFAULT TRUE -- TRUE = activo, FALSE = inactivo
);

-- Tabla de movimientos con campo cantidad
CREATE TABLE movimientos (
    idMovimiento INT PRIMARY KEY AUTO_INCREMENT,
    idProducto INT NOT NULL,
    idUsuario INT NOT NULL,
    tipoMovimiento VARCHAR(10) NOT NULL, -- valores: 'entrada' o 'salida'
    cantidad INT NOT NULL,
    fechaHora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idProducto) REFERENCES productos(idProducto),
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
);
