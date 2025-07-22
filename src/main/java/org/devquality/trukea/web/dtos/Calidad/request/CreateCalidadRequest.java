package org.devquality.trukea.web.dtos.Calidad.request;

public class CreateCalidadRequest {
    private String nivelCalidad;
    private String descripcionCalidad;

    public CreateCalidadRequest() {}

    public CreateCalidadRequest(String nivelCalidad, String descripcionCalidad) {
        this.nivelCalidad = nivelCalidad;
        this.descripcionCalidad = descripcionCalidad;
    }

    public boolean isValid() {
        return nivelCalidad != null && !nivelCalidad.trim().isEmpty();
    }

    // Getters y setters
    public String getNivelCalidad() {
        return nivelCalidad;
    }

    public void setNivelCalidad(String nivelCalidad) {
        this.nivelCalidad = nivelCalidad;
    }

    public String getDescripcionCalidad() {
        return descripcionCalidad;
    }

    public void setDescripcionCalidad(String descripcionCalidad) {
        this.descripcionCalidad = descripcionCalidad;
    }
}