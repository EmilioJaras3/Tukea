package org.devquality.trukea.web.dtos.usuarios.response;

public class CreateUsuarioResponse {
    private Long idUsuario;
    private String nombre;
    private String correo;
    private String ciudad;
    private Integer edad;
    private String fotoPerfil;
    private String descripcion;

    public CreateUsuarioResponse() {}

    public CreateUsuarioResponse(Long idUsuario, String nombre, String correo, String ciudad, Integer edad, String fotoPerfil, String descripcion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.ciudad = ciudad;
        this.edad = edad;
        this.fotoPerfil = fotoPerfil;
        this.descripcion = descripcion;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public String getFotoPerfil() {
        return fotoPerfil;
    }
    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}