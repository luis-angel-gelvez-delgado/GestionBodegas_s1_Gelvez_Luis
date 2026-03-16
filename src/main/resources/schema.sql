-- ================================================
-- SCHEMA - LogiTrack Sistema de Gestion de Bodegas
-- Autor: Luis Gelvez
-- ================================================

CREATE DATABASE IF NOT EXISTS logitrackgelvezluis;
USE logitrackgelvezluis;

-- Tabla usuario
CREATE TABLE IF NOT EXISTS `usuario` (
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `nombre`   varchar(100) NOT NULL,
    `username` varchar(100) NOT NULL,
    `password` varchar(255) NOT NULL,
    `rol`      varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla bodega
CREATE TABLE IF NOT EXISTS `bodega` (
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `nombre`    varchar(100) NOT NULL,
    `ubicacion` varchar(150) NOT NULL,
    `capacidad` int NOT NULL,
    `encargado` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla producto
CREATE TABLE IF NOT EXISTS `producto` (
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `nombre`    varchar(150) NOT NULL,
    `categoria` varchar(100) NOT NULL,
    `stock`     int NOT NULL,
    `precio`    decimal(10,2) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla movimiento_inventario
CREATE TABLE IF NOT EXISTS `movimiento_inventario` (
    `id`                bigint NOT NULL AUTO_INCREMENT,
    `fecha`             datetime NOT NULL,
    `tipo`              varchar(50) NOT NULL,
    `usuario_id`        bigint NOT NULL,
    `bodega_origen_id`  bigint DEFAULT NULL,
    `bodega_destino_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_mov_usuario` FOREIGN KEY (`usuario_id`)
        REFERENCES `usuario` (`id`),
    CONSTRAINT `fk_mov_bodega_origen` FOREIGN KEY (`bodega_origen_id`)
        REFERENCES `bodega` (`id`),
    CONSTRAINT `fk_mov_bodega_destino` FOREIGN KEY (`bodega_destino_id`)
        REFERENCES `bodega` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla detalle_movimiento
CREATE TABLE IF NOT EXISTS `detalle_movimiento` (
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `cantidad`       int NOT NULL,
    `movimiento_id`  bigint NOT NULL,
    `producto_id`    bigint NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_det_movimiento` FOREIGN KEY (`movimiento_id`)
        REFERENCES `movimiento_inventario` (`id`),
    CONSTRAINT `fk_det_producto` FOREIGN KEY (`producto_id`)
        REFERENCES `producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla producto_bodega
CREATE TABLE IF NOT EXISTS `producto_bodega` (
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `producto_id` bigint NOT NULL,
    `bodega_id`  bigint NOT NULL,
    `stock`      int NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_pb_producto` FOREIGN KEY (`producto_id`)
        REFERENCES `producto` (`id`),
    CONSTRAINT `fk_pb_bodega` FOREIGN KEY (`bodega_id`)
        REFERENCES `bodega` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla auditoria
CREATE TABLE IF NOT EXISTS `auditoria` (
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `tipo_operacion`   varchar(50) NOT NULL,
    `fecha`            datetime NOT NULL,
    `usuario`          varchar(100) NOT NULL,
    `entidad_afectada` varchar(100) NOT NULL,
    `valor_anterior`   text,
    `valor_nuevo`      text,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;