package org.devquality.trukea.persistance.repositories.impl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Usuario;
import org.devquality.trukea.persistance.repositories.IUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//esta es una prueba de commit
public class UsuarioRepositoryImpl implements IUsuarioRepository {
    private final DatabaseConfig databaseConfig;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioRepositoryImpl.class);

    // CONSULTAS CORREGIDAS PARA TU BD
    private static final String SELECT_USER_COMPLETE = """
        SELECT id, nombre, correo, contraseña, ciudad, edad, foto_perfil, descripcion
        FROM usuarios
        """;
    private static final String SELECT_USER_BY_ID = SELECT_USER_COMPLETE + " WHERE id = ?";
    private static final String SELECT_USER_BY_EMAIL = SELECT_USER_COMPLETE + " WHERE correo = ?";
    private static final String INSERT_USER = """
        INSERT INTO usuarios (nombre, correo, contraseña, ciudad, edad, foto_perfil, descripcion)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
    private static final String UPDATE_USER = """
        UPDATE usuarios SET nombre = ?, correo = ?, contraseña = ?, ciudad = ?, edad = ?, foto_perfil = ?, descripcion = ?
        WHERE id = ?
        """;
    private static final String DELETE_USER = "DELETE FROM usuarios WHERE id = ?";
    private static final String EXISTS_BY_EMAIL = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM usuarios WHERE id = ?";

    public UsuarioRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public ArrayList<Usuario> findAllUsers() {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_COMPLETE);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ArrayList<Usuario> usuarios = new ArrayList<>();
            while(resultSet.next()) {
                Usuario usuario = this.mapResultSetToUsuarioCompleto(resultSet);
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException e) {
            logger.error("Error finding all users", e);
            throw new RuntimeException("Error al obtener todos los usuarios", e);
        }
    }

    @Override
    public Usuario findById(Long id) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return this.mapResultSetToUsuarioCompleto(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error finding user by id: {}", id, e);
            throw new RuntimeException("Error al buscar usuario por ID", e);
        }
    }

    @Override
    public Usuario findByEmail(String email) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return this.mapResultSetToUsuarioCompleto(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error finding user by email: {}", email, e);
            throw new RuntimeException("Error al buscar usuario por email", e);
        }
    }

    @Override
    public Usuario createUser(Usuario usuario) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCorreo());
            preparedStatement.setString(3, usuario.getContraseña());
            preparedStatement.setString(4, usuario.getCiudad());
            if (usuario.getEdad() != null) {
                preparedStatement.setInt(5, usuario.getEdad());
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(6, usuario.getFotoPerfil());
            preparedStatement.setString(7, usuario.getDescripcion());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getLong(1));
                }
            }

            logger.info("Usuario created successfully with ID: {}", usuario.getId());
            return usuario;
        } catch (SQLException e) {
            logger.error("Error creating user: {}", usuario.getCorreo(), e);
            throw new RuntimeException("Error al crear usuario", e);
        }
    }

    @Override
    public Usuario updateUser(Usuario usuario) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCorreo());
            preparedStatement.setString(3, usuario.getContraseña());
            preparedStatement.setString(4, usuario.getCiudad());
            if (usuario.getEdad() != null) {
                preparedStatement.setInt(5, usuario.getEdad());
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(6, usuario.getFotoPerfil());
            preparedStatement.setString(7, usuario.getDescripcion());
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

    //HOLA ESTA ES UNA PRUEBA PARA HACER UN PUSH

    @Override
    public boolean deleteUser(Long id) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {

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
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXISTS_BY_EMAIL)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            logger.error("Error checking if user exists by email: {}", email, e);
            throw new RuntimeException("Error al verificar existencia de usuario por email", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection connection = this.databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXISTS_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            logger.error("Error checking if user exists by id: {}", id, e);
            throw new RuntimeException("Error al verificar existencia de usuario por ID", e);
        }
    }

    // ✅ MAPEO COMPLETO CON TODOS LOS CAMPOS
    private Usuario mapResultSetToUsuarioCompleto(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getLong("id"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setCorreo(resultSet.getString("correo"));
        usuario.setContraseña(resultSet.getString("contraseña"));
        usuario.setCiudad(resultSet.getString("ciudad"));
        int edad = resultSet.getInt("edad");
        if (!resultSet.wasNull()) {
            usuario.setEdad(edad);
        }
        usuario.setFotoPerfil(resultSet.getString("foto_perfil"));
        usuario.setDescripcion(resultSet.getString("descripcion"));
        return usuario;
    }
}
