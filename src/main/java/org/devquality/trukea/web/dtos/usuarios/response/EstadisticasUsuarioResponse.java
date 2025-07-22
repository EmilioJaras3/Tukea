// =====================================================
// ✅ 1. DTO PARA ESTADÍSTICAS DE USUARIO
// =====================================================

package org.devquality.trukea.web.dtos.usuarios.response;

import java.time.LocalDate;

public class EstadisticasUsuarioResponse {
    private Long idUsuario;
    private String nombreCompleto;
    private String correo;
    private String ciudadNombre;

    // Estadísticas de edad
    private Integer edad;
    private String fechaNacimiento;

    // Estadísticas de calificación
    private Double calificacionPromedio;
    private Integer totalCalificaciones;
    private String estrellasTexto; // "⭐⭐⭐⭐⭐"
    private Integer estrellasEnteras;
    private Boolean tieneMediaEstrella;

    // Estadísticas de actividad
    private Integer totalProductos;
    private Integer totalPublicaciones;
    private Integer publicacionesActivas;
    private Integer solicitudesEnviadas;
    private Integer solicitudesRecibidas;
    private Integer solicitudesPendientes;
    private Integer intercambiosCompletados;

    // Estadísticas de tiempo
    private String fechaRegistro;
    private String tiempoActivo; // "2 años, 3 meses"

    // Constructores
    public EstadisticasUsuarioResponse() {}

    public EstadisticasUsuarioResponse(Long idUsuario, String nombreCompleto, String correo) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.calificacionPromedio = 0.0;
        this.totalCalificaciones = 0;
        this.estrellasTexto = "☆☆☆☆☆"; // Estrellas vacías por defecto
        this.estrellasEnteras = 0;
        this.tieneMediaEstrella = false;
    }

    // Método para calcular edad automáticamente
    public void calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento != null) {
            LocalDate hoy = LocalDate.now();
            this.edad = hoy.getYear() - fechaNacimiento.getYear();

            // Ajustar si no ha cumplido años este año
            if (hoy.getDayOfYear() < fechaNacimiento.getDayOfYear()) {
                this.edad--;
            }

            this.fechaNacimiento = fechaNacimiento.toString();
        } else {
            this.edad = null;
            this.fechaNacimiento = null;
        }
    }

    // Método para calcular representación de estrellas
    public void calcularEstrellas(Double calificacion) {
        if (calificacion == null || calificacion == 0.0) {
            this.calificacionPromedio = 0.0;
            this.estrellasTexto = "☆☆☆☆☆";
            this.estrellasEnteras = 0;
            this.tieneMediaEstrella = false;
            return;
        }

        this.calificacionPromedio = Math.round(calificacion * 100.0) / 100.0; // 2 decimales

        // Calcular estrellas enteras y media estrella
        this.estrellasEnteras = (int) Math.floor(calificacion);
        double decimal = calificacion - this.estrellasEnteras;
        this.tieneMediaEstrella = decimal >= 0.5;

        // Generar texto de estrellas
        StringBuilder estrellas = new StringBuilder();

        // Estrellas llenas
        for (int i = 0; i < this.estrellasEnteras; i++) {
            estrellas.append("⭐");
        }

        // Media estrella si aplica
        if (this.tieneMediaEstrella && this.estrellasEnteras < 5) {
            estrellas.append("⭐"); // En texto usamos estrella completa
        }

        // Completar con estrellas vacías hasta 5
        int totalMostradas = this.estrellasEnteras + (this.tieneMediaEstrella ? 1 : 0);
        for (int i = totalMostradas; i < 5; i++) {
            estrellas.append("☆");
        }

        this.estrellasTexto = estrellas.toString();
    }

    // Método para calcular tiempo activo
    public void calcularTiempoActivo(LocalDate fechaRegistro) {
        if (fechaRegistro != null) {
            LocalDate hoy = LocalDate.now();

            int años = hoy.getYear() - fechaRegistro.getYear();
            int meses = hoy.getMonthValue() - fechaRegistro.getMonthValue();

            if (meses < 0) {
                años--;
                meses += 12;
            }

            if (años > 0 && meses > 0) {
                this.tiempoActivo = años + " año" + (años > 1 ? "s" : "") + ", " +
                        meses + " mes" + (meses > 1 ? "es" : "");
            } else if (años > 0) {
                this.tiempoActivo = años + " año" + (años > 1 ? "s" : "");
            } else if (meses > 0) {
                this.tiempoActivo = meses + " mes" + (meses > 1 ? "es" : "");
            } else {
                this.tiempoActivo = "Nuevo usuario";
            }

            this.fechaRegistro = fechaRegistro.toString();
        }
    }

    // Getters y Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getCiudadNombre() { return ciudadNombre; }
    public void setCiudadNombre(String ciudadNombre) { this.ciudadNombre = ciudadNombre; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Double getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
        this.calcularEstrellas(calificacionPromedio);
    }

    public Integer getTotalCalificaciones() { return totalCalificaciones; }
    public void setTotalCalificaciones(Integer totalCalificaciones) {
        this.totalCalificaciones = totalCalificaciones;
    }

    public String getEstrellasTexto() { return estrellasTexto; }
    public void setEstrellasTexto(String estrellasTexto) { this.estrellasTexto = estrellasTexto; }

    public Integer getEstrellasEnteras() { return estrellasEnteras; }
    public void setEstrellasEnteras(Integer estrellasEnteras) { this.estrellasEnteras = estrellasEnteras; }

    public Boolean getTieneMediaEstrella() { return tieneMediaEstrella; }
    public void setTieneMediaEstrella(Boolean tieneMediaEstrella) {
        this.tieneMediaEstrella = tieneMediaEstrella;
    }

    // Resto de getters y setters...
    public Integer getTotalProductos() { return totalProductos; }
    public void setTotalProductos(Integer totalProductos) { this.totalProductos = totalProductos; }

    public Integer getTotalPublicaciones() { return totalPublicaciones; }
    public void setTotalPublicaciones(Integer totalPublicaciones) {
        this.totalPublicaciones = totalPublicaciones;
    }

    public Integer getPublicacionesActivas() { return publicacionesActivas; }
    public void setPublicacionesActivas(Integer publicacionesActivas) {
        this.publicacionesActivas = publicacionesActivas;
    }

    public Integer getSolicitudesEnviadas() { return solicitudesEnviadas; }
    public void setSolicitudesEnviadas(Integer solicitudesEnviadas) {
        this.solicitudesEnviadas = solicitudesEnviadas;
    }

    public Integer getSolicitudesRecibidas() { return solicitudesRecibidas; }
    public void setSolicitudesRecibidas(Integer solicitudesRecibidas) {
        this.solicitudesRecibidas = solicitudesRecibidas;
    }

    public Integer getSolicitudesPendientes() { return solicitudesPendientes; }
    public void setSolicitudesPendientes(Integer solicitudesPendientes) {
        this.solicitudesPendientes = solicitudesPendientes;
    }

    public Integer getIntercambiosCompletados() { return intercambiosCompletados; }
    public void setIntercambiosCompletados(Integer intercambiosCompletados) {
        this.intercambiosCompletados = intercambiosCompletados;
    }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getTiempoActivo() { return tiempoActivo; }
    public void setTiempoActivo(String tiempoActivo) { this.tiempoActivo = tiempoActivo; }
}
