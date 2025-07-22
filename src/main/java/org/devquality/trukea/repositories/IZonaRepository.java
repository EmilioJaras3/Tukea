package org.devquality.trukea.repositories;

import org.devquality.trukea.persistance.entities.ZonaSegura;
import org.devquality.trukea.persistance.entities.ZonaSeguraHorario;

import java.util.List;

public interface IZonaRepository {
    List<ZonaSegura> obtenerTodasLasZonas();
    List<ZonaSeguraHorario> obtenerHorariosPorZona(Long idZona);
    void crearZona(ZonaSegura zona);
}
