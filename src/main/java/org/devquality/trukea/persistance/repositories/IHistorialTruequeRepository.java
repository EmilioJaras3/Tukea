package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.HistorialTrueque;
import java.util.ArrayList;

public interface IHistorialTruequeRepository {
    ArrayList<HistorialTrueque> findAll();
    ArrayList<HistorialTrueque> findByUsuarioId(Long usuarioId);
    HistorialTrueque findById(Long id);
    HistorialTrueque save(HistorialTrueque historial); // Para crear al aceptar un trueque
}
