package com.example.escola.controller;


import com.example.escola.model.Funcao;
import com.example.escola.model.Funcionario;
import com.example.escola.service.FuncaoService;
import com.example.escola.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class FuncaoController {

    private final FuncaoService funcaoService;
    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncaoController(FuncaoService funcaoService, FuncionarioService funcionarioService) {
        this.funcaoService = funcaoService;
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/funcao/list")
    public String getAllFuncao(Model model){
        List<Funcao> funcao = funcaoService.getAllFuncao();
        model.addAttribute("funcao", funcao);
        model.addAttribute("funcionario", funcionarioService.getAllFuncionario());

        return "pages/funcao/list";
    }

    @GetMapping("/funcao/view/{id}")
    public String getFuncaoById(@PathVariable("id") Long id, Model model){
        Optional<Funcao> funcao = funcaoService.getFuncaoById(id);
        if (funcao.isPresent()){
            model.addAttribute("funcao", funcao.get());
            model.addAttribute("funcionario", funcionarioService.getFuncionarioByFuncao(funcao.get()));

            return "pages/funcao/view";
        }
        return "redirect:/funcao/list";

    }
    @GetMapping("/funcao/new")
    public String addFuncao(Model model){
        model.addAttribute("funcao", new Funcao());
        model.addAttribute("funcionario", funcionarioService.getAllFuncionario());
        return "pages/funcao/new";
    }
    @PostMapping("/funcao/save")
    public String saveFuncao(@Valid @ModelAttribute("funcao") Funcao funcao, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("funcionario", funcionarioService.getAllFuncionario());
            return "pages/funcao/new";
        }
        funcaoService.saveFuncao(funcao);
        return "redirect:/funcao/new";
    }

    @GetMapping("/funcao/delete/{id}")
    public String deleteFuncao(@PathVariable("id") Long id){
        funcaoService.deleteFuncao(id);
        return "redirect:/funcao/list";
    }

    @GetMapping("/funcao/edit/{id}")
    public String editFuncao(@PathVariable("id") Long id, Model model){
        Optional<Funcao> funcao = funcaoService.getFuncaoById(id);
        if (funcao.isPresent()){
            model.addAttribute("funcao", funcao.get());
            model.addAttribute("funcionario", funcionarioService.getAllFuncionario());

            return "pages/funcao/edit";
        }
        return "redirect:/funcao/list";
    }
    @PostMapping("/funcao/{id}/edit")
    public String updateFuncionario(@PathVariable("id") Long id, @Valid Funcao funcao, BindingResult result, Model model) {

//        Funcao updatedFuncao = funcaoService.updateFuncao(id, funcao);
//        if (updatedFuncao== null){
//            return "pages/funcao/edit";
//        }
//        return "redirect:/funcao/list";
//    }


        Optional<Funcao> existingFuncao = funcaoService.getFuncaoById(id);
        if (existingFuncao.isPresent()) {
            Funcao updatedFuncao = existingFuncao.get();

            // Atualize os campos da entidade Funcao conforme necessário
            updatedFuncao.setFuncaonome(funcao.getFuncaonome());
            // ... atualize outros campos

            // Adicione os funcionários associados à função original à coleção da função atualizada
            updatedFuncao.getFuncionarios().addAll(existingFuncao.get().getFuncionarios());

            // Salve a entidade Funcao atualizada
            funcaoService.saveFuncao(updatedFuncao);
            model.addAttribute("sucesso", "Função atualizada com sucesso!");

            return "pages/funcao/edit";
//            return "redirect:/funcao/list";
        }
        return "pages/funcao/list";
    }

}
