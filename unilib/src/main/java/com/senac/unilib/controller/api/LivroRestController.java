package com.senac.unilib.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.unilib.exceptions.IsbnExistsException;
import com.senac.unilib.model.LivroModel;
import com.senac.unilib.services.LivroService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livros")
public class LivroRestController {

    @Autowired
    LivroService livroService;

    @PutMapping
    public ResponseEntity<Object> updateById(@RequestBody @Valid LivroModel livroModel) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(livroService.updateById(livroModel, livroModel.getId()));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (IsbnExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<Object> unavailable(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(livroService.unavailable(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

}
