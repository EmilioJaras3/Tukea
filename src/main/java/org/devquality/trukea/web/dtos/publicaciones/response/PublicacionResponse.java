package org.devquality.trukea.web.dtos.publicaciones.response;

import java.time.LocalDateTime;

/**
 * Este es el DTO que se enviará como respuesta al cliente.
 * Su estructura refleja la tabla 'publicaciones' de la base de datos.
 */
public class PublicacionResponse {

    // Campos que coinciden con la tabla 'publicaciones'
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String estado;
    private String imagenUrl;
    private Long zonaSeguraId;
    private LocalDateTime fechaPublicacion;

    /**
     * El único constructor necesario. Recibe todos los datos de la publicación
     * y los asigna a las propiedades de la clase.
     */
    public PublicacionResponse(Long id, Long usuarioId, String titulo, String descripcion,
                               String categoria, String estado, String imagenUrl,
                               Long zonaSeguraId, LocalDateTime fechaPublicacion) {

        // CORRECCIÓN: Se asignan TODOS los valores recibidos
        this.id = id;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estado = estado;
        this.imagenUrl = imagenUrl;
        this.zonaSeguraId = zonaSeguraId;
        this.fechaPublicacion = fechaPublicacion;
    }

    // --- GETTERS para que Jackson (la librería de Javalin) pueda leer los valores ---
    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getCategoria() { return categoria; }
    public String getEstado() { return estado; }
    public String getImagenUrl() { return imagenUrl; }
    public Long getZonaSeguraId() { return zonaSeguraId; }
    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
}