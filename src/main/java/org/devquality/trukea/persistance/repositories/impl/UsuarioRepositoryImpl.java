package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Usuario;
import org.devquality.trukea.persistance.repositories.IUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioRepositoryImpl implements IUsuarioRepository {

    private final DatabaseConfig databaseConfig;

    // ✅ SELECT queries CORRECTAS para tu BD
    private static final String SELECT_USER = "SELECT id,nombre,apellidoPaterno,apellidoMaterno,fechaNacimiento,correo,clave,idCiudad from usuarios ";
    private static final String SELECT_USER_BY_EMAIL = "SELECT id,nombre,apellidoPaterno,apellidoMaterno,fechaNacimiento,correo,clave,idCiudad from usuarios WHERE correo = ?";
    private static final String SELECT_USER_BY_ID = "SELECT id,nombre,apellidoPaterno,apellidoMaterno,fechaNacimiento,correo,clave,idCiudad from usuarios WHERE id = ?";

    // ✅ INSERT query CORRECTA (7 campos)
    private static final String INSERT_USER = "INSERT INTO usuarios (nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, correo, clave, idCiudad) VALUES (?, ?, ?, ?, ?, ?, ?)";

    // ✅ UPDATE query CORRECTA
    private static final String UPDATE_USER = "UPDATE usuarios SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaNacimiento = ?, correo = ?, clave = ?, idCiudad = ? WHERE id = ?";

    // ✅ DELETE query CORRECTA
    private static final String DELETE_USER = "DELETE FROM usuarios WHERE id = ?";

    // ✅ EXISTS queries CORRECTAS
    private static final String EXISTS_BY_EMAIL = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM usuarios WHERE id = ?";

    private static final Logger logger = LoggerFactory.getLogger(UsuarioRepositoryImpl.class);

    public UsuarioRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public Usuario findByEmail(String email) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUsuario(resultSet);
            }

        } catch (SQLException e) {
            logger.error("Error finding user by email: {}", email, e);
            throw new RuntimeException("Error al buscar usuario por email", e);
        }
        return null;
    }

    @Override
    public Usuario findById(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUsuario(resultSet);
            }

        } catch (SQLException e) {
            logger.error("Error finding user by id: {}", id, e);
            throw new RuntimeException("Error al buscar usuario por ID", e);
        }
        return null;
    }

    @Override
    public ArrayList<Usuario> findAllUsers() {
        try (Connection connection = databaseConfig.getConnection()) {
            ArrayList<Usuario> usuarios = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = mapResultSetToUsuario(resultSet);
                usuarios.add(usuario);
                logger.info("Usuario found: {}", usuario.getCorreo());
            }
            return usuarios;

        } catch (SQLException e) {
            logger.error("Error finding all users", e);
            throw new RuntimeException("Error al obtener todos los usuarios", e);
        }
    }

    @Override
    public Usuario createUser(Usuario usuario) {
        try (Connection connection = databaseConfig.getConnection()) {
            logger.debug("Creating user: {}", usuario.getCorreo());
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);

            // ✅ 7 parámetros para INSERT
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellidoPaterno());
            preparedStatement.setString(3, usuario.getApellidoMaterno());
            preparedStatement.setDate(4, usuario.getFechaNacimiento() != null ? Date.valueOf(usuario.getFechaNacimiento()) : null);
            preparedStatement.setString(5, usuario.getCorreo());
            preparedStatement.setString(6, usuario.getclave());
            preparedStatement.setObject(7, usuario.getIdCiudad());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getLong(1));
                    logger.info("Usuario created successfully with ID: {}", usuario.getId());
                    return usuario;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            logger.error("Error creating user: {}", usuario.getCorreo(), e);
            throw new RuntimeException("Error al crear usuario", e);
        }
    }

    @Override
    public Usuario updateUser(Usuario usuario) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);

            // ✅ 8 parámetros para UPDATE
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellidoPaterno());
            preparedStatement.setString(3, usuario.getApellidoMaterno());
            preparedStatement.setDate(4, usuario.getFechaNacimiento() != null ? Date.valueOf(usuario.getFechaNacimiento()) : null);
            preparedStatement.setString(5, usuario.getCorreo());
            preparedStatement.setString(6, usuario.getclave());
            preparedStatement.setObject(7, usuario.getIdCiudad());
            preparedStatement.setLong(8, usuario.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }

            logger.info("Usuario updated successfully with ID: {}", usuario.getId());
            return usuario;

        } catch (SQLException e) {
            logger.error("Error updating user: {}", usuario.getId(), e);
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            boolean deleted = affectedRows > 0;

            if (deleted) {
                logger.info("Usuario deleted successfully with ID: {}", id);
            } else {
                logger.warn("No user found to delete with ID: {}", id);
            }

            return deleted;

        } catch (SQLException e) {
            logger.error("Error deleting user: {}", id, e);
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(EXISTS_BY_EMAIL);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            logger.error("Error checking if user exists by email: {}", email, e);
            throw new RuntimeException("Error al verificar existencia de usuario por email", e);
        }
        return false;
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
            logger.error("Error checking if user exists by id: {}", id, e);
            throw new RuntimeException("Error al verificar existencia de usuario por ID", e);
        }
        return false;
    }

    // ✅ MAPEO CORRECTO para todos los campos de tu BD
    private Usuario mapResultSetToUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getLong("id"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
        usuario.setApellidoMaterno(resultSet.getString("apellidoMaterno"));

        Date fechaNacimiento = resultSet.getDate("fechaNacimiento");
        usuario.setFechaNacimiento(fechaNacimiento != null ? fechaNacimiento.toLocalDate() : null);

        usuario.setCorreo(resultSet.getString("correo"));
        usuario.setclave(resultSet.getString("clave"));

        Object idCiudad = resultSet.getObject("idCiudad");
        usuario.setIdCiudad(idCiudad != null ? ((Number) idCiudad).intValue() : null);

        return usuario;
    }
}