
package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;

public class Producto {
    private Long id; // id de la publicación
    private Long usuarioId; // usuario_id
    private String titulo; // titulo
    private String descripcion; // descripcion
    private String categoria; // categoria
    private String estado; // estado (Nuevo, Usado, Buen estado)
    private String imagenUrl; // imagen_url
    private Long zonaSeguraId; // zona_segura_id
    private LocalDateTime fechaPublicacion; // fecha_publicacion

    public Producto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Long getZonaSeguraId() { return zonaSeguraId; }
    public void setZonaSeguraId(Long zonaSeguraId) { this.zonaSeguraId = zonaSeguraId; }

    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
}
