package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Publicacion;
import org.devquality.trukea.persistance.repositories.IPublicacionRepository;
import org.devquality.trukea.services.IPublicacionService;
import org.devquality.trukea.web.dtos.publicaciones.request.CreatePublicacionRequest;
import org.devquality.trukea.web.dtos.publicaciones.response.PublicacionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PublicacionServiceImpl implements IPublicacionService {

    private final IPublicacionRepository repo;
    private static final Logger logger = LoggerFactory.getLogger(PublicacionServiceImpl.class);

    public PublicacionServiceImpl(IPublicacionRepository repo) {
        this.repo = repo;
    }

    private PublicacionResponse map(Publicacion p) {
        return new PublicacionResponse(
                p.getId(),           // Long
                p.getProductoId(),   // Long → Long (sin conversión)
                p.getUsuarioId(),    // Long → Long (sin conversión)
                p.getTitulo(),       // String
                p.getDescripcion(),  // String
                p.getCiudadId(),     // Integer
                p.getFechaPublicacion() // LocalDateTime
        );
    }

    @Override
    public ArrayList<PublicacionResponse> findAll() {
        return repo.findAll().stream().map(this::map).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PublicacionResponse findById(Long id) {
        Publicacion p = repo.findById(id);
        return p == null ? null : map(p);
    }

    @Override
    public ArrayList<PublicacionResponse> findByUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId).stream().map(this::map).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PublicacionResponse create(CreatePublicacionRequest req) {
        Publicacion p = new Publicacion();
        p.setId(null);
        p.setProductoId(req.getProductoId());    // Long → Long (sin conversión)
        p.setUsuarioId(req.getUsuarioId());      // Long → Long (sin conversión)
        p.setTitulo(req.getTitulo());
        p.setDescripcion(req.getDescripcion());
        p.setCiudadId(req.getCiudadId() != null ? req.getCiudadId().intValue() : null); // Long → Integer
        p.setFechaPublicacion(LocalDateTime.now());

        return map(repo.save(p));
    }

    @Override
    public PublicacionResponse update(Long id, CreatePublicacionRequest request) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }

            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Datos inválidos");
            }

            Publicacion existing = repo.findById(id);
            if (existing == null) {
                return null;
            }

            if (request.getProductoId() != null) {
                existing.setProductoId(request.getProductoId());    // Long → Long
            }
            if (request.getTitulo() != null) {
                existing.setTitulo(request.getTitulo());
            }
            if (request.getDescripcion() != null) {
                existing.setDescripcion(request.getDescripcion());
            }
            if (request.getCiudadId() != null) {
                existing.setCiudadId(request.getCiudadId().intValue()); // Long → Integer
            }

            Publicacion updated = repo.save(existing);
            return map(updated);
        } catch (Exception e) {
            logger.error("Error updating publicacion", e);
            throw new RuntimeException("Error al actualizar publicación", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return repo.delete(id);
    }
}