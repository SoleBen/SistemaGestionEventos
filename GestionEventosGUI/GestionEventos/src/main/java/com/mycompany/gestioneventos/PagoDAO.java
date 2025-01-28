/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

/**
 *
 * @author Ryzen
 */

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {

    private Connection connection;

    public PagoDAO() {
        this.connection = Database.getConnection();
    }

    public void insertarPago(Pago pago) {
        String sql = "INSERT INTO Pago (monto, fecha, metodoPago, idReserva) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, pago.getMonto());
            statement.setTimestamp(2, Timestamp.valueOf(pago.getFecha()));
            statement.setString(3, pago.getMetodo().toString());
            statement.setInt(4, pago.getIdReserva());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar pago: " + e.getMessage());
        }
    }

    public List<Pago> listarPagos() {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT idPago, monto, fecha, metodoPago, idReserva FROM Pago";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Pago pago = new Pago(
                    resultSet.getBigDecimal("monto"),
                    resultSet.getTimestamp("fecha").toLocalDateTime(),
                    MetodoPago.valueOf(resultSet.getString("metodoPago")),
                    resultSet.getInt("idReserva")
                );
                pagos.add(pago);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pagos: " + e.getMessage());
        }

        return pagos;
    }

    public Pago obtenerPorId(int idPago) {
        String sql = "SELECT idPago, monto, fecha, metodoPago, idReserva FROM Pago WHERE idPago = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPago);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Pago(
                    rs.getBigDecimal("monto"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    MetodoPago.valueOf(rs.getString("metodoPago")),
                    rs.getInt("idReserva")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pago: " + e.getMessage());
        }
        return null;
    }

    public void eliminarPago(int idPago) {
        String sql = "DELETE FROM Pago WHERE idPago = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPago);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar pago: " + e.getMessage());
        }
    }
}
