package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.PublicacionEstastusHistorial;

import java.util.List;

public interface IPublicacionEstastusRepository {
    void guardarCambio(PublicacionEstastusHistorial historial);
    List<PublicacionEstastusHistorial> obtenerHistorialPorPublicacion(Long idPublicacion);
}
