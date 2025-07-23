package org.devquality.trukea.web.dtos.productos.request;

public class CreateProductoRequest {
    private Long idUsuario;
    private Long idCategoria;
    private String nombreProducto;
    private String descripcionProducto;
    private Integer valorEstimado;
    private Integer idCalidad;
    // Nuevos campos para adaptarse a la BD y modelo
    private String categoria;
    private String estado;
    private String imagenUrl;
    private Long zonaSeguraId;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Integer getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(Integer valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public Integer getIdCalidad() {
        return idCalidad;
    }

    public void setIdCalidad(Integer idCalidad) {
        this.idCalidad = idCalidad;
    }

    // Getters y setters nuevos
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Long getZonaSeguraId() { return zonaSeguraId; }
    public void setZonaSeguraId(Long zonaSeguraId) { this.zonaSeguraId = zonaSeguraId; }

    public boolean isValid() {
        return idUsuario != null && idUsuario > 0 &&
               idCategoria != null && idCategoria > 0 &&
               nombreProducto != null && !nombreProducto.isEmpty();
    }
}
