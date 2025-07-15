package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.persistance.repositories.ICategoriaRepository;

import java.sql.*;
import java.util.ArrayList;

public class CategoriaRepositoryImpl implements ICategoriaRepository {

    private final DatabaseConfig db;

    public CategoriaRepositoryImpl(DatabaseConfig db) {
        this.db = db;
    }

    @Override
    public ArrayList<Categoria> findAll() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria cat = new Categoria(
                        rs.getLong("id"),
                        rs.getString("nombre")
                );
                categorias.add(cat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    @Override
    public Categoria findById(Long id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        Categoria categoria = null;

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = new Categoria(
                            rs.getLong("id"),
                            rs.getString("nombre")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoria;
    }

    @Override
    public Categoria save(Categoria categoria) {
        String sql = "INSERT INTO categorias(nombre) VALUES(?)";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, categoria.getNombre());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoria.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoria;
    }
}
