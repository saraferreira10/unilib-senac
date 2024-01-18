package com.senac.unilib.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_emprestimos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bibliotecario_id")
    @NotNull(message = "Bibliotecário não pode ser nulo!")
    private UsuarioModel bibliotecario;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    @NotNull(message = "Aluno não pode ser nulo!")
    private AlunoModel alunoModel;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    @NotNull(message = "Livro não pode ser nulo!")
    private LivroModel livroModel;

    @NotNull(message = "Data de empréstimo não pode ser nula!")
    private LocalDate dataEmprestimo;

    @NotNull(message = "Prazo não pode ser nulo!")
    private LocalDate prazo;

    private LocalDate dataDevolucao = null;

    private boolean devolvido = false;
}
