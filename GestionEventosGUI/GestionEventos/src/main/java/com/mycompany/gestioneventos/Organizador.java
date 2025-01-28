/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

/**
 *
 * @author Ryzen
 */
public class Organizador extends Usuario {
    private int idOrganizador;

    public Organizador() {
        this.rol = "Organizador";
    }

    @Override
    public String obtenerDetalles() {
        return String.format("Organizador ID: %d - Nombre: %s", idOrganizador, nombre);
    }

    public int getIdOrganizador() { return idOrganizador; }
    public void setIdOrganizador(int idOrganizador) { this.idOrganizador = idOrganizador; }
}
