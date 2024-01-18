package com.senac.unilib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.unilib.model.CursoModel;
import com.senac.unilib.repository.CursoRepository;

import jakarta.validation.Valid;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<CursoModel> getAll() {
        return cursoRepository.findAll();
    }

    public CursoModel save(@Valid CursoModel cursoModel) {
        return cursoRepository.save(cursoModel);
    }
}
