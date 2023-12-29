package com.senac.unilib.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/acervo")
public class AcervoController {

    @GetMapping("/listar")
    public ModelAndView listarObras() {
        ModelAndView mv = new ModelAndView("acervo");
        return mv;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrarObra() {
        return new ModelAndView("cadastro-obra");
    }

    @GetMapping("/editar")
    public ModelAndView editarObra() {
        return new ModelAndView("editar-obra");
    }

}
