
package org.devquality.trukea.persistance.entities;

import java.time.LocalDateTime;
import org.devquality.trukea.persistance.entities.Usuario;

public class Producto {
    private Long id_producto;
    private String nombre;
    private String descripcion;
    private Integer valorEstimado = 0; // Valor por defecto
    private Integer idCalidad;
    private Long categoriaId;
    private Long idUsuarioPropietario;
    private LocalDateTime fechaCreacion;
    private Boolean activo;

    public Producto() {}

    public Producto(Long id, String nombre, String descripcion, Integer valorEstimado,
                    Integer idCalidad, Long categoriaId, Long idUsuarioPropietario) {
        this.id_producto = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valorEstimado = valorEstimado;
        this.idCalidad = idCalidad;
        this.categoriaId = categoriaId;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }

    // Getters y Setters
    public Long getId() { return id_producto; }
    public void setId(Long id) { this.id_producto = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getValorEstimado() { return valorEstimado; }
    public void setValorEstimado(int valorEstimado) { this.valorEstimado = valorEstimado; }

    public Integer getIdCalidad() { return idCalidad; }
    public void setIdCalidad(Integer idCalidad) { this.idCalidad = idCalidad; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public Long getIdUsuarioPropietario() { return idUsuarioPropietario; }
    public void setIdUsuarioPropietario(Long idUsuarioPropietario) {
        this.idUsuarioPropietario = idUsuarioPropietario;
    }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    // ⚠ MANTENER COMPATIBILIDAD CON CÓDIGO EXISTENTE
    public Long getUsuarioId() { return idUsuarioPropietario; }
    public void setUsuarioId(Long usuarioId) { this.idUsuarioPropietario = usuarioId; }
}
