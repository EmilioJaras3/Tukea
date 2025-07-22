package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Calidad;
import org.devquality.trukea.persistance.repositories.ICalidadRepository;
import org.devquality.trukea.services.ICalidadService;
import org.devquality.trukea.web.dtos.Calidad.request.CreateCalidadRequest;
import org.devquality.trukea.web.dtos.Calidad.response.CalidadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CalidadServiceImpl implements ICalidadService {

    private final ICalidadRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(CalidadServiceImpl.class);

    public CalidadServiceImpl(ICalidadRepository repository) {
        this.repository = repository;
    }

    private CalidadResponse map(Calidad calidad) {
        return new CalidadResponse(
                calidad.getIdCalidad(),
                calidad.getNivelCalidad(),
                calidad.getDescripcionCalidad()
        );
    }

    @Override
    public ArrayList<CalidadResponse> findAll() {
        try {
            return repository.findAll().stream()
                    .map(this::map)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            logger.error("Error in findAll calidad", e);
            throw new RuntimeException("Error al obtener niveles de calidad", e);
        }
    }

    @Override
    public CalidadResponse findById(Integer id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }

            Calidad calidad = repository.findById(id);
            return calidad != null ? map(calidad) : null;
        } catch (Exception e) {
            logger.error("Error in findById calidad for id: {}", id, e);
            throw new RuntimeException("Error al buscar nivel de calidad", e);
        }
    }

    @Override
    public CalidadResponse create(CreateCalidadRequest request) {
        try {
            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Datos de calidad inválidos");
            }

            Calidad calidad = new Calidad();
            calidad.setNivelCalidad(request.getNivelCalidad());
            calidad.setDescripcionCalidad(request.getDescripcionCalidad());

            Calidad savedCalidad = repository.save(calidad);
            return map(savedCalidad);
        } catch (Exception e) {
            logger.error("Error in create calidad", e);
            throw new RuntimeException("Error al crear nivel de calidad", e);
        }
    }

    @Override
    public CalidadResponse update(Integer id, CreateCalidadRequest request) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }

            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Datos inválidos");
            }

            Calidad existing = repository.findById(id);
            if (existing == null) {
                return null;
            }

            existing.setNivelCalidad(request.getNivelCalidad());
            existing.setDescripcionCalidad(request.getDescripcionCalidad());

            Calidad updated = repository.save(existing);
            return map(updated);
        } catch (Exception e) {
            logger.error("Error in update calidad for id: {}", id, e);
            throw new RuntimeException("Error al actualizar nivel de calidad", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }

            return repository.delete(id);
        } catch (Exception e) {
            logger.error("Error in delete calidad for id: {}", id, e);
            throw new RuntimeException("Error al eliminar nivel de calidad", e);
        }
    }
}