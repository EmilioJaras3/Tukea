package org.devquality.trukea.web.dtos.zona.request;

public class CreateZonaRequest {
    private String nombreZona;
    private String direccion;

    public CreateZonaRequest() {}

    public CreateZonaRequest(String nombreZona, String direccion) {
        this.nombreZona = nombreZona;
        this.direccion = direccion;
    }

    public boolean isValid() {
        return nombreZona != null && !nombreZona.trim().isEmpty() &&
                direccion != null && !direccion.trim().isEmpty();
    }

    // Getters y setters
    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}