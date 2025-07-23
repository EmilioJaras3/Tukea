package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Intercambio;
import org.devquality.trukea.web.dtos.trueques.request.CreateIntercambioRequest;
import org.devquality.trukea.web.dtos.trueques.response.CreateIntercambioResponse;
import java.util.ArrayList;

public interface IIntercambioService {

    ArrayList<Intercambio> findAll();
    Intercambio findById(Long id);
    ArrayList<Intercambio> findByPublicacion1Id(Long publicacionId);
    ArrayList<Intercambio> findByPublicacion2Id(Long publicacionId);
    Intercambio createIntercambio(Intercambio intercambio);
    Intercambio updateIntercambio(Long id, Intercambio intercambio);
    boolean deleteIntercambio(Long id);
    boolean existsById(Long id);
}