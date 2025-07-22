package org.devquality.trukea.services;

import org.devquality.trukea.web.dtos.ciudades.request.CreateCiudadRequest;
import org.devquality.trukea.web.dtos.ciudades.response.CiudadResponse;
import java.util.ArrayList;

public interface ICiudadService {

    /**
     * Obtener todas las ciudades
     */
    ArrayList<CiudadResponse> findAll();

    /**
     * Buscar ciudad por ID
     */
    CiudadResponse findById(Integer id);

    /**
     * Crear nueva ciudad
     */
    CiudadResponse create(CreateCiudadRequest request);

    /**
     * Actualizar ciudad
     */
    CiudadResponse update(Integer id, CreateCiudadRequest request);

    /**
     * Eliminar ciudad
     */
    boolean delete(Integer id);
}