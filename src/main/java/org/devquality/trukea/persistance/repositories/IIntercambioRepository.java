package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Intercambio;

import java.util.ArrayList;

public interface IIntercambioRepository {

    // READ operations
    ArrayList<Intercambio> findAllIntercambios();
    Intercambio findById(Long id);
    ArrayList<Intercambio> findByPublicacion1Id(Long publicacionId);
    ArrayList<Intercambio> findByPublicacion2Id(Long publicacionId);

    // CREATE operation
    Intercambio createIntercambio(Intercambio intercambio);

    // UPDATE operation
    Intercambio updateIntercambio(Intercambio intercambio);

    // DELETE operation
    boolean deleteIntercambio(Long id);

    // UTILITY operations
    boolean existsById(Long id);
}