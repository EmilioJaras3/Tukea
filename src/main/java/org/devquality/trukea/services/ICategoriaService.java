package org.devquality.trukea.services;

import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.web.dtos.categorias.requests.CreateCategoriaRequest;
import org.devquality.trukea.web.dtos.categorias.responses.CreateCategoriaResponse;

import java.util.ArrayList;

public interface ICategoriaService {
    ArrayList<CreateCategoriaResponse> findAll();
    CreateCategoriaResponse createCategoria(CreateCategoriaRequest request);
    Categoria findById(Long id);
}