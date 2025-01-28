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
public class OrganizadorDAOImpl extends UsuarioDAO {
    public OrganizadorDAOImpl(Connection conexion) {
        super(conexion);
    }

    @Override
    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Organizador o JOIN Usuario u ON o.idUsuario = u.idUsuario WHERE o.idOrganizador = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Organizador org = new Organizador();
                org.setIdOrganizador(rs.getInt("idOrganizador"));
                org.setIdUsuario(rs.getInt("idUsuario"));
                org.setNombre(rs.getString("nombre"));
                org.setCorreo(rs.getString("correo"));
                return org;
            }
            return null;
        }
    }

    @Override
    public void insertar(Usuario usuario) throws SQLException {
        String sqlUsuario = "INSERT INTO Usuario(nombre, correo, Rol) VALUES (?, ?, 'Organizador')";
        try (PreparedStatement stmt = conexion.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);
                    
                    // Luego insertar en Organizador
                    String sqlOrg = "INSERT INTO Organizador(idUsuario) VALUES (?)";
                    try (PreparedStatement stmtOrg = conexion.prepareStatement(sqlOrg)) {
                        stmtOrg.setInt(1, idUsuario);
                        stmtOrg.executeUpdate();
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
