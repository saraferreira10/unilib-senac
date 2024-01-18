package com.senac.unilib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senac.unilib.exceptions.IsbnExistsException;
import com.senac.unilib.model.LivroModel;
import com.senac.unilib.services.AutorService;
import com.senac.unilib.services.CategoriaService;
import com.senac.unilib.services.LivroService;
import com.senac.unilib.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/acervo")
public class AcervoController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public String listarObras(HttpServletRequest request, Model model) {
        HttpSession sessao = request.getSession();

        if (sessao.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());
        model.addAttribute("nome", usuario.getNome());
        model.addAttribute("role", usuario.getRole());
        model.addAttribute("livros", livroService.getLivrosDisponiveis());
        return "acervo";
    }

    @GetMapping("/cadastrar")
    public String cadastrarObra(HttpServletRequest request, Model model) {
        HttpSession sessao = request.getSession();

        if (sessao.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());
        model.addAttribute("nome", usuario.getNome());
        model.addAttribute("role", usuario.getRole());
        model.addAttribute("livro", new LivroModel());
        model.addAttribute("categorias", categoriaService.getAll());
        model.addAttribute("autores", autorService.getAll());
        return "cadastro-obra";
    }

    @GetMapping("/editar/{id}")
    public String editarObra(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        if (!livroService.existsById(id)) {
            return "redirect:/acervo/listar";
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());
        model.addAttribute("nome", usuario.getNome());
        model.addAttribute("role", usuario.getRole());
        model.addAttribute("livro", livroService.getById(id));
        model.addAttribute("categorias", categoriaService.getAll());
        model.addAttribute("autores", autorService.getAll());
        return "editar-obra";
    }

    @PostMapping("/cadastrar")
    public String cadastrarObra(@ModelAttribute LivroModel livro) {
        try {
            livroService.save(livro);
            return "redirect:/acervo/listar";
        } catch (Exception exception) {
            throw exception;
        }
    }

    @ExceptionHandler(IsbnExistsException.class)
    public String handleException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erro", true);
        return "redirect:/acervo/cadastrar";
    }

}
