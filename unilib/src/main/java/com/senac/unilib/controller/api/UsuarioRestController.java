package com.senac.unilib.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.unilib.exceptions.UserExistsException;
import com.senac.unilib.model.UsuarioModel;
import com.senac.unilib.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    UsuarioService usuarioService;

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Valid UsuarioModel usuarioModel) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(usuarioService.updateById(usuarioModel, usuarioModel.getId()));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (UserExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<Object> desativar(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.disable(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<Object> enable(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.enable(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

}
