package com.senac.unilib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.senac.unilib.model.AutorModel;
import com.senac.unilib.services.AutorService;
import com.senac.unilib.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastrar")
    public ModelAndView getCadastrar(HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView mv = new ModelAndView("cadastro-autor");
        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());
        mv.addObject("nome", usuario.getNome());
        mv.addObject("role", usuario.getRole());
        mv.addObject("autor", new AutorModel());
        return mv;
    }

    @PostMapping("/cadastrar")
    public String postMethodName(@ModelAttribute AutorModel autor) {
        autorService.save(autor);
        return "redirect:/autores/cadastrar";
    }

}
