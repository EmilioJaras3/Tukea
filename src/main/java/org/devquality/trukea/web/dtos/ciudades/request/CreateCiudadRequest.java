package org.devquality.trukea.web.dtos.ciudades.request;

public class CreateCiudadRequest {
    private String nombre;

    public CreateCiudadRequest() {}

    public CreateCiudadRequest(String nombre) {
        this.nombre = nombre;
    }

    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty();
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}