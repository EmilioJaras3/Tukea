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

    public PublicacionServiceImpl(IPublicacionRepository repo) {
        this.repo = repo;
    }

    private PublicacionResponse mapToResponse(Publicacion p) {
        if (p == null) return null;
        return new PublicacionResponse(
                p.getId(),
                p.getUsuarioId(),
                p.getTitulo(),
                p.getDescripcion(),
                // Aquí debes agregar los campos adicionales si los tienes en PublicacionResponse
                p.getCategoria(),
                p.getEstado(),
                p.getImagenUrl(),
                p.getZonaSeguraId(),
                p.getFechaPublicacion()
        );
    }

    @Override
    public ArrayList<PublicacionResponse> findAll() {
        return repo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PublicacionResponse findById(Long id) {
        Publicacion p = repo.findById(id);
        return mapToResponse(p);
    }

    @Override
    public ArrayList<PublicacionResponse> findByUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PublicacionResponse create(CreatePublicacionRequest req) {
        Publicacion p = new Publicacion();
        // Convertimos el request a una entidad
        p.setUsuarioId(req.getUsuarioId());
        p.setTitulo(req.getTitulo());
        p.setDescripcion(req.getDescripcion());
        // Establecer valores por defecto obligatorios
        p.setCategoria(req.getCategoria()); // El request debe tener este campo
        p.setEstado("Nuevo"); // Por defecto, una publicación nueva tiene este estado
        p.setImagenUrl(req.getImagenUrl()); // El request debe tener este campo
        p.setZonaSeguraId(req.getZonaSeguraId()); // Puede ser nulo

        Publicacion publicacionGuardada = repo.save(p);
        return mapToResponse(publicacionGuardada);
    }

    @Override
    public PublicacionResponse update(Long id, CreatePublicacionRequest req) {
        Publicacion p = repo.findById(id);
        if (p == null) {
            throw new RuntimeException("Publicación no encontrada con ID: " + id);
        }
        // Actualizamos los campos que se pueden cambiar
        p.setTitulo(req.getTitulo());
        p.setDescripcion(req.getDescripcion());
        p.setCategoria(req.getCategoria());
        p.setEstado(req.getEstado()); // El estado puede cambiar (ej: 'Vendido')
        p.setImagenUrl(req.getImagenUrl());
        p.setZonaSeguraId(req.getZonaSeguraId());

        Publicacion publicacionActualizada = repo.update(p);
        return mapToResponse(publicacionActualizada);
    }

    @Override
    public boolean delete(Long id) {
        return repo.delete(id);
    }
}