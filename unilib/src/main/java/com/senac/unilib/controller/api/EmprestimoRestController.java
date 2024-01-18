package com.senac.unilib.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.unilib.model.EmprestimoModel;
import com.senac.unilib.model.LivroModel;
import com.senac.unilib.services.EmprestimoService;
import com.senac.unilib.services.LivroService;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoRestController {

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private LivroService livroService;

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable(name = "id") Long id) {
        if (emprestimoService.existsById(id)) {
            if (!emprestimoService.getById(id).isDevolvido()) {
                EmprestimoModel emprestimoModel = emprestimoService.getById(id);
                emprestimoModel.devolver();
                LivroModel livroModel = emprestimoModel.getLivroModel();
                livroService.updateById(livroModel, livroModel.getId());
                emprestimoService.updateById(emprestimoModel, id);
            }

            emprestimoService.deleteById(id);
        }
    }

    @PutMapping("/devolver/{id}")
    public void devolver(@PathVariable(name = "id") Long id) {
        if (emprestimoService.existsById(id)) {
            EmprestimoModel emprestimoModel = emprestimoService.getById(id);
            emprestimoModel.devolver();
            LivroModel livroModel = emprestimoModel.getLivroModel();
            livroService.updateById(livroModel, livroModel.getId());
            emprestimoService.updateById(emprestimoModel, id);
        }
    }

}
