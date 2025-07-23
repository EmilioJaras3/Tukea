package org.devquality.trukea.web.dtos.publicaciones.request;

/**
 * Este es el DTO que se recibe desde el cliente (Postman/Frontend)
 * para crear o actualizar una publicación. Debe contener todos los
 * campos que el usuario puede proporcionar.
 */
public class CreatePublicacionRequest {

    // --- CAMPOS CORREGIDOS Y AMPLIADOS ---
    private Long usuarioId;
    private String titulo;
    private String descripcion;
    private String categoria;    // CAMPO AÑADIDO
    private String estado;       // CAMPO AÑADIDO (para actualizaciones)
    private String imagenUrl;    // CAMPO AÑADIDO
    private Long zonaSeguraId;   // CAMPO AÑADIDO

    // Dejé el 'productoId' y 'ciudadId' de tu versión original por si los usas
    // en alguna otra lógica, pero no están en tu `CREATE TABLE` script.
    // Si no los usas, puedes borrarlos para mayor limpieza.
    private Long productoId;
    private Long ciudadId;

    // Constructor vacío requerido para que Javalin/Jackson funcione
    public CreatePublicacionRequest() {}

    // Método para validar que los campos OBLIGATORIOS no vengan vacíos
    public boolean isValid() {
        return usuarioId != null && usuarioId > 0 &&
                titulo != null && !titulo.trim().isEmpty() &&
                descripcion != null && !descripcion.trim().isEmpty() &&
                categoria != null && !categoria.trim().isEmpty();
    }

    // --- GETTERS Y SETTERS PARA TODOS LOS CAMPOS ---

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Long getZonaSeguraId() { return zonaSeguraId; }
    public void setZonaSeguraId(Long zonaSeguraId) { this.zonaSeguraId = zonaSeguraId; }

    // Getters y setters para los campos antiguos (borrar si no los usas)
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Long getCiudadId() { return ciudadId; }
    public void setCiudadId(Long ciudadId) { this.ciudadId = ciudadId; }
}