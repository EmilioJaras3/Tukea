package org.devquality.trukea.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.PublicacionEstastusHistorial;
import org.devquality.trukea.persistance.repositories.IPublicacionEstastusRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicacionEstastusRepositoryImpl implements IPublicacionEstastusRepository {

    @Override
    public void guardarCambio(PublicacionEstastusHistorial historial) {
        String sql = "INSERT INTO publicacion_estatus_historial (id_publicacion, status, fecha_modificacion, modificado_por) VALUES (?, ?, NOW(), ?)";
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, historial.getIdPublicacion());
            stmt.setString(2, historial.getStatus());
            stmt.setLong(3, historial.getModificadoPor());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PublicacionEstastusHistorial> obtenerHistorialPorPublicacion(Long idPublicacion) {
        List<PublicacionEstastusHistorial> lista = new ArrayList<>();
        String sql = "SELECT * FROM publicacion_estatus_historial WHERE id_publicacion = ? ORDER BY fecha_modificacion DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idPublicacion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PublicacionEstastusHistorial h = new PublicacionEstastusHistorial();
                h.setId(rs.getLong("id_historial"));
                h.setIdPublicacion(rs.getLong("id_publicacion"));
                h.setStatus(rs.getString("status"));
                h.setFechaModificacion(rs.getTimestamp("fecha_modificacion").toLocalDateTime());
                h.setModificadoPor(rs.getLong("modificado_por"));
                lista.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
