/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

/**
 *
 * @author Ryzen
 */

class LugarWrapper {
    private int id;
    private String nombre;

    public LugarWrapper(Lugar lugar) {
        this.id = lugar.getIdLugar();
        this.nombre = lugar.getNombre();
    }

    public int getId() { return id; }
    @Override
    public String toString() { return nombre; }
}