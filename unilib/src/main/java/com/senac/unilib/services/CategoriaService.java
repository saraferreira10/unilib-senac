package com.senac.unilib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.unilib.model.CategoriaModel;
import com.senac.unilib.repository.CategoriaRepository;

import jakarta.validation.Valid;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaModel> getAll() {
        return categoriaRepository.findAll();
    }

    public CategoriaModel save(@Valid CategoriaModel categoriaModel) {
        return categoriaRepository.save(categoriaModel);
    }
}
