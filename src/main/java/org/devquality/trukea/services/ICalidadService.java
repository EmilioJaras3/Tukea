package org.devquality.trukea.services;

import org.devquality.trukea.web.dtos.Calidad.request.CreateCalidadRequest;
import org.devquality.trukea.web.dtos.Calidad.response.CalidadResponse;
import java.util.ArrayList;

public interface ICalidadService {

    /**
     * Obtener todos los niveles de calidad
     */
    ArrayList<CalidadResponse> findAll();

    /**
     * Buscar calidad por ID
     */
    CalidadResponse findById(Integer id);

    /**
     * Crear nuevo nivel de calidad
     */
    CalidadResponse create(CreateCalidadRequest request);

    /**
     * Actualizar nivel de calidad
     */
    CalidadResponse update(Integer id, CreateCalidadRequest request);

    /**
     * Eliminar nivel de calidad
     */
    boolean delete(Integer id);
}