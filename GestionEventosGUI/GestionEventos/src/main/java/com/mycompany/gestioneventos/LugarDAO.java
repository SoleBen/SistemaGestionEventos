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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryzen
 */
public class LugarDAO {

    private Connection connection;

    public LugarDAO() {
        this.connection = Database.getConnection();
    }

    public void insertarLugar(Lugar lugar) throws SQLException {
        String sql = "INSERT INTO Lugar (nombreLugar, direccion, capacidad,disponibilidad) VALUES (?, ?, ?, 1)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, lugar.getNombre());
            stmt.setString(2, lugar.getDireccion());
            stmt.setInt(3, lugar.getCapacidad());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lugar.setIdLugar(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Lugar> listarTodos() throws SQLException {
        List<Lugar> lugares = new ArrayList<>();
        String sql = "SELECT idLugar, nombreLugar, direccion, capacidad, disponibilidad FROM Lugar";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Lugar lugar = new Lugar();
                lugar.setIdLugar(rs.getInt("idLugar"));
                lugar.setNombre(rs.getString("nombreLugar"));
                lugar.setDireccion(rs.getString("direccion"));
                lugar.setCapacidad(rs.getInt("capacidad"));
                lugar.setDisponibilidad(rs.getInt("disponibilidad") == 1);
                lugares.add(lugar);
            }
        }
        return lugares;
    }
public Lugar obtenerPorId(int idLugar) throws SQLException {
    String sql = "SELECT * FROM Lugar WHERE idLugar = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, idLugar);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Lugar lugar = new Lugar();
            lugar.setIdLugar(rs.getInt("idLugar"));
            lugar.setNombre(rs.getString("nombreLugar"));
            lugar.setDireccion(rs.getString("direccion"));
            lugar.setCapacidad(rs.getInt("capacidad"));
            lugar.setDisponibilidad(rs.getInt("disponibilidad") == 1);
            return lugar;
        }
    }
    return null;
}

    public void actualizarDisponibilidad(int idLugar, boolean disponible) throws SQLException {
        String sql = "UPDATE Lugar SET disponibilidad = ? WHERE idLugar = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, disponible ? 1 : 0);
            stmt.setInt(2, idLugar);
            stmt.executeUpdate();
        }
    }
}
