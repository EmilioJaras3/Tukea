package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Calificaciones;

import java.util.ArrayList;

public interface ICalificacionesRepository {

    /**
     * Obtener todas las calificaciones.
     */
    ArrayList<Calificaciones> findAll();

    /**
     * Buscar calificación por ID.
     */
    Calificaciones findById(Long id);

    /**
     * Obtener calificaciones que recibió un usuario.
     */
    ArrayList<Calificaciones> findByUsuarioCalificado(Long usuarioId);

    /**
     * Obtener calificaciones hechas por un usuario.
     */
    ArrayList<Calificaciones> findByUsuarioCalificador(Long usuarioId);

    /**
     * Guardar nueva calificación.
     */
    Calificaciones save(Calificaciones calificacion);

    /**
     * Eliminar calificación por ID.
     */
    boolean delete(Long id);
}
