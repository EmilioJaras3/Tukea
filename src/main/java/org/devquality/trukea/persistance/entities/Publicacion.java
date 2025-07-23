package org.devquality.trukea.persistance.entities;// package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;

public class Publicacion {

    // Nombres de campo que coinciden con el uso en tu repositorio y DTO
    private Long id;                  // Mapea a 'id_publicacion'
    private Long usuarioId;           // Mapea a 'usuario_id'
    private String titulo;
    private String descripcion;       // El servicio lo usará así
    private String categoria;         // Columna del CREATE TABLE
    private String estado;            // Columna del CREATE TABLE
    private String imagenUrl;         // Columna del CREATE TABLE
    private Long zonaSeguraId;        // Columna del CREATE TABLE
    private LocalDateTime fechaPublicacion;

    // Getters y Setters para todos los campos...

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