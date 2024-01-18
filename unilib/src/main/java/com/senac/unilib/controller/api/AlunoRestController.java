package com.senac.unilib.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.unilib.model.AlunoModel;
import com.senac.unilib.services.AlunoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alunos")
public class AlunoRestController {

    @Autowired
    AlunoService alunoService;

    @PutMapping
    public ResponseEntity<Object> updateById(@RequestBody @Valid AlunoModel alunoModel) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(alunoService.updateById(alunoModel, alunoModel.getId()));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado!");
        }
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<Object> disable(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(alunoService.disable(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<Object> enable(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(alunoService.enable(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id) {
        try {
            alunoService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Aluno excluído!");
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
