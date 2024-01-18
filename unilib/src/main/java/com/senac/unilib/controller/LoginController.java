package com.senac.unilib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.senac.unilib.model.UsuarioModel;
import com.senac.unilib.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public String getLogin(HttpServletRequest request, Model model) {
        HttpSession sessao = request.getSession();

        if (sessao != null && sessao.getAttribute("usuario") != null) {
            return "redirect:/acervo/listar";
        }

        model.addAttribute("usuarioModel", new UsuarioModel());
        return "login";
    }

    @PostMapping
    public String postLogin(HttpServletRequest request, @ModelAttribute UsuarioModel usuarioModel, Model model) {
        HttpSession sessao = request.getSession();
        UsuarioModel usuarioExiste = usuarioService.getByUsername(usuarioModel.getUsername());

        if (usuarioExiste == null || !usuarioExiste.isAtivo()) {
            return "redirect:/logout";
        }

        if (sessao != null && sessao.getAttribute("usuario") != null) {
            return "redirect:/acervo/listar";
        }

        if (sessao != null && usuarioExiste != null && usuarioModel.getSenha().equals(usuarioExiste.getSenha())) {
            sessao.setAttribute("usuario", usuarioExiste.getUsername());
            return "redirect:/acervo/listar";
        }

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession sessao = request.getSession();

        if (sessao != null) {
            sessao.removeAttribute("usuario");
        }

        return "redirect:/";
    }
}
