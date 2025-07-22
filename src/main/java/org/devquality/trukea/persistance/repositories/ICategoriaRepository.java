package org.devquality.trukea.persistance.repositories;

import org.devquality.trukea.persistance.entities.Categoria;

import java.util.ArrayList;

public interface ICategoriaRepository {
    ArrayList<Categoria> findAll();
    Categoria save(Categoria categoria);
    Categoria findById(Long id);
}
