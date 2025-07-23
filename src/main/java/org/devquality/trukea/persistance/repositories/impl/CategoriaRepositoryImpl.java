package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.persistance.repositories.ICategoriaRepository;

import java.sql.*;
import java.util.ArrayList;

public class CategoriaRepositoryImpl implements ICategoriaRepository {

    private final DatabaseConfig databaseConfig;

    public CategoriaRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public ArrayList<Categoria> findAll() {
        ArrayList<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM categorias";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getLong("id"));
                c.setNombre(rs.getString("nombre"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Categoria save(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, categoria.getNombre());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                categoria.setId(keys.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoria;
    }

    @Override
    public Categoria findById(Long id) {
        String sql = "SELECT id, nombre FROM categorias WHERE id = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getLong("id"));
                c.setNombre(rs.getString("nombre"));
                return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
