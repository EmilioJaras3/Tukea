package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;

/**
 * Representa un registro en la tabla historial_trueques.
 * Ajusta los campos si tu script MySQL usa nombres distintos.
 */
public class HistorialTrueque {

    private Long id;
    private Long solicitudId;
    private java.time.LocalDateTime fechaRealizacion;
    private String estado;

    public HistorialTrueque() {}

    public HistorialTrueque(Long id, Long solicitudId, java.time.LocalDateTime fechaRealizacion, String estado) {
        this.id = id;
        this.solicitudId = solicitudId;
        this.fechaRealizacion = fechaRealizacion;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSolicitudId() { return solicitudId; }
    public void setSolicitudId(Long solicitudId) { this.solicitudId = solicitudId; }
    public java.time.LocalDateTime getFechaRealizacion() { return fechaRealizacion; }
    public void setFechaRealizacion(java.time.LocalDateTime fechaRealizacion) { this.fechaRealizacion = fechaRealizacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
