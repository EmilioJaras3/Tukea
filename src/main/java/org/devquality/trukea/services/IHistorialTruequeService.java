package org.devquality.trukea.services;

import org.devquality.trukea.web.dtos.historiales.response.CreateHistorialTruequeResponse;
import org.devquality.trukea.persistance.entities.HistorialTrueque;

import java.util.ArrayList;

public interface IHistorialTruequeService {
    ArrayList<CreateHistorialTruequeResponse> findAll();
    CreateHistorialTruequeResponse findById(Long id);
    ArrayList<CreateHistorialTruequeResponse> findByUsuarioId(Long usuarioId);
    CreateHistorialTruequeResponse create(HistorialTrueque historial);
}
