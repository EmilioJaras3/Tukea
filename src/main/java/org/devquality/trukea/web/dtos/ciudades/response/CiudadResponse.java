package org.devquality.trukea.web.dtos.ciudades.response;

public class CiudadResponse {
    private Integer idCiudad;
    private String nombre;

    public CiudadResponse() {}

    public CiudadResponse(Integer idCiudad, String nombre) {
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