/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

/**
 *
 * @author Ryzen
 */
class EventoWrapper {
    private int id;
    private String nombre;
    private String tipo;

    public EventoWrapper(Evento evento) {
        this.id = evento.getIdEvento();
        this.nombre = evento.getNombreEvento();
        this.tipo = evento.getTipoEvento();
    }

    public int getId() { return id; }
    public String getTipo() { return tipo; }
    @Override
    public String toString() { return nombre; }
}

