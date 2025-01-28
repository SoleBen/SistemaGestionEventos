-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 28-01-2025 a las 16:07:15
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_eventos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evento`
--

CREATE TABLE `evento` (
  `idEvento` int(11) NOT NULL,
  `nombreEvento` varchar(100) NOT NULL,
  `TipoEvento` varchar(50) NOT NULL,
  `fecha` datetime NOT NULL,
  `Duracion` int(11) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `idOrganizador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `evento`
--

INSERT INTO `evento` (`idEvento`, `nombreEvento`, `TipoEvento`, `fecha`, `Duracion`, `descripcion`, `idOrganizador`) VALUES
(1, 'Alasitas', 'Temporal', '2025-01-28 14:10:51', 6, 'Importante', 1),
(2, 'Carnaval Oruro', 'Festivo', '2025-01-28 15:17:19', 11, 'Oruro', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eventoservicio`
--

CREATE TABLE `eventoservicio` (
  `idEvento` int(11) NOT NULL,
  `idServicio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `invitado`
--

CREATE TABLE `invitado` (
  `idInvitado` int(11) NOT NULL,
  `estado` enum('Confirmado','Pendiente','Cancelado') NOT NULL,
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar`
--

CREATE TABLE `lugar` (
  `idLugar` int(11) NOT NULL,
  `nombreLugar` varchar(100) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `capacidad` int(11) NOT NULL,
  `disponibilidad` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `lugar`
--

INSERT INTO `lugar` (`idLugar`, `nombreLugar`, `direccion`, `capacidad`, `disponibilidad`) VALUES
(1, 'Carpa 1', 'Zona Leste Auditoria N1', 4, 0),
(2, 'Carpa 2', 'Zona Leste Auditoria N2', 5, 0),
(3, 'Carpa 3', 'Zona Leste Auditoria N3', 2, 0),
(4, 'Asiento Carnavalero', 'Silla 1', 1, 0),
(5, 'Asiento Carnavalero', 'Silla 2', 1, 1),
(6, 'Asiento Carnavalero', 'Silla 3', 1, 1),
(7, 'Asiento Carnavalero', 'Silla 4', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organizador`
--

CREATE TABLE `organizador` (
  `idOrganizador` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `organizador`
--

INSERT INTO `organizador` (`idOrganizador`, `idUsuario`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `idPago` int(11) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `fecha` datetime NOT NULL,
  `metodoPago` enum('Tarjeta','Efectivo','Transferencia') NOT NULL,
  `idReserva` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`idPago`, `monto`, `fecha`, `metodoPago`, `idReserva`) VALUES
(1, 200.00, '2025-01-28 09:41:13', 'Tarjeta', 2),
(2, 200.00, '2025-01-28 09:53:02', 'Efectivo', 3),
(3, 21.00, '2025-01-28 10:04:30', 'Tarjeta', 4),
(4, 200.00, '2025-01-28 10:21:21', 'Efectivo', 6),
(5, 2111.00, '2025-01-28 11:03:34', 'Transferencia', 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `idReserva` int(11) NOT NULL,
  `idEvento` int(11) NOT NULL,
  `idLugar` int(11) NOT NULL,
  `Fecha` datetime NOT NULL,
  `TipoReserva` varchar(50) NOT NULL,
  `estado` enum('Confirmada','Pendiente','Cancelada') NOT NULL,
  `idPago` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`idReserva`, `idEvento`, `idLugar`, `Fecha`, `TipoReserva`, `estado`, `idPago`) VALUES
(1, 1, 1, '2002-10-10 12:10:00', 'CONCIERTO', 'Cancelada', NULL),
(2, 1, 2, '2002-10-10 10:10:00', 'CONCIERTO', 'Confirmada', NULL),
(3, 1, 1, '2002-10-10 10:10:00', 'CONCIERTO', 'Confirmada', NULL),
(4, 1, 3, '2002-10-21 10:11:00', 'PRUEBA', 'Confirmada', NULL),
(5, 1, 4, '2010-10-10 12:12:00', 'CONCIERTO', 'Cancelada', NULL),
(6, 2, 4, '2002-10-21 12:12:00', 'CONCIERTO', 'Confirmada', NULL),
(7, 2, 7, '2025-01-28 11:03:14', 'Festivo', 'Cancelada', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE `servicio` (
  `idServicio` int(11) NOT NULL,
  `TipoServicio` varchar(50) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `costo` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `Rol` enum('Organizador','Invitado') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `correo`, `Rol`) VALUES
(1, 'Guillermo', 'Guillermo@gmail.com', 'Organizador');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`idEvento`),
  ADD KEY `idOrganizador` (`idOrganizador`);

--
-- Indices de la tabla `eventoservicio`
--
ALTER TABLE `eventoservicio`
  ADD PRIMARY KEY (`idEvento`,`idServicio`),
  ADD KEY `idServicio` (`idServicio`);

--
-- Indices de la tabla `invitado`
--
ALTER TABLE `invitado`
  ADD PRIMARY KEY (`idInvitado`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indices de la tabla `lugar`
--
ALTER TABLE `lugar`
  ADD PRIMARY KEY (`idLugar`);

--
-- Indices de la tabla `organizador`
--
ALTER TABLE `organizador`
  ADD PRIMARY KEY (`idOrganizador`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`idPago`),
  ADD KEY `idReserva` (`idReserva`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`idReserva`),
  ADD KEY `idEvento` (`idEvento`),
  ADD KEY `idLugar` (`idLugar`);

--
-- Indices de la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD PRIMARY KEY (`idServicio`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `evento`
--
ALTER TABLE `evento`
  MODIFY `idEvento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `invitado`
--
ALTER TABLE `invitado`
  MODIFY `idInvitado` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `lugar`
--
ALTER TABLE `lugar`
  MODIFY `idLugar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `organizador`
--
ALTER TABLE `organizador`
  MODIFY `idOrganizador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `idReserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `servicio`
--
ALTER TABLE `servicio`
  MODIFY `idServicio` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `evento_ibfk_1` FOREIGN KEY (`idOrganizador`) REFERENCES `organizador` (`idOrganizador`);

--
-- Filtros para la tabla `eventoservicio`
--
ALTER TABLE `eventoservicio`
  ADD CONSTRAINT `eventoservicio_ibfk_1` FOREIGN KEY (`idEvento`) REFERENCES `evento` (`idEvento`),
  ADD CONSTRAINT `eventoservicio_ibfk_2` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`);

--
-- Filtros para la tabla `invitado`
--
ALTER TABLE `invitado`
  ADD CONSTRAINT `invitado_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);

--
-- Filtros para la tabla `organizador`
--
ALTER TABLE `organizador`
  ADD CONSTRAINT `organizador_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`idReserva`) REFERENCES `reserva` (`idReserva`);

--
-- Filtros para la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`idEvento`) REFERENCES `evento` (`idEvento`),
  ADD CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`idLugar`) REFERENCES `lugar` (`idLugar`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
