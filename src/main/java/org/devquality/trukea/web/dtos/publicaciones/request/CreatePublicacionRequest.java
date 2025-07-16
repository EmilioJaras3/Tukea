package org.devquality.trukea.web.dtos.publicaciones.request;

public class CreatePublicacionRequest {
    private Long productoId;
    private Long usuarioId;
    private String titulo;
    private String descripcion;
    private Integer ciudadId; // opcional

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCiudadId() { return ciudadId; }
    public void setCiudadId(Integer ciudadId) { this.ciudadId = ciudadId; }
}
