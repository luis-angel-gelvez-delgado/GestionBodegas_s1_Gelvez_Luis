-- ================================================
-- DATA - LogiTrack Sistema de Gestion de Bodegas
-- Datos iniciales de prueba
-- Passwords: admin=admin1234, carlos=carlos1234, maria=maria1234
-- ================================================

USE logitrackgelvezluis;

-- Usuarios
INSERT INTO `usuario` (nombre, username, password, rol) VALUES
('Admin LogiTrack', 'admin',  '$2a$10$QBI8tuUQqjQg9aJC3xfld.JKiFCHWKo.Sl2QqabM4qs6LNVvDMkTW', 'ADMIN'),
('Carlos Empleado', 'carlos', '$2a$10$Hp56SgxvMpOEERugmfzX2exwHMxsT.nBTphNvuEO6oe3AcNMNQx2i', 'EMPLEADO'),
('Maria Empleada',  'maria',  '$2a$10$aBq7hHpXicLkssSodwmYe.lOZIRCCWie4ho6JdPXpcQl7l74fp7hy', 'EMPLEADO');

-- Bodegas
INSERT INTO `bodega` (nombre, ubicacion, capacidad, encargado) VALUES
('Bodega Central', 'Bogota',   1000, 'Admin LogiTrack'),
('Bodega Norte',   'Medellin', 800,  'Carlos Empleado'),
('Bodega Sur',     'Cali',     600,  'Maria Empleada');

-- Productos
INSERT INTO `producto` (nombre, categoria, stock, precio) VALUES
('Arroz',     'Comestible', 0, 2500.00),
('Aceite',    'Comestible', 0, 8000.00),
('Harina',    'Comestible', 0, 3000.00),
('Tornillos', 'Ferreteria', 0, 500.00),
('Pintura',   'Ferreteria', 0, 15000.00);