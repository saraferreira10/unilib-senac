package com.senac.unilib.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senac.unilib.validations.ISBN;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_livros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ISBN
    @Column(unique = true)
    private String isbn;

    @NotBlank(message = "Título não pode ser nulo!")
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    @NotNull(message = "Autor não pode ser nulo!")
    private AutorModel autor;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @NotNull(message = "Categoria não pode ser nula!")
    private CategoriaModel categoria;

    //verifica se o livro ainda está presente no acervo.
    private boolean existe = true;
    private boolean alugado = false;

    @JsonIgnore
    @OneToMany(mappedBy = "livroModel", cascade = CascadeType.REMOVE)
    private List<EmprestimoModel> emprestimos;

}
