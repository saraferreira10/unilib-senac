package com.senac.unilib.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.unilib.model.AutorModel;
import com.senac.unilib.repository.AutorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<AutorModel> getAll() {
        return autorRepository.findAll();
    }

    public AutorModel getById(Long id) {
        return autorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado!"));
    }

    public AutorModel save(@Valid AutorModel autorModel) {
        return autorRepository.save(autorModel);
    }

    public AutorModel updateById(Long id, @Valid AutorModel autorModel) {
        if (!autorRepository.existsById(id)) {
            throw new EntityNotFoundException("Autor não existe!");
        }

        AutorModel updatedAutorModel = autorRepository.findById(id).get();

        BeanUtils.copyProperties(autorModel, updatedAutorModel);
        autorRepository.save(autorModel);

        return autorModel;
    }

    public void delete(Long id) {
        if (!exists(id)) {
            throw new EntityNotFoundException("Autor não existe!");
        }

        autorRepository.deleteById(id);
    }

    public boolean exists(Long id) {
        return autorRepository.existsById(id);
    }
}
