package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Trueque;
import org.devquality.trukea.web.dtos.trueques.request.CreateTruequeRequest;
import org.devquality.trukea.web.dtos.trueques.response.CreateTruequeResponse;
import java.util.ArrayList;

public interface ITruequeService {

    ArrayList<CreateTruequeResponse> findAll();
    Trueque findById(Long id);
    ArrayList<CreateTruequeResponse> findByEstado(String estado);
    ArrayList<CreateTruequeResponse> findByProductoOfrecidoId(Long productoId);
    ArrayList<CreateTruequeResponse> findByProductoDeseadoId(Long productoId);

    // Nuevos métodos necesarios
    ArrayList<CreateTruequeResponse> findByUsuarioOferente(Long usuarioId);
    ArrayList<CreateTruequeResponse> findByUsuarioReceptor(Long usuarioId);
    boolean aceptarTrueque(Long id);
    boolean rechazarTrueque(Long id);

    CreateTruequeResponse createTrueque(CreateTruequeRequest request);
    CreateTruequeResponse updateTrueque(Long id, CreateTruequeRequest request);
    boolean deleteTrueque(Long id);
    boolean existsById(Long id);
    int countByEstado(String estado);
    boolean existsByProductos(Long productoOfrecidoId, Long productoDeseadoId);
}