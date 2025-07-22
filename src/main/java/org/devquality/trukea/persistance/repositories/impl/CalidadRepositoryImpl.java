package org.devquality.trukea.persistance.repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Calidad;
import org.devquality.trukea.persistance.repositories.ICalidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalidadRepositoryImpl implements ICalidadRepository {
    private final DatabaseConfig databaseConfig;
    private static final Logger logger = LoggerFactory.getLogger(CalidadRepositoryImpl.class);

    public CalidadRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public ArrayList<Calidad> findAll() {
        String sql = "SELECT id_calidad, nivel_calidad, descripcion_calidad FROM calidad ORDER BY id_calidad";
        ArrayList<Calidad> calidades = new ArrayList<>();

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                Calidad calidad = new Calidad();
                calidad.setIdCalidad(rs.getInt("id_calidad"));
                calidad.setNivelCalidad(rs.getString("nivel_calidad"));
                calidad.setDescripcionCalidad(rs.getString("descripcion_calidad"));
                calidades.add(calidad);
            }
        } catch (SQLException e) {
            logger.error("Error obteniendo calidades", e);
            throw new RuntimeException("Error obteniendo calidades", e);
        }

        return calidades;
    }

    @Override
    public Calidad findById(Integer id) {
        String sql = "SELECT id_calidad, nivel_calidad, descripcion_calidad FROM calidad WHERE id_calidad = ?";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Calidad calidad = new Calidad();
                calidad.setIdCalidad(rs.getInt("id_calidad"));
                calidad.setNivelCalidad(rs.getString("nivel_calidad"));
                calidad.setDescripcionCalidad(rs.getString("descripcion_calidad"));
                return calidad;
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error obteniendo calidad por ID: {}", id, e);
            throw new RuntimeException("Error obteniendo calidad por ID", e);
        }
    }

    @Override
    public Calidad save(Calidad calidad) {
        if (calidad.getIdCalidad() == null) {
            // INSERT
            String sql = "INSERT INTO calidad (nivel_calidad, descripcion_calidad) VALUES (?, ?)";
            try (Connection conn = databaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, calidad.getNivelCalidad());
                stmt.setString(2, calidad.getDescripcionCalidad());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        calidad.setIdCalidad(generatedKeys.getInt(1));
                    }
                }
                return calidad;
            } catch (SQLException e) {
                logger.error("Error creando calidad", e);
                throw new RuntimeException("Error creando calidad", e);
            }
        } else {
            // UPDATE
            String sql = "UPDATE calidad SET nivel_calidad = ?, descripcion_calidad = ? WHERE id_calidad = ?";
            try (Connection conn = databaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, calidad.getNivelCalidad());
                stmt.setString(2, calidad.getDescripcionCalidad());
                stmt.setInt(3, calidad.getIdCalidad());

                stmt.executeUpdate();
                return calidad;
            } catch (SQLException e) {
                logger.error("Error actualizando calidad", e);
                throw new RuntimeException("Error actualizando calidad", e);
            }
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM calidad WHERE id_calidad = ?";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Error eliminando calidad: {}", id, e);
            throw new RuntimeException("Error eliminando calidad", e);
        }
    }
}
