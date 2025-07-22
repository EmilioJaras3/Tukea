package org.devquality.trukea.services;

import org.devquality.trukea.dtos.request.PublicacionEstastusRequestDTO;
import org.devquality.trukea.dtos.response.PublicacionEstastusResponseDTO;

import java.util.List;

public interface IPublicacionEstastusService {
    void registrarCambio(PublicacionEstastusRequestDTO dto);
    List<PublicacionEstastusResponseDTO> obtenerHistorial(Long idPublicacion);
}
