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
import java.util.List;

/**
 *
 * @author Ryzen
 */
public class InvitadoDAOImpl extends UsuarioDAO {
    public InvitadoDAOImpl(Connection conexion) {
        super(conexion);
    }

    @Override
    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Invitado i JOIN Usuario u ON i.idUsuario = u.idUsuario WHERE i.idInvitado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Invitado inv = new Invitado();
                inv.setIdInvitado(rs.getInt("idInvitado"));
                inv.setEstado(Estado.valueOf(rs.getString("estado")));
                inv.setIdUsuario(rs.getInt("idUsuario"));
                inv.setNombre(rs.getString("nombre"));
                inv.setCorreo(rs.getString("correo"));
                return inv;
            }
            return null;
        }
    }

    @Override
    public void insertar(Usuario usuario) throws SQLException {
        Invitado invitado = (Invitado) usuario;
        String sqlUsuario = "INSERT INTO Usuario(nombre, correo, Rol) VALUES (?, ?, 'Invitado')";
        try (PreparedStatement stmt = conexion.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);
                    
                    String sqlInv = "INSERT INTO Invitado(estado, idUsuario) VALUES (?, ?)";
                    try (PreparedStatement stmtInv = conexion.prepareStatement(sqlInv)) {
                        stmtInv.setString(1, invitado.getEstado().name());
                        stmtInv.setInt(2, idUsuario);
                        stmtInv.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}