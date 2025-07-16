package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Publicacion;
import org.devquality.trukea.persistance.repositories.IPublicacionRepository;
import org.devquality.trukea.services.IPublicacionService;
import org.devquality.trukea.web.dtos.publicaciones.request.CreatePublicacionRequest;
import org.devquality.trukea.web.dtos.publicaciones.response.PublicacionResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PublicacionServiceImpl implements IPublicacionService {

    private final IPublicacionRepository repo;
    public PublicacionServiceImpl(IPublicacionRepository repo) { this.repo = repo; }

    private PublicacionResponse map(Publicacion p) {
        return new PublicacionResponse(
                p.getId(),
                p.getProductoId(),
                p.getUsuarioId(),
                p.getTitulo(),
                p.getDescripcion(),
                p.getCiudadId(),
                p.getFechaPublicacion()
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
        Publicacion p = new Publicacion(null, req.getProductoId(), req.getUsuarioId(),
                req.getTitulo(), req.getDescripcion(), req.getCiudadId(), LocalDateTime.now());
        return map(repo.save(p));
    }

    @Override
    public boolean delete(Long id) {
        return repo.delete(id);
    }
}