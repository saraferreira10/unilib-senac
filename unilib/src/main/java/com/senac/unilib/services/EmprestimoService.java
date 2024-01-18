package com.senac.unilib.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.unilib.model.EmprestimoModel;
import com.senac.unilib.repository.EmprestimoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public List<EmprestimoModel> getAll() {
        return emprestimoRepository.findAll();
    }

    public EmprestimoModel getById(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado!"));
    }

    public EmprestimoModel save(@Valid EmprestimoModel emprestimoModel) {
        return emprestimoRepository.save(emprestimoModel);
    }

    public EmprestimoModel updateById(@Valid EmprestimoModel emprestimoModel, Long id) {
        if (!emprestimoRepository.existsById(id)) {
            throw new EntityNotFoundException("Empréstimo não encontrado!");
        }

        EmprestimoModel updatedEmprestimoModel = emprestimoRepository.findById(id).get();

        BeanUtils.copyProperties(emprestimoModel, updatedEmprestimoModel);
        emprestimoRepository.save(emprestimoModel);

        return emprestimoModel;
    }

    public void deleteById(Long id) {
        emprestimoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return emprestimoRepository.existsById(id);
    }
}
