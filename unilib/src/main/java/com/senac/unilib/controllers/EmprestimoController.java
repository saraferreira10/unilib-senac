package com.senac.unilib.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @GetMapping("/listar")
    public ModelAndView listar() {
        return new ModelAndView("emprestimos");
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        return new ModelAndView("cadastro-emprestimo");
    }

    @GetMapping("/editar")
    public ModelAndView editar() {
        return new ModelAndView("editar-emprestimo");
    }
}
