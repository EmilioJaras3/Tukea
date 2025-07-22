package org.devquality.trukea.web.dtos.categorias.requests;

public class CreateCategoriaRequest {
    private String categoria;
    private String descripcionCategoria;

    public CreateCategoriaRequest() {}

    public CreateCategoriaRequest(String categoria, String descripcionCategoria) {
        this.categoria = categoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    public boolean isValid() {
        return categoria != null && !categoria.trim().isEmpty();
    }

    // Getters y setters
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }
}