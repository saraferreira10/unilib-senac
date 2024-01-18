package com.senac.unilib.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.unilib.model.AlunoModel;
import com.senac.unilib.repository.AlunoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<AlunoModel> getAll() {
        return alunoRepository.findAll();
    }

    public AlunoModel getById(Long id) {
        return alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não existe!"));
    }

    public AlunoModel save(@Valid AlunoModel alunoModel) {
        return alunoRepository.save(alunoModel);
    }

    public AlunoModel updateById(@Valid AlunoModel alunoModel, Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new EntityNotFoundException("Aluno não existe!");
        }

        AlunoModel updatedAlunoModel = alunoRepository.findById(id).get();

        BeanUtils.copyProperties(alunoModel, updatedAlunoModel);
        alunoRepository.save(alunoModel);

        return alunoModel;
    }

    public AlunoModel disable(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new EntityNotFoundException("Aluno não existe!");
        }

        AlunoModel alunoModel = alunoRepository.findById(id).get();
        alunoModel.setAtivo(false);
        return alunoRepository.save(alunoModel);
    }

    public AlunoModel enable(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new EntityNotFoundException("Aluno não existe!");
        }

        AlunoModel alunoModel = alunoRepository.findById(id).get();
        alunoModel.setAtivo(true);
        return alunoRepository.save(alunoModel);
    }

    public void delete(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new EntityNotFoundException("Aluno não existe!");
        }

        alunoRepository.deleteById(id);
    }

    public List<AlunoModel> getAlunosAtivos() {
        return alunoRepository.findByAtivo(true);
    }

    public boolean existsById(Long id) {
        return alunoRepository.existsById(id);
    }
}
