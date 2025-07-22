package org.devquality.trukea.web.dtos.categorias.responses;

public class CreateCategoriaResponse {
    private Integer idCategoria;
    private String categoria;
    private String descripcionCategoria;

    public CreateCategoriaResponse() {}

    public CreateCategoriaResponse(Integer idCategoria, String categoria, String descripcionCategoria) {
        this.idCategoria = idCategoria;
        this.categoria = categoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    // Getters y setters
    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcionCategoria() { return descripcionCategoria; }
    public void setDescripcionCategoria(String descripcionCategoria) { this.descripcionCategoria = descripcionCategoria; }
}