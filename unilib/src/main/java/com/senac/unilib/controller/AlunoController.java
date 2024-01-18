package com.senac.unilib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.senac.unilib.model.AlunoModel;
import com.senac.unilib.services.AlunoService;
import com.senac.unilib.services.CursoService;
import com.senac.unilib.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ModelAndView listar(HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/logout");
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());

        ModelAndView mv = new ModelAndView("alunos");
        mv.addObject("alunos", alunoService.getAll());
        mv.addObject("role", usuario.getRole());
        mv.addObject("nome", usuario.getNome());
        return mv;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/logout");
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());
        ModelAndView mv = new ModelAndView("cadastro-aluno");
        mv.addObject("aluno", new AlunoModel());
        mv.addObject("cursos", cursoService.getAll());
        mv.addObject("role", usuario.getRole());
        mv.addObject("nome", usuario.getNome());
        return mv;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@ModelAttribute AlunoModel aluno) {
        alunoService.save(aluno);
        return "redirect:/alunos/cadastrar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return "redirect:/logout";
        }

        if (!alunoService.existsById(id)) {
            return "redirect:/alunos/listar";
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());

        model.addAttribute("aluno", alunoService.getById(id));
        model.addAttribute("cursos", cursoService.getAll());
        model.addAttribute("nome", usuario.getNome());
        model.addAttribute("role", usuario.getRole());
        return "editar-aluno";
    }

}
