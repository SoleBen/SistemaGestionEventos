/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneventos;

// Clase Evento
public class Evento {
    private int idEvento;
    private String nombreEvento;
    private String tipoEvento;
    private String fecha;
    private int duracion;
    private String descripcion;
    private int idOrganizador;

    public Evento(int idEvento, String nombreEvento, String tipoEvento, String fecha, int duracion, String descripcion, int idOrganizador) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.tipoEvento = tipoEvento;
        this.fecha = fecha;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.idOrganizador = idOrganizador;
    }

    public void crearEvento() {
        EventoDAO eventoDAO = new EventoDAO();
        eventoDAO.insertarEvento(this);
    }

    public int getIdEvento() { return idEvento; }
    public String getNombreEvento() { return nombreEvento; }
    public String getTipoEvento() { return tipoEvento; }
    public String getFecha() { return fecha; }
    public int getDuracion() { return duracion; }
    public String getDescripcion() { return descripcion; }
    public int getIdOrganizador() { return idOrganizador; }
}
