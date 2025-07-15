package org.devquality.trukea.web.dtos.categorias.request;

public class CreateCategoriaRequest {

    private String nombre;

    public CreateCategoriaRequest() {
    }

    public CreateCategoriaRequest(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Validación simple
    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty();
    }
}
