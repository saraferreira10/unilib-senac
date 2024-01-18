package com.senac.unilib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.unilib.model.UsuarioModel;
import java.util.List;
import com.senac.unilib.model.enums.Role;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    List<UsuarioModel> findByRole(Role role);

    boolean existsByUsername(String username);

    UsuarioModel findByUsername(String username);
}
