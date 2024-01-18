package com.senac.unilib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.unilib.model.CursoModel;

@Repository
public interface CursoRepository extends JpaRepository<CursoModel, Long> {
}
