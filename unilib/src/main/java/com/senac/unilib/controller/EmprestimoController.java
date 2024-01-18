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

import com.senac.unilib.model.EmprestimoModel;
import com.senac.unilib.model.LivroModel;
import com.senac.unilib.services.AlunoService;
import com.senac.unilib.services.EmprestimoService;
import com.senac.unilib.services.LivroService;
import com.senac.unilib.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {
    
    @Autowired
    EmprestimoService emprestimoService;
    
    @Autowired
    AlunoService alunoService;
    
    @Autowired
    LivroService livroService;
    
    @Autowired
    UsuarioService usuarioService;
    
    @GetMapping("/listar")
    public ModelAndView listar(HttpServletRequest request) {
        HttpSession sessao = request.getSession();
        
        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/");
        }
        
        ModelAndView mv = new ModelAndView("emprestimos");
        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());
        mv.addObject("nome", usuario.getNome());
        mv.addObject("username", usuario.getUsername());
        mv.addObject("role", usuario.getRole());
        mv.addObject("emprestimos", emprestimoService.getAll());
        return mv;
    }
    
    @GetMapping("/cadastrar/{id}")
    public String cadastrar(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request) {
        HttpSession sessao = request.getSession();
        
        if (sessao == null || sessao.getAttribute("usuario") == null) {
            return "redirect:/";
        }
        
        var usuario = usuarioService.getByUsername(sessao.getAttribute("usuario").toString());
        
        if (!livroService.existsById(id) || usuario.getRole().toString().equalsIgnoreCase("ADMINISTRADOR")) {
            return "redirect:/emprestimos/listar";
        }
        
        model.addAttribute("nome", usuario.getNome());
        model.addAttribute("bibliotecario", usuario);
        model.addAttribute("role", usuario.getRole());
        
        model.addAttribute("emprestimo", new EmprestimoModel());
        model.addAttribute("livro", livroService.getById(id));
        model.addAttribute("alunos", alunoService.getAlunosAtivos());
        model.addAttribute("usuarios", usuarioService.getBibliotecariosAtivos());
        return "cadastro-emprestimo";
    }
    
    @PostMapping("/cadastrar/{id}")
    public String cadastrarObra(@PathVariable(name = "id") Long id, @ModelAttribute EmprestimoModel emprestimo) {
        if (!livroService.existsById(id)) {
            return "redirect:/emprestimos/listar";
        }
        
        EmprestimoModel emprestimoModel = new EmprestimoModel();
        emprestimoModel.setBibliotecario(emprestimo.getBibliotecario());
        emprestimoModel.setAlunoModel(emprestimo.getAlunoModel());
        emprestimoModel.setDataEmprestimo(emprestimo.getDataEmprestimo());
        emprestimoModel.setPrazo(emprestimo.getPrazo());
        
        LivroModel livroModel = livroService.getById(id);
        livroModel.setAlugado(true);
        emprestimoModel.setDevolvido(false);
        emprestimoModel.setLivroModel(livroModel);
        emprestimoService.save(emprestimoModel);
        livroService.updateById(livroModel, id);
        
        return "redirect:/emprestimos/listar";
    }
    
}
