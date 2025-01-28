/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

import java.time.LocalDateTime;

/**
 *
 * @author Ryzen
 */
public class Reserva {
    private int idReserva;
    private int idEvento;
    private int idLugar;
    private LocalDateTime fecha;
    private String tipoReserva;
    private EstadoReserva estado;
    private Integer idPago;  
    
    public Reserva() {}

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(String tipoReserva) {
        this.tipoReserva = tipoReserva;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }
    
    public Reserva(int idEvento, int idLugar, LocalDateTime fecha, String tipoReserva) {
        this.idEvento = idEvento;
        this.idLugar = idLugar;
        this.fecha = fecha;
        this.tipoReserva = tipoReserva;
        this.estado = EstadoReserva.Pendiente;
    }
    
}