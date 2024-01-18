package com.senac.unilib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.unilib.model.AutorModel;

@Repository
public interface AutorRepository extends JpaRepository<AutorModel, Long> {
}
