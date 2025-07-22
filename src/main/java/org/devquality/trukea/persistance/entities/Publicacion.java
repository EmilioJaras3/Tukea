package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;

public class Publicacion {
    private Long id;              // PK AUTO_INCREMENT
    private Long productoId;      // FK a productos.id_producto
    private Long usuarioId;       // FK a usuarios.id_usuario
    private String titulo;
    private String descripcion;
    private Integer ciudadId;     // opcional: FK a ciudades.id_ciudad
    private LocalDateTime fechaPublicacion;

    public Publicacion() { }

    public Publicacion(Long id, Long productoId, Long usuarioId,
                       String titulo, String descripcion,
                       Integer ciudadId, LocalDateTime fechaPublicacion) {
        this.id = id;
        this.productoId = productoId;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ciudadId = ciudadId;
        this.fechaPublicacion = fechaPublicacion;
    }

    // ---------- getters & setters CORREGIDOS ---------- //
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }  // ← CORREGIDO: Long no Integer

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }     // ← CORREGIDO: Long no Integer

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCiudadId() { return ciudadId; }
    public void setCiudadId(Integer ciudadId) { this.ciudadId = ciudadId; }

    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
}