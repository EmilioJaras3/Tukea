package org.devquality.trukea.services.impl;

import org.devquality.trukea.persistance.entities.Categoria;
import org.devquality.trukea.persistance.repositories.ICategoriaRepository;
import org.devquality.trukea.services.ICategoriaService;
import org.devquality.trukea.web.dtos.categorias.request.CreateCategoriaRequest;
import org.devquality.trukea.web.dtos.categorias.response.CreateCategoriaResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CategoriaServiceImpl implements ICategoriaService {

    private final ICategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ArrayList<CreateCategoriaResponse> findAll() {
        ArrayList<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(cat -> new CreateCategoriaResponse(cat.getId(), cat.getNombre()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public CreateCategoriaResponse createCategoria(CreateCategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());

        Categoria guardada = categoriaRepository.save(categoria);

        return new CreateCategoriaResponse(guardada.getId(), guardada.getNombre());
    }
}
