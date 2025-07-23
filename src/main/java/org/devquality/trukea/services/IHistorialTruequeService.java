// IHistorialTruequeService.java
package org.devquality.trukea.services;

import org.devquality.trukea.web.dtos.historial.response.HistorialTruequeResponse;
import java.util.ArrayList;

public interface IHistorialTruequeService {
    ArrayList<HistorialTruequeResponse> findAll();
    HistorialTruequeResponse findById(Long id);
    ArrayList<HistorialTruequeResponse> findByUsuarioId(Long usuarioId);
    void crearDesdeTrueque(Long truequeId, Long prodO, Long prodD,
                           Long userO, Long userR);   // ← coincide con la impl
    org.devquality.trukea.persistance.entities.HistorialTrueque crearDirecto(org.devquality.trukea.persistance.entities.HistorialTrueque h);
    org.devquality.trukea.persistance.entities.HistorialTrueque actualizar(Long id, org.devquality.trukea.persistance.entities.HistorialTrueque h);
    boolean eliminar(Long id);
}
