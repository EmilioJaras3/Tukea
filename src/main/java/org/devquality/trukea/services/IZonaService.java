package org.devquality.trukea.services;

import org.devquality.trukea.web.dtos.zona.request.CreateZonaRequest;
import org.devquality.trukea.web.dtos.zona.response.ZonaResponseDTO;
import org.devquality.trukea.web.dtos.zona.response.ZonaHorarioResponseDTO;

import java.util.ArrayList;

public interface IZonaService {
    ArrayList<ZonaResponseDTO> getAll();
    ZonaResponseDTO create(CreateZonaRequest request);
    ArrayList<ZonaHorarioResponseDTO> getHorariosByZona(int zonaId);
}
