package com.senac.unilib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.senac.unilib.model.LivroModel;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, Long> {

    boolean existsByIsbn(String isbn);

    List<LivroModel> findByExiste(boolean existe);
}
