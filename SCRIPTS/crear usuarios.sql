-- Inserta un Administrador
INSERT INTO usuarios (nombre, correo, contrasena, idRol, estatus)
VALUES ('admin', 'admin@correo.com', '12345', 1, 1);

-- Inserta un Almacenista
INSERT INTO usuarios (nombre, correo, contrasena, idRol, estatus)
VALUES ('almacen', 'almacen@correo.com', '12345', 2, 1);

-- Inserta los roles base
INSERT INTO roles (nombreRol) VALUES ('Administrador'), ('Almacenista');