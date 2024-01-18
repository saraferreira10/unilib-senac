package com.senac.unilib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.senac.unilib.model.CategoriaModel;
import com.senac.unilib.services.CategoriaService;
import com.senac.unilib.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastrar")
    public ModelAndView getCadastro(HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView mv = new ModelAndView("cadastro-categoria");
        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());
        mv.addObject("usuario", usuario);
        mv.addObject("nome", usuario.getNome());
        mv.addObject("role", usuario.getRole());
        mv.addObject("categoria", new CategoriaModel());
        return mv;
    }

    @PostMapping("/cadastrar")
    public String postCadastro(@ModelAttribute CategoriaModel categoria) {
        categoriaService.save(categoria);
        return "redirect:/categorias/cadastrar";
    }

}
