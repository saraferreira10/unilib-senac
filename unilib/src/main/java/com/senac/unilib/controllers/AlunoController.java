package com.senac.unilib.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @GetMapping("/listar")
    public ModelAndView listar() {
        return new ModelAndView("alunos");
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        return new ModelAndView("cadastro-aluno");
    }

    @GetMapping("/editar")
    public ModelAndView editar() {
        return new ModelAndView("editar-aluno");
    }

}
