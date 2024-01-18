package com.senac.unilib.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping("/listar")
    public ModelAndView listar() {
        return new ModelAndView("usuarios");
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        return new ModelAndView("cadastro-usuario");
    }

    @GetMapping("/editar")
    public ModelAndView editar() {
        return new ModelAndView("editar-usuario");
    }

}
