package org.devquality.trukea.services;

import org.devquality.trukea.web.dtos.calificaciones.request.CreateCalificacionRequest;
import org.devquality.trukea.web.dtos.calificaciones.response.CalificacionResponse;

import java.util.ArrayList;

public interface ICalificacionesService {

    /**
     * Obtener todas las calificaciones.
     *
     * @return Lista de calificaciones
     */
    ArrayList<CalificacionResponse> findAll();

    /**
     * Buscar calificación por ID.
     *
     * @param id ID de la calificación
     * @return Objeto CalificacionResponse o null si no se encuentra
     */
    CalificacionResponse findById(Long id);

    /**
     * Obtener calificaciones de un usuario específico (calificaciones que recibió).
     *
     * @param usuarioId ID del usuario calificado
     * @return Lista de calificaciones
     */
    ArrayList<CalificacionResponse> findByUsuarioCalificado(Long usuarioId);

    /**
     * Obtener calificaciones realizadas por un usuario (usuario que calificó).
     *
     * @param usuarioId ID del usuario calificador
     * @return Lista de calificaciones hechas por el usuario
     */
    ArrayList<CalificacionResponse> findByUsuarioCalificador(Long usuarioId);

    /**
     * Crear nueva calificación.
     *
     * @param request Objeto con los datos necesarios para crear una calificación
     * @return Calificación creada
     */
    CalificacionResponse create(CreateCalificacionRequest request);

    /**
     * Eliminar calificación por ID.
     *
     * @param id ID de la calificación
     * @return true si se eliminó correctamente, false si no se encontró
     */
    boolean delete(Long id);
}
