package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Calificaciones;
import org.devquality.trukea.persistance.repositories.ICalificacionesRepository;
import org.devquality.trukea.services.ICalificacionesService;
import org.devquality.trukea.web.dtos.calificaciones.request.CreateCalificacionRequest;
import org.devquality.trukea.web.dtos.calificaciones.response.CalificacionResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CalificacionesServiceImpl implements ICalificacionesService {

    private final ICalificacionesRepository calificacionesRepository;

    public CalificacionesServiceImpl(ICalificacionesRepository calificacionesRepository) {
        this.calificacionesRepository = calificacionesRepository;
    }

    private CalificacionResponse mapToResponse(Calificaciones calificacion) {
        return new CalificacionResponse(
                calificacion.getId(),
                calificacion.getUsuarioCalificadorId(),
                calificacion.getUsuarioCalificadoId(),
                calificacion.getPuntuacion(),
                calificacion.getComentario(),
                calificacion.getFecha()
        );
    }

    @Override
    public ArrayList<CalificacionResponse> findAll() {
        return calificacionesRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public CalificacionResponse findById(Long id) {
        Calificaciones calificacion = calificacionesRepository.findById(id);
        return calificacion != null ? mapToResponse(calificacion) : null;
    }

    @Override
    public ArrayList<CalificacionResponse> findByUsuarioCalificado(Long usuarioId) {
        return calificacionesRepository.findByUsuarioCalificado(usuarioId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<CalificacionResponse> findByUsuarioCalificador(Long usuarioId) {
        return calificacionesRepository.findByUsuarioCalificador(usuarioId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public CalificacionResponse create(CreateCalificacionRequest request) {
        Calificaciones calificacion = new Calificaciones();
        calificacion.setUsuarioCalificadorId(request.getUsuarioCalificadorId());
        calificacion.setUsuarioCalificadoId(request.getUsuarioCalificadoId());
        calificacion.setPuntuacion(request.getPuntuacion());
        calificacion.setComentario(request.getComentario());
        calificacion.setFecha(LocalDateTime.now());

        Calificaciones savedCalificacion = calificacionesRepository.save(calificacion);
        return mapToResponse(savedCalificacion);
    }

    @Override
    public boolean delete(Long id) {
        return calificacionesRepository.delete(id);
    }
}
