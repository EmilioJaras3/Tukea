package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.HistorialTrueque;
import org.devquality.trukea.persistance.repositories.IHistorialTruequeRepository;
import org.devquality.trukea.services.IHistorialTruequeService;
import org.devquality.trukea.web.dtos.historial.response.HistorialTruequeResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HistorialTruequeServiceImpl implements IHistorialTruequeService {

    private final IHistorialTruequeRepository repo;
    public HistorialTruequeServiceImpl(IHistorialTruequeRepository repo) { this.repo = repo; }

    private HistorialTruequeResponse map(HistorialTrueque h) {
        return new HistorialTruequeResponse(
                h.getId(),
                h.getProductoOfrecidoId(),
                h.getProductoDeseadoId(),
                h.getUsuarioOfrecidoId(),
                h.getUsuarioRecibidoId(),
                h.getFecha()
        );
    }

    @Override
    public ArrayList<HistorialTruequeResponse> findAll() {
        return repo.findAll().stream()
                .map(this::map)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<HistorialTruequeResponse> findByUsuarioId(Long uid) {
        return repo.findByUsuarioId(uid).stream()
                .map(this::map)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public HistorialTruequeResponse findById(Long id) {
        HistorialTrueque h = repo.findById(id);
        return h == null ? null : map(h);
    }

    @Override
    public void crearDesdeTrueque(Long truequeId, Long prodO, Long prodD, Long userO, Long userR) {
        HistorialTrueque h = new HistorialTrueque(null, truequeId, prodO, prodD,
                userO, userR, LocalDateTime.now());
        repo.save(h);
    }
}
