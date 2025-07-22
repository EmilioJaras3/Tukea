
        package org.devquality.trukea.persistance.repositories.impl;

import java.sql.*;
import java.time.LocalDate;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.web.dtos.usuarios.response.EstadisticasUsuarioResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EstadisticasRepositoryImpl {
    private final DatabaseConfig databaseConfig;
    private static final Logger logger = LoggerFactory.getLogger(EstadisticasRepositoryImpl.class);

    // ✅ CONSULTA COMPLETA PARA ESTADÍSTICAS DE USUARIO
    private static final String GET_USER_STATISTICS = """
        SELECT 
            u.id_usuario,
            CONCAT(u.nombre, ' ', u.apellido_paterno, 
                   CASE WHEN u.apellido_materno IS NOT NULL 
                        THEN CONCAT(' ', u.apellido_materno) 
                        ELSE '' END) as nombre_completo,
            u.correo,
            u.fecha_nacimiento,
            c.nombre as ciudad_nombre,
            
            -- Estadísticas de productos
            COUNT(DISTINCT p.id_producto) as total_productos,
            
            -- Estadísticas de publicaciones
            COUNT(DISTINCT pub.id_publicacion) as total_publicaciones,
            COUNT(DISTINCT CASE WHEN pub.fecha_cierre > NOW() OR pub.fecha_cierre IS NULL 
                               THEN pub.id_publicacion END) as publicaciones_activas,
            
            -- Estadísticas de solicitudes enviadas
            COUNT(DISTINCT si_env.id_solicitud) as solicitudes_enviadas,
            
            -- Estadísticas de solicitudes recibidas
            COUNT(DISTINCT si_rec.id_solicitud) as solicitudes_recibidas,
            COUNT(DISTINCT CASE WHEN si_rec.status = 'Pendiente' 
                               THEN si_rec.id_solicitud END) as solicitudes_pendientes,
            
            -- Estadísticas de intercambios completados
            COUNT(DISTINCT hi.id_intercambio) as intercambios_completados,
            
            -- Estadísticas de calificación
            AVG(cal.puntaje) as calificacion_promedio,
            COUNT(DISTINCT cal.id_calificacion) as total_calificaciones

        FROM usuarios u
        LEFT JOIN ciudades c ON u.id_ciudad = c.id_ciudad
        LEFT JOIN productos p ON u.id_usuario = p.id_usuario_propietario
        LEFT JOIN publicaciones pub ON u.id_usuario = pub.id_usuario
        LEFT JOIN solicitud_intercambio si_env ON u.id_usuario = si_env.id_usuario_solicitante
        LEFT JOIN solicitud_intercambio si_rec ON pub.id_publicacion = si_rec.id_publicacion
        LEFT JOIN historial_intercambios hi ON (u.id_usuario = hi.id_usuario_1 OR u.id_usuario = hi.id_usuario_2)
        LEFT JOIN calificaciones cal ON u.id_usuario = cal.id_usuario_calificado

        WHERE u.id_usuario = ?
        GROUP BY u.id_usuario
        """;

    public EstadisticasRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public EstadisticasUsuarioResponse getEstadisticasUsuario(Long usuarioId) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_STATISTICS)) {

            preparedStatement.setLong(1, usuarioId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                EstadisticasUsuarioResponse stats = new EstadisticasUsuarioResponse();

                // Datos básicos
                stats.setIdUsuario(rs.getLong("id_usuario"));
                stats.setNombreCompleto(rs.getString("nombre_completo"));
                stats.setCorreo(rs.getString("correo"));
                stats.setCiudadNombre(rs.getString("ciudad_nombre"));

                // Calcular edad
                Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                if (fechaNacimiento != null) {
                    stats.calcularEdad(fechaNacimiento.toLocalDate());
                }

                // Estadísticas de calificación
                Double calificacionPromedio = rs.getDouble("calificacion_promedio");
                if (rs.wasNull()) calificacionPromedio = 0.0;

                stats.setCalificacionPromedio(calificacionPromedio);
                stats.setTotalCalificaciones(rs.getInt("total_calificaciones"));
                stats.calcularEstrellas(calificacionPromedio);

                // Estadísticas de actividad
                stats.setTotalProductos(rs.getInt("total_productos"));
                stats.setTotalPublicaciones(rs.getInt("total_publicaciones"));
                stats.setPublicacionesActivas(rs.getInt("publicaciones_activas"));
                stats.setSolicitudesEnviadas(rs.getInt("solicitudes_enviadas"));
                stats.setSolicitudesRecibidas(rs.getInt("solicitudes_recibidas"));
                stats.setSolicitudesPendientes(rs.getInt("solicitudes_pendientes"));
                stats.setIntercambiosCompletados(rs.getInt("intercambios_completados"));

                // Tiempo activo (necesitamos otra consulta para fecha_registro si no la tienes)
                // stats.calcularTiempoActivo(fechaRegistro);

                return stats;
            }

            return null;
        } catch (SQLException e) {
            logger.error("Error obteniendo estadísticas de usuario: {}", usuarioId, e);
            throw new RuntimeException("Error al obtener estadísticas de usuario", e);
        }
    }
}
