/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Ryzen
 */
public abstract class UsuarioDAO {
    protected Connection conexion;

    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public abstract Usuario obtenerPorId(int id) throws SQLException;
    public abstract List<Usuario> listarTodos() throws SQLException;
    public abstract void insertar(Usuario usuario) throws SQLException;
    public abstract void actualizar(Usuario usuario) throws SQLException;
    public abstract void eliminar(int id) throws SQLException;
}