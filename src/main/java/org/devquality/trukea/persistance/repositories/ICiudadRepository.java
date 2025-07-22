package org.devquality.trukea.persistance.repositories;
import org.devquality.trukea.persistance.entities.Ciudad
        ;

import java.util.ArrayList;


public interface ICiudadRepository {
    ArrayList<Ciudad> findAll();
    Ciudad findById(Integer id);
    Ciudad save(Ciudad ciudad);
    boolean delete(Integer id);
}