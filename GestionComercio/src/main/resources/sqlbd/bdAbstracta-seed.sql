-- Inserciones en ObjetoBD
INSERT INTO ObjetoBD (id, tipo) VALUES (1, 'Usuario');
INSERT INTO ObjetoBD (id, tipo) VALUES (2, 'Usuario');
INSERT INTO ObjetoBD (id, tipo) VALUES (3, 'Usuario');
INSERT INTO ObjetoBD (id, tipo) VALUES (4, 'Usuario');
INSERT INTO ObjetoBD (id, tipo) VALUES (5, 'Usuario');
-- Inserciones en Usuario
INSERT INTO Usuario (id, usuario, contra) VALUES (1, 'usuario1', 'contra1');
INSERT INTO Usuario (id, usuario, contra) VALUES (2, 'usuario2', 'contra2');
INSERT INTO Usuario (id, usuario, contra) VALUES (3, 'usuario3', 'contra3');
INSERT INTO Usuario (id, usuario, contra) VALUES (4, 'usuario4', 'contra4');
INSERT INTO Usuario (id, usuario, contra) VALUES (5, 'usuario5', 'contra5');

INSERT INTO Test (texto) VALUES ('Texto de ejemplo 1');
INSERT INTO Test (texto) VALUES ('Texto de ejemplo 2');
INSERT INTO Test (texto) VALUES ('Texto de ejemplo 3');
INSERT INTO Test (texto) VALUES ('Texto de ejemplo 4');
INSERT INTO Test (texto) VALUES ('Texto de ejemplo 5');


-- Inserciones en la tabla Rol
INSERT INTO Rol (nombre) VALUES ('Admin');
INSERT INTO Rol (nombre) VALUES ('User');
INSERT INTO Rol (nombre) VALUES ('Guest');

-- Inserciones en la tabla Permiso
INSERT INTO Permiso (nombre) VALUES ('Leer');
INSERT INTO Permiso (nombre) VALUES ('Escribir');
INSERT INTO Permiso (nombre) VALUES ('Eliminar');

-- Inserciones en la tabla Usuario_Rol
INSERT INTO Usuario_Rol (usuario_id, rol_id) VALUES (1, 1); -- usuario1 es Admin
INSERT INTO Usuario_Rol (usuario_id, rol_id) VALUES (5, 1); -- usuario5 es Admin
INSERT INTO Usuario_Rol (usuario_id, rol_id) VALUES (2, 2); -- usuario2 es User
INSERT INTO Usuario_Rol (usuario_id, rol_id) VALUES (3, 3); -- usuario3 es Guest

-- Inserciones en la tabla Rol_Permiso
INSERT INTO Rol_Permiso (rol_id, permiso_id) VALUES (1, 1); -- Admin tiene permiso Leer
INSERT INTO Rol_Permiso (rol_id, permiso_id) VALUES (1, 2); -- Admin tiene permiso Escribir
INSERT INTO Rol_Permiso (rol_id, permiso_id) VALUES (1, 3); -- Admin tiene permiso Eliminar
INSERT INTO Rol_Permiso (rol_id, permiso_id) VALUES (2, 1); -- User tiene permiso Leer
INSERT INTO Rol_Permiso (rol_id, permiso_id) VALUES (3, 1); -- Guest tiene permiso Leer
