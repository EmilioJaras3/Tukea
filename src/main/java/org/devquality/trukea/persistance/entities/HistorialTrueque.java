package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;

/**
 * Representa un registro en la tabla historial_trueques.
 * Ajusta los campos si tu script MySQL usa nombres distintos.
 */
public class HistorialTrueque {

    private Long id;                   // PK autoincremental
    private Long truequeId;            // ID del trueque que originó el historial
    private Long productoOfrecidoId;   // Producto que dio el usuario A
    private Long productoDeseadoId;    // Producto que recibió el usuario A
    private Long usuarioOfrecidoId;    // Usuario que ofreció el producto
    private Long usuarioRecibidoId;    // Usuario que lo aceptó
    private LocalDateTime fecha;       // Cuándo se concretó el intercambio

    /* ---------- Constructores ---------- */
    public HistorialTrueque() {}

    public HistorialTrueque(Long id, Long truequeId,
                            Long productoOfrecidoId, Long productoDeseadoId,
                            Long usuarioOfrecidoId, Long usuarioRecibidoId,
                            LocalDateTime fecha) {
        this.id = id;
        this.truequeId = truequeId;
        this.productoOfrecidoId = productoOfrecidoId;
        this.productoDeseadoId = productoDeseadoId;
        this.usuarioOfrecidoId = usuarioOfrecidoId;
        this.usuarioRecibidoId = usuarioRecibidoId;
        this.fecha = fecha;
    }

    /* ---------- Getters y setters ---------- */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTruequeId() { return truequeId; }
    public void setTruequeId(Long truequeId) { this.truequeId = truequeId; }

    public Long getProductoOfrecidoId() { return productoOfrecidoId; }
    public void setProductoOfrecidoId(Long productoOfrecidoId) { this.productoOfrecidoId = productoOfrecidoId; }

    public Long getProductoDeseadoId() { return productoDeseadoId; }
    public void setProductoDeseadoId(Long productoDeseadoId) { this.productoDeseadoId = productoDeseadoId; }

    public Long getUsuarioOfrecidoId() { return usuarioOfrecidoId; }
    public void setUsuarioOfrecidoId(Long usuarioOfrecidoId) { this.usuarioOfrecidoId = usuarioOfrecidoId; }

    public Long getUsuarioRecibidoId() { return usuarioRecibidoId; }
    public void setUsuarioRecibidoId(Long usuarioRecibidoId) { this.usuarioRecibidoId = usuarioRecibidoId; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
