// Crear en: repositories/impl/CalidadRepositoryImpl.java
package org.devquality.trukea.persistance.repositories.impl;

import org.devquality.trukea.config.DatabaseConfig;
import org.devquality.trukea.persistance.entities.Calidad;
import org.devquality.trukea.persistance.repositories.ICalidadRepository;
import java.util.ArrayList;

public class CalidadRepositoryImpl implements ICalidadRepository {
    public CalidadRepositoryImpl(DatabaseConfig databaseConfig) {
    }

    // Implementación básica temporal
    @Override
    public ArrayList<Calidad> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Calidad findById(Integer id) {
        return null;
    }

    @Override
    public Calidad save(Calidad calidad) {
        return calidad;
    }

    @Override
    public boolean delete(Integer id) {
        return true;
    }
}