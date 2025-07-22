package org.devquality.trukea.web.dtos.publicaciones.request;

public class CreatePublicacionRequest {
    private Long productoId;
    private Long usuarioId;
    private String titulo;
    private String descripcion;
    private Long ciudadId;

    public CreatePublicacionRequest() {}

    public CreatePublicacionRequest(Long productoId, Long usuarioId, String titulo,
                                    String descripcion, Long ciudadId) {
        this.productoId = productoId;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ciudadId = ciudadId;
    }

    public boolean isValid() {
        return productoId != null && productoId > 0 &&
                usuarioId != null && usuarioId > 0 &&
                titulo != null && !titulo.trim().isEmpty() &&
                descripcion != null && !descripcion.trim().isEmpty() &&
                ciudadId != null && ciudadId > 0;
    }

    // Getters y setters
    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }
}