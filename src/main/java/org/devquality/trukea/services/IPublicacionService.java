package org.devquality.trukea.services;

import org.devquality.trukea.web.dtos.publicaciones.request.CreatePublicacionRequest;
import org.devquality.trukea.web.dtos.publicaciones.response.PublicacionResponse;
import java.util.ArrayList;

public interface IPublicacionService {
    ArrayList<PublicacionResponse> findAll();
    PublicacionResponse findById(Long id);
    ArrayList<PublicacionResponse> findByUsuario(Long usuarioId);
    PublicacionResponse create(CreatePublicacionRequest request);
    PublicacionResponse update(Long id, CreatePublicacionRequest request);  // ← AGREGAR ESTA LÍNEA
    boolean delete(Long id);

}