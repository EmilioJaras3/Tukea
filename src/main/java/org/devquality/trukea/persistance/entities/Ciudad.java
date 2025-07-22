package org.devquality.trukea.persistance.entities;

public class Ciudad {
    private Integer idCiudad;
    private String nombre;

    public Ciudad() {}

    public Ciudad(Integer idCiudad, String nombre) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
    }

    // Getters y setters
    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}