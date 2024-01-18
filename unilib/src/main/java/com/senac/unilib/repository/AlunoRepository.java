package com.senac.unilib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.unilib.model.AlunoModel;
import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoModel, Long> {
    List<AlunoModel> findByAtivo(boolean ativo);
}
