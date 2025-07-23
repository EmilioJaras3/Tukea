package org.devquality.trukea.web.dtos.usuarios.request;

public class CreateUsuarioRequest {
    private String nombre;
    private String correo;
    private String contraseña;
    private String ciudad;
    private Integer edad;
    private String fotoPerfil;
    private String descripcion;

    public CreateUsuarioRequest() {}

    public CreateUsuarioRequest(String nombre, String correo, String contraseña, String ciudad, Integer edad, String fotoPerfil, String descripcion) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.ciudad = ciudad;
        this.edad = edad;
        this.fotoPerfil = fotoPerfil;
        this.descripcion = descripcion;
    }

    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty() &&
                correo != null && !correo.trim().isEmpty() &&
                contraseña != null && !contraseña.trim().isEmpty();
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
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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