/*package org.devquality.trukea.services.impl;


import org.devquality.trukea.persistance.repositories.impl.EstadisticasRepositoryImpl;
import org.devquality.trukea.services.IEstadisticasService;
import org.devquality.trukea.web.dtos.usuarios.response.EstadisticasUsuarioResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EstadisticasServiceImpl implements org.devquality.trukea.services.impl. IEstadisticasService {
    private final EstadisticasRepositoryImpl estadisticasRepository;
    private static final Logger logger = LoggerFactory.getLogger(EstadisticasServiceImpl.class);

    public EstadisticasServiceImpl(EstadisticasRepositoryImpl estadisticasRepository) {
        this.estadisticasRepository = estadisticasRepository;
    }

    @Override
    public EstadisticasUsuarioResponse getEstadisticasUsuario(Long usuarioId) {
        try {
            if (usuarioId == null || usuarioId <= 0) {
                throw new IllegalArgumentException("ID de usuario inválido");
            }

            EstadisticasUsuarioResponse stats = this.estadisticasRepository.getEstadisticasUsuario(usuarioId);

            if (stats == null) {
                throw new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId);
            }

            return stats;
        } catch (IllegalArgumentException e) {
            logger.warn("Error de validación en getEstadisticasUsuario: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error en getEstadisticasUsuario para usuario: {}", usuarioId, e);
            throw new RuntimeException("Error al obtener estadísticas del usuario", e);
        }
    }
}
*/


