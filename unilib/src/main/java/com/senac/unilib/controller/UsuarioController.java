package com.senac.unilib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senac.unilib.exceptions.UserExistsException;
import com.senac.unilib.model.UsuarioModel;
import com.senac.unilib.model.enums.Role;
import com.senac.unilib.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ModelAndView listar(HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/logout");
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());

        if (usuario.getRole().equals(Role.BIBLIOTECARIO)) {
            return new ModelAndView("redirect:/logout");
        }
        
        ModelAndView mv = new ModelAndView("usuarios");
        mv.addObject("nome", usuario.getNome());
        mv.addObject("role", usuario.getRole());
        mv.addObject("usuarios", usuarioService.getAll());
        return mv;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar(HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/logout");
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());

        if (usuario.getRole().equals(Role.BIBLIOTECARIO)) {
            return new ModelAndView("redirect:/logout");
        }

        ModelAndView mv = new ModelAndView("cadastro-usuario");
        mv.addObject("nome", usuario.getNome());
        mv.addObject("role", usuario.getRole());
        mv.addObject("usuario", new UsuarioModel());
        return mv;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@ModelAttribute UsuarioModel usuario) {
        usuarioService.save(usuario);
        return "redirect:/usuarios/cadastrar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return "redirect:/logout";
        }

        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());

        if (usuario.getRole().equals(Role.BIBLIOTECARIO)) {
            return "redirect:/logout";
        }

        if (!usuarioService.existsById(id)) {
            return "redirect:/usuarios/listar";
        }

        model.addAttribute("usuario", usuarioService.getById(id));
        model.addAttribute("usuarioLogado", sessao.getAttribute("usuario"));
        model.addAttribute("username", usuarioService.getById(id).getUsername());
        model.addAttribute("nome", usuario.getNome());
        model.addAttribute("role", usuario.getRole());
        return "editar-usuario";
    }

    @ExceptionHandler(UserExistsException.class)
    public String handleException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erro", true);
        return "redirect:/usuarios/cadastrar";
    }
}
