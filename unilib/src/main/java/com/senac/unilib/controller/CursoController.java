package com.senac.unilib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.senac.unilib.model.CursoModel;
import com.senac.unilib.services.CursoService;
import com.senac.unilib.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastrar")
    public ModelAndView getCursos(HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/logout");
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());

        ModelAndView mv = new ModelAndView("cursos");
        mv.addObject("curso", new CursoModel());
        mv.addObject("nome", usuario.getNome());
        mv.addObject("role", usuario.getRole());
        return mv;
    }

    @PostMapping("/cadastrar")
    public String postMethodName(@ModelAttribute CursoModel curso) {
        cursoService.save(curso);
        return "redirect:/cursos/cadastrar";
    }

}
