package com.senac.unilib.model;

import com.senac.unilib.model.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser nulo!")
    private String nome;

    @Column(unique = true)
    @NotBlank(message = "Username não pode ser nulo!")
    private String username;

    @NotBlank(message = "Senha não pode ser nula!")
    private String senha;

    @NotNull(message = "Role não pode ser nula!")
    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean ativo = true;
}
