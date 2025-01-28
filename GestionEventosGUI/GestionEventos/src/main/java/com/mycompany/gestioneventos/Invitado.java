/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

/**
 *
 * @author Ryzen
 */
public class Invitado extends Usuario {
    private int idInvitado;
    private Estado estado;

    public Invitado() {
        this.rol = "Invitado";
    }

    @Override
    public String obtenerDetalles() {
        return String.format("Invitado ID: %d - Estado: %s", idInvitado, estado.name());
    }

    public int getIdInvitado() { return idInvitado; }
    public void setIdInvitado(int idInvitado) { this.idInvitado = idInvitado; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
}