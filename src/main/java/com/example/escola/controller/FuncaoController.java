package com.example.escola.controller;


import com.example.escola.model.Funcao;
import com.example.escola.service.FuncaoService;
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

    @Autowired
    public FuncaoController(FuncaoService funcaoService) {
        this.funcaoService = funcaoService;
    }

    @GetMapping("/funcao/list")
    public String getAllFuncao(Model model){
        List<Funcao> funcao = funcaoService.getAllFuncao();
        model.addAttribute("funcao", funcao);
        return "pages/funcao/list";
    }

    @GetMapping("/funcao/view/{id}")
    public String getFuncaoById(@PathVariable("id") Long id, Model model){
        Optional<Funcao> funcao = funcaoService.getFuncaoById(id);
        if (funcao.isPresent()){
            model.addAttribute("funcao", funcao.get());
            return "pages/funcao/view";
        }
        return "redirect:/funcao/list";

    }
    @GetMapping("/funcao/new")
    public String addFuncao(Model model){
        model.addAttribute("funcao", new Funcao());
        return "pages/funcao/new";
    }
    @PostMapping("/funcao/save")
    public String saveFuncao(@Valid @ModelAttribute("funcao") Funcao funcao, BindingResult result){
        if (result.hasErrors()){
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

            return "pages/funcao/edit";
        }
        return "redirect:/funcao/list";
    }
    @PostMapping("/funcao/{id}/edit")
    public String updateFuncionario(@PathVariable("id") Long id, @Valid Funcao funcao, BindingResult result, Model model){

        Funcao updatedFuncao = funcaoService.updateFuncao(id, funcao);
        if (updatedFuncao== null){
            return "pages/funcao/edit";
        }
        return "redirect:/funcao/list";
    }


}
