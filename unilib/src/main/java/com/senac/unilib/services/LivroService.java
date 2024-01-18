package com.senac.unilib.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.senac.unilib.exceptions.IsbnExistsException;
import com.senac.unilib.model.LivroModel;
import com.senac.unilib.repository.LivroRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<LivroModel> getAll() {
        return livroRepository.findAll();
    }

    public LivroModel getById(Long id) {
        return livroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado!"));
    }

    public LivroModel save(@Valid LivroModel livroModel) {
        try {
            return livroRepository.save(livroModel);
        } catch (DataIntegrityViolationException exception) {
            if (livroRepository.existsByIsbn(livroModel.getIsbn())) {
                throw new IsbnExistsException("ISBN já existente!");
            }

            throw exception;
        }
    }

    public LivroModel updateById(@Valid LivroModel livroModel, Long id) {
        try {
            LivroModel updatedLivroModel = livroRepository.findById(id).get();

            BeanUtils.copyProperties(livroModel, updatedLivroModel);
            livroRepository.save(livroModel);

            return livroModel;
        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException("Livro não encontrado");
        } catch (DataIntegrityViolationException exception) {
            if (livroRepository.existsByIsbn(livroModel.getIsbn())) {
                throw new IsbnExistsException("ISBN já existente!");
            }

            throw exception;
        }
    }

    public LivroModel unavailable(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new EntityNotFoundException("Livro não encontrado!");
        }

        LivroModel livroModel = livroRepository.findById(id).get();
        livroModel.setExiste(false);
        return livroRepository.save(livroModel);
    }

    public List<LivroModel> getLivrosDisponiveis() {
        return livroRepository.findByExiste(true);
    }

    public boolean existsById(Long id) {
        return livroRepository.existsById(id);
    }
}
