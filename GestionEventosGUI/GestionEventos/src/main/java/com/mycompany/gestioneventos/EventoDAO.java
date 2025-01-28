/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

/**
 *
 * @author Ryzen
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    private Connection connection;

    public EventoDAO() {
        this.connection = Database.getConnection();
    }

    public void insertarEvento(Evento evento) {
        String sql = "INSERT INTO Evento (nombreEvento, TipoEvento, fecha, Duracion, descripcion, idOrganizador) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, evento.getNombreEvento());
            statement.setString(2, evento.getTipoEvento());
            statement.setString(3, evento.getFecha());
            statement.setInt(4, evento.getDuracion());
            statement.setString(5, evento.getDescripcion());
            statement.setInt(6, evento.getIdOrganizador());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar evento: " + e.getMessage());
        }
    }

    public List<Evento> listarEventos() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT nombreEvento, TipoEvento, fecha, Duracion, descripcion, idOrganizador, idEvento FROM Evento";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Evento evento = new Evento(
                        resultSet.getInt("idEvento"),
                        resultSet.getString("nombreEvento"),
                        resultSet.getString("TipoEvento"),
                        resultSet.getString("fecha"),
                        resultSet.getInt("Duracion"),
                        resultSet.getString("descripcion"),
                        resultSet.getInt("idOrganizador")
                );
                eventos.add(evento);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar eventos: " + e.getMessage());
        }

        return eventos;
    }

    public Evento obtenerPorId(int idEvento) {
        String sql = "SELECT idEvento, nombreEvento, TipoEvento, fecha, Duracion, descripcion, idOrganizador FROM Evento WHERE idEvento = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEvento);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Evento(
                        rs.getInt("idEvento"),
                        rs.getString("nombreEvento"),
                        rs.getString("TipoEvento"),
                        rs.getString("fecha"),
                        rs.getInt("Duracion"),
                        rs.getString("descripcion"),
                        rs.getInt("idOrganizador")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener evento: " + e.getMessage());
        }
        return null;
    }
}
