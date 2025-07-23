package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Intercambio;
import org.devquality.trukea.persistance.repositories.IIntercambioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class IntercambioRepositoryImpl implements IIntercambioRepository {

    private final DatabaseConfig databaseConfig;

    // SELECT queries
    private static final String SELECT_ALL_INTERCAMBIOS = "SELECT id, historial_id, publicacion_1_id, publicacion_2_id FROM intercambios";
    private static final String SELECT_INTERCAMBIO_BY_ID = "SELECT id, historial_id, publicacion_1_id, publicacion_2_id FROM intercambios WHERE id = ?";
    private static final String SELECT_INTERCAMBIOS_BY_PUBLICACION1 = "SELECT id, historial_id, publicacion_1_id, publicacion_2_id FROM intercambios WHERE publicacion_1_id = ?";
    private static final String SELECT_INTERCAMBIOS_BY_PUBLICACION2 = "SELECT id, historial_id, publicacion_1_id, publicacion_2_id FROM intercambios WHERE publicacion_2_id = ?";

    // INSERT query
    private static final String INSERT_INTERCAMBIO = "INSERT INTO intercambios (historial_id, publicacion_1_id, publicacion_2_id) VALUES (?, ?, ?)";
    // UPDATE query
    private static final String UPDATE_INTERCAMBIO = "UPDATE intercambios SET historial_id = ?, publicacion_1_id = ?, publicacion_2_id = ? WHERE id = ?";
    // DELETE query
    private static final String DELETE_INTERCAMBIO = "DELETE FROM intercambios WHERE id = ?";
    // EXISTS query
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM intercambios WHERE id = ?";

    private static final Logger logger = LoggerFactory.getLogger(IntercambioRepositoryImpl.class);

    public IntercambioRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public ArrayList<Intercambio> findAllIntercambios() {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Intercambio> intercambios = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_INTERCAMBIOS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Intercambio intercambio = mapResultSetToIntercambio(resultSet);
                intercambios.add(intercambio);
            }
            return intercambios;
        } catch (SQLException e) {
            logger.error("Error finding all intercambios", e);
            throw new RuntimeException("Error al obtener todos los intercambios", e);
        }
    }

    @Override
    public Intercambio findById(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INTERCAMBIO_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToIntercambio(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error finding intercambio by id: {}", id, e);
            throw new RuntimeException("Error al buscar intercambio por ID", e);
        }
        return null;
    }

    @Override
    public ArrayList<Intercambio> findByPublicacion1Id(Long publicacionId) {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Intercambio> intercambios = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INTERCAMBIOS_BY_PUBLICACION1);
            preparedStatement.setLong(1, publicacionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Intercambio intercambio = mapResultSetToIntercambio(resultSet);
                intercambios.add(intercambio);
            }
            return intercambios;
        } catch (SQLException e) {
            logger.error("Error finding intercambios by publicacion_1_id: {}", publicacionId, e);
            throw new RuntimeException("Error al buscar intercambios por publicacion_1_id", e);
        }
    }

    @Override
    public ArrayList<Intercambio> findByPublicacion2Id(Long publicacionId) {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Intercambio> intercambios = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INTERCAMBIOS_BY_PUBLICACION2);
            preparedStatement.setLong(1, publicacionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Intercambio intercambio = mapResultSetToIntercambio(resultSet);
                intercambios.add(intercambio);
            }
            return intercambios;
        } catch (SQLException e) {
            logger.error("Error finding intercambios by publicacion_2_id: {}", publicacionId, e);
            throw new RuntimeException("Error al buscar intercambios por publicacion_2_id", e);
        }
    }

    @Override
    public Intercambio createIntercambio(Intercambio intercambio) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTERCAMBIO, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, intercambio.getHistorialId());
            preparedStatement.setLong(2, intercambio.getPublicacion1Id());
            preparedStatement.setLong(3, intercambio.getPublicacion2Id());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating intercambio failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    intercambio.setId(generatedKeys.getLong(1));
                    return intercambio;
                } else {
                    throw new SQLException("Creating intercambio failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating intercambio", e);
            throw new RuntimeException("Error al crear intercambio", e);
        }
    }

    @Override
    public Intercambio updateIntercambio(Intercambio intercambio) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INTERCAMBIO);
            preparedStatement.setLong(1, intercambio.getHistorialId());
            preparedStatement.setLong(2, intercambio.getPublicacion1Id());
            preparedStatement.setLong(3, intercambio.getPublicacion2Id());
            preparedStatement.setLong(4, intercambio.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating intercambio failed, no rows affected.");
            }
            return intercambio;
        } catch (SQLException e) {
            logger.error("Error updating intercambio: {}", intercambio.getId(), e);
            throw new RuntimeException("Error al actualizar intercambio", e);
        }
    }

    @Override
    public boolean deleteIntercambio(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INTERCAMBIO);
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Error deleting intercambio: {}", id, e);
            throw new RuntimeException("Error al eliminar intercambio", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(EXISTS_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error checking if intercambio exists by id: {}", id, e);
            throw new RuntimeException("Error al verificar existencia de intercambio por id", e);
        }
        return false;
    }

    private Intercambio mapResultSetToIntercambio(ResultSet resultSet) throws SQLException {
        Intercambio intercambio = new Intercambio();
        intercambio.setId(resultSet.getLong("id"));
        intercambio.setHistorialId(resultSet.getLong("historial_id"));
        intercambio.setPublicacion1Id(resultSet.getLong("publicacion_1_id"));
        intercambio.setPublicacion2Id(resultSet.getLong("publicacion_2_id"));
        return intercambio;
    }
}