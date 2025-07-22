package org.devquality.trukea.web.dtos.publicaciones.response;

import java.time.LocalDateTime;

public class PublicacionResponse {
    private Long id;
    private Long productoId;
    private Long usuarioId;
    private String titulo;
    private String descripcion;
    private Integer ciudadId;
    private LocalDateTime fechaPublicacion;

    public PublicacionResponse(Long id, Long productoId, Long usuarioId,
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

    // getters...
    public Long getId() { return id; }
    public Long getProductoId() { return productoId; }
    public Long getUsuarioId() { return usuarioId; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Integer getCiudadId() { return ciudadId; }
    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
}