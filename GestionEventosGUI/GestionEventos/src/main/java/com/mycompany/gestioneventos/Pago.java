/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestioneventos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Ryzen
 */
public class Pago {
    private int idPago;
    private BigDecimal monto;
    private LocalDateTime fecha;
    private MetodoPago metodo;
    private int idReserva;
    
    public Pago() {}
    
    public Pago(BigDecimal monto, LocalDateTime fecha, MetodoPago metodo, int idReserva) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodo = metodo;
        this.idReserva = idReserva;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPago metodo) {
        this.metodo = metodo;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
    
}

