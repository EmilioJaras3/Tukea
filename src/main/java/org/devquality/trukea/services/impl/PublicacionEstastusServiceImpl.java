package org.devquality.trukea.services.impl;

import org.devquality.trukea.dtos.request.PublicacionEstastusRequestDTO;
import org.devquality.trukea.dtos.response.PublicacionEstastusResponseDTO;
import org.devquality.trukea.persistance.entities.PublicacionEstastusHistorial;
import org.devquality.trukea.persistance.repositories.IPublicacionEstastusRepository;
import org.devquality.trukea.services.IPublicacionEstastusService;

import java.util.List;
import java.util.stream.Collectors;

public class PublicacionEstastusServiceImpl implements IPublicacionEstastusService {

    private final IPublicacionEstastusRepository repository;

    public PublicacionEstastusServiceImpl(IPublicacionEstastusRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrarCambio(PublicacionEstastusRequestDTO dto) {
        PublicacionEstastusHistorial historial = new PublicacionEstastusHistorial();
        historial.setIdPublicacion(dto.idPublicacion);
        historial.setStatus(dto.status);
        historial.setModificadoPor(dto.modificadoPor);
        repository.guardarCambio(historial);
    }

    @Override
    public List<PublicacionEstastusResponseDTO> obtenerHistorial(Long idPublicacion) {
        return repository.obtenerHistorialPorPublicacion(idPublicacion)
                .stream()
                .map(h -> {
                    PublicacionEstastusResponseDTO dto = new PublicacionEstastusResponseDTO();
                    dto.status = h.getStatus();
                    dto.fechaModificacion = h.getFechaModificacion();
                    dto.modificadoPorNombre = "Usuario ID: " + h.getModificadoPor(); // opcional: mostrar nombre real
                    return dto;
                }).collect(Collectors.toList());
    }
}
