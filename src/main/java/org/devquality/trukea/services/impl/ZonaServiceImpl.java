package org.devquality.trukea.services.impl;

import org.devquality.trukea.web.dtos.zona.request.CreateZonaRequest;
import org.devquality.trukea.web.dtos.zona.response.ZonaResponseDTO;
import org.devquality.trukea.web.dtos.zona.response.ZonaHorarioResponseDTO;
import org.devquality.trukea.persistance.entities.ZonaSegura;
import org.devquality.trukea.persistance.entities.ZonaSeguraHorario;
import org.devquality.trukea.repositories.IZonaRepository;
import org.devquality.trukea.services.IZonaService;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ZonaServiceImpl implements IZonaService {

    private final IZonaRepository repository;

    public ZonaServiceImpl(IZonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArrayList<ZonaResponseDTO> getAll() {
        return repository.obtenerTodasLasZonas().stream().map(z -> {
            ZonaResponseDTO dto = new ZonaResponseDTO();
            dto.idZona = z.getIdZona();
            dto.nombreZona = z.getNombreZona();
            dto.direccion = z.getDireccion();
            return dto;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<ZonaHorarioResponseDTO> getHorariosByZona(int zonaId) {
        return repository.obtenerHorariosPorZona((long) zonaId).stream().map(h -> {
            ZonaHorarioResponseDTO dto = new ZonaHorarioResponseDTO();
            dto.diaDeSemana = h.getDiaDeSemana();
            dto.horaApertura = h.getHoraApertura();
            dto.horaCierre = h.getHoraCierre();
            return dto;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ZonaResponseDTO create(CreateZonaRequest request) {
        ZonaSegura z = new ZonaSegura();
        z.setNombreZona(request.getNombreZona());
        z.setDireccion(request.getDireccion());
        repository.crearZona(z);

        // Retornar el DTO creado
        ZonaResponseDTO dto = new ZonaResponseDTO();
        dto.idZona = z.getIdZona();
        dto.nombreZona = z.getNombreZona();
        dto.direccion = z.getDireccion();
        return dto;
    }
}