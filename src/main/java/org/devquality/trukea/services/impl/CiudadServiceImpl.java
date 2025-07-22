package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Ciudad;
import org.devquality.trukea.persistance.repositories.ICiudadRepository;
import org.devquality.trukea.services.ICiudadService;
import org.devquality.trukea.web.dtos.ciudades.request.CreateCiudadRequest;
import org.devquality.trukea.web.dtos.ciudades.response.CiudadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CiudadServiceImpl implements ICiudadService {

    private final ICiudadRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(CiudadServiceImpl.class);

    public CiudadServiceImpl(ICiudadRepository repository) {
        this.repository = repository;
    }

    private CiudadResponse map(Ciudad ciudad) {
        return new CiudadResponse(
                ciudad.getIdCiudad(),
                ciudad.getNombre()
        );
    }

    @Override
    public ArrayList<CiudadResponse> findAll() {
        try {
            return repository.findAll().stream()
                    .map(this::map)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            logger.error("Error in findAll ciudades", e);
            throw new RuntimeException("Error al obtener ciudades", e);
        }
    }

    @Override
    public CiudadResponse findById(Integer id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }

            Ciudad ciudad = repository.findById(id);
            return ciudad != null ? map(ciudad) : null;
        } catch (Exception e) {
            logger.error("Error in findById ciudad for id: {}", id, e);
            throw new RuntimeException("Error al buscar ciudad", e);
        }
    }

    @Override
    public CiudadResponse create(CreateCiudadRequest request) {
        try {
            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Datos de ciudad inválidos");
            }

            Ciudad ciudad = new Ciudad();
            ciudad.setNombre(request.getNombre());

            Ciudad savedCiudad = repository.save(ciudad);
            return map(savedCiudad);
        } catch (Exception e) {
            logger.error("Error in create ciudad", e);
            throw new RuntimeException("Error al crear ciudad", e);
        }
    }

    @Override
    public CiudadResponse update(Integer id, CreateCiudadRequest request) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID inválido");
            }

            if (request == null || !request.isValid()) {
                throw new IllegalArgumentException("Datos inválidos");
            }

            Ciudad existing = repository.findById(id);
            if (existing == null) {
                return null;
            }

            existing.setNombre(request.getNombre());
            Ciudad updated = repository.save(existing);
            return map(updated);
        } catch (Exception e) {
            logger.error("Error in update ciudad for id: {}", id, e);
            throw new RuntimeException("Error al actualizar ciudad", e);
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
            logger.error("Error in delete ciudad for id: {}", id, e);
            throw new RuntimeException("Error al eliminar ciudad", e);
        }
    }
}