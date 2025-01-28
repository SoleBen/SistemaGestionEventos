/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryzen
 */
public class ReservaDAO {

    private Connection connection;

    public ReservaDAO() {
        this.connection = Database.getConnection();
    }

    public void insertarReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO Reserva (idEvento, idLugar, fecha, tipoReserva, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reserva.getIdEvento());
            stmt.setInt(2, reserva.getIdLugar());
            stmt.setTimestamp(3, Timestamp.valueOf(reserva.getFecha()));
            stmt.setString(4, reserva.getTipoReserva());
            stmt.setString(5, reserva.getEstado().name());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reserva.setIdReserva(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void actualizarEstadoReserva(int idReserva, EstadoReserva estado) throws SQLException {
        String sql = "UPDATE Reserva SET estado = ? WHERE idReserva = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estado.name());
            stmt.setInt(2, idReserva);
            stmt.executeUpdate();
        }
    }

    public List<Reserva> listarTodasLasReservas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT idReserva, idEvento, idLugar, fecha, tipoReserva, estado FROM Reserva";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("idReserva"));
                reserva.setIdEvento(rs.getInt("idEvento"));
                reserva.setIdLugar(rs.getInt("idLugar"));
                reserva.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                reserva.setTipoReserva(rs.getString("tipoReserva"));
                reserva.setEstado(EstadoReserva.valueOf(rs.getString("estado")));
                reservas.add(reserva);
            }
        }
        return reservas;
    }

    public Reserva obtenerPorId(int idReserva) throws SQLException {
        String sql = "SELECT * FROM Reserva WHERE idReserva = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Reserva reserva = new Reserva();
                    reserva.setIdReserva(rs.getInt("idReserva"));
                    reserva.setIdEvento(rs.getInt("idEvento"));
                    reserva.setIdLugar(rs.getInt("idLugar"));
                    reserva.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    reserva.setTipoReserva(rs.getString("tipoReserva"));
                    reserva.setEstado(EstadoReserva.valueOf(rs.getString("estado")));
                    return reserva;
                }
            }
        }
        return null;
    }
}
