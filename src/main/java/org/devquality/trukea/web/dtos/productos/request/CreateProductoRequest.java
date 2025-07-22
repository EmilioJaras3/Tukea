package org.devquality.trukea.web.dtos.productos.request;

public class CreateProductoRequest {
    private String nombreProducto;
    private String descripcionProducto;
    private Integer valorEstimado;
    private Integer idCalidad;
    private Integer idCategoria;

    public CreateProductoRequest() {}

    public CreateProductoRequest(String nombreProducto, String descripcionProducto,
                                 Integer valorEstimado, Integer idCalidad, Integer idCategoria) {
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.valorEstimado = valorEstimado;
        this.idCalidad = idCalidad;
        this.idCategoria = idCategoria;
    }

    public boolean isValid() {
        return nombreProducto != null && !nombreProducto.trim().isEmpty() &&
                valorEstimado != null && valorEstimado > 0 &&
                idCategoria != null && idCategoria > 0;
    }

    // Getters y setters
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

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
}