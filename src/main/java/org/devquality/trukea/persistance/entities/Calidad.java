package org.devquality.trukea.persistance.entities;

public class Calidad {
    private Integer idCalidad;
    private String nivelCalidad;
    private String descripcionCalidad;

    public Calidad() {}

    public Calidad(Integer idCalidad, String nivelCalidad, String descripcionCalidad) {
        this.idCalidad = idCalidad;
        this.nivelCalidad = nivelCalidad;
        this.descripcionCalidad = descripcionCalidad;
    }

    // Getters y setters
    public Integer getIdCalidad() {
        return idCalidad;
    }

    public void setIdCalidad(Integer idCalidad) {
        this.idCalidad = idCalidad;
    }

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