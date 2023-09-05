package com.example.escola.controller;

import com.example.escola.model.Disciplina;
import com.example.escola.service.DisciplinaService;
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
public class DisciplinaController {

    private final DisciplinaService disciplinaService;
    @Autowired
    public DisciplinaController(DisciplinaService disciplinaService){this.disciplinaService=disciplinaService;}
    @GetMapping("disciplinas/list")
    public String getAllDisciplinas(Model model){
        List<Disciplina> disciplinas = disciplinaService.getAllDisciplinas();
        model.addAttribute("disciplinas", disciplinas);

        return "pages/disciplinas/list";
    }
    @GetMapping("/disciplinas/view/{id}")
    public String getDisciplinaById(@PathVariable("id") Long id, Model model){
        Optional<Disciplina> disciplina=disciplinaService.getDisciplinaById(id);

        if(disciplina.isPresent()){
            model.addAttribute("disciplina", disciplina.get());

            return "pages/disciplinas/view";
        }
        return "redirect:/disciplinas/list";
    }
    @GetMapping
    public String addDisciplina(Model model){
        model.addAttribute("disciplina", new Disciplina());

        return "pages/disciplinas/new";
    }
    @PostMapping("/disciplinas/save")
    public String saveDisciplina(@Valid @ModelAttribute("disciplina") Disciplina disciplina,
                                 BindingResult disciplinaResult, Model model){
        if(disciplinaResult.hasErrors()){
            return "pages/disciplinas/new";
        }

        Disciplina _disciplina=disciplinaService.saveDisciplina(disciplina);

        return "redirect:/disciplinas/new";
    }
    @GetMapping("/disciplinas/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Optional<Disciplina> disciplinaOptional=disciplinaService.getDisciplinaById(id);
        if(disciplinaOptional.isPresent()){
            model.addAttribute("disciplina", disciplinaOptional.get());

            return "pages/disciplinas/edit";
        }
        return "redirect:/disciplinas/list";
    }
    @PostMapping("disciplinas/edit/{id}")
    public String updateDisciplina(@PathVariable("id") Long id, @Valid Disciplina disciplina,
                                   BindingResult result, Model model){
        if(result.hasErrors()){
            return "pages/disciplinas/edit";
        }
        Disciplina updateDisciplina=disciplinaService.updateDisciplina(id, disciplina);
        if(updateDisciplina==null){
            model.addAttribute("erro", "Algo correu mal.");
            return "pages/disciplinas/edit";
        }else{
            model.addAttribute("sucesso", "Disciplina atualizada com sucesso.");
            return "pages/disciplinas/edit";
        }
    }

    public String deleteDisciplina(@PathVariable("id") Long id){
        disciplinaService.deleteDisciplina(id);

        return "redirect:/disciplinas/list";
    }

}