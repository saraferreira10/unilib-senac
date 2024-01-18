package com.senac.unilib.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.unilib.exceptions.UserExistsException;
import com.senac.unilib.model.UsuarioModel;
import com.senac.unilib.model.enums.Role;
import com.senac.unilib.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioModel> getAll() {
        return usuarioRepository.findAll();
    }

    public UsuarioModel getById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
    }

    public UsuarioModel getByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public UsuarioModel save(@Valid UsuarioModel usuarioModel) {
        if (usuarioRepository.existsByUsername(usuarioModel.getUsername())
                && usuarioRepository.findByUsername(usuarioModel.getUsername()).getId() != usuarioModel.getId()) {
            throw new UserExistsException("Username inválido!");
        }

        return usuarioRepository.save(usuarioModel);
    }

    public UsuarioModel updateById(@Valid UsuarioModel usuarioModel, Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        } else if (usuarioRepository.existsByUsername(usuarioModel.getUsername())
                && usuarioRepository.findByUsername(usuarioModel.getUsername()).getId() != usuarioModel.getId()) {
            throw new UserExistsException("Username inválido!");
        }

        UsuarioModel updatedUsuarioModel = usuarioRepository.findById(id).get();

        BeanUtils.copyProperties(usuarioModel, updatedUsuarioModel);
        usuarioRepository.save(usuarioModel);

        return usuarioModel;
    }

    public UsuarioModel disable(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        }

        UsuarioModel usuarioModel = usuarioRepository.findById(id).get();
        usuarioModel.setAtivo(false);
        return usuarioRepository.save(usuarioModel);
    }

    public UsuarioModel enable(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        }

        UsuarioModel usuarioModel = usuarioRepository.findById(id).get();
        usuarioModel.setAtivo(true);
        return usuarioRepository.save(usuarioModel);
    }

    public boolean existsById(Long id) {
        return usuarioRepository.existsById(id);
    }

    public List<UsuarioModel> getBibliotecariosAtivos() {
        List<UsuarioModel> users = usuarioRepository.findByRole(Role.BIBLIOTECARIO);
        List<UsuarioModel> activeUsers = new ArrayList<>();

        for (UsuarioModel user : users) {
            if (user.isAtivo()) {
                activeUsers.add(user);
            }
        }

        return activeUsers;
    }
}
