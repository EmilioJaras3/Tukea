package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Intercambio;
import org.devquality.trukea.persistance.repositories.IIntercambioRepository;
import org.devquality.trukea.services.IIntercambioService;
import org.devquality.trukea.web.dtos.trueques.request.CreateIntercambioRequest;
import org.devquality.trukea.web.dtos.trueques.response.CreateIntercambioResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class IntercambioServiceImpl implements IIntercambioService {

    private final IIntercambioRepository intercambioRepository;
    private static final Logger logger = LoggerFactory.getLogger(IntercambioServiceImpl.class);

    public IntercambioServiceImpl(IIntercambioRepository intercambioRepository) {
        this.intercambioRepository = intercambioRepository;
    }

    @Override
    public ArrayList<Intercambio> findAll() {
        return intercambioRepository.findAllIntercambios();
    }

    @Override
    public Intercambio findById(Long id) {
        return intercambioRepository.findById(id);
    }

    @Override
    public ArrayList<Intercambio> findByPublicacion1Id(Long publicacionId) {
        return intercambioRepository.findByPublicacion1Id(publicacionId);
    }

    @Override
    public ArrayList<Intercambio> findByPublicacion2Id(Long publicacionId) {
        return intercambioRepository.findByPublicacion2Id(publicacionId);
    }

    @Override
    public Intercambio createIntercambio(Intercambio intercambio) {
        return intercambioRepository.createIntercambio(intercambio);
    }

    @Override
    public Intercambio updateIntercambio(Long id, Intercambio intercambio) {
        intercambio.setId(id);
        return intercambioRepository.updateIntercambio(intercambio);
    }

    @Override
    public boolean deleteIntercambio(Long id) {
        return intercambioRepository.deleteIntercambio(id);
    }

    @Override
    public boolean existsById(Long id) {
        return intercambioRepository.existsById(id);
    }
}