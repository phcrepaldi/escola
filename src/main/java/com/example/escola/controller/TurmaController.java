package com.example.escola.controller;


import com.example.escola.model.Disciplina;
import com.example.escola.model.Turma;
import com.example.escola.service.TurmaService;
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
public class TurmaController {

    private final TurmaService turmaService;

    @Autowired
    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping("/turma/list")
    public String getAllTurma(Model model){
        List<Turma> turma = turmaService.getAllTurma();
        model.addAttribute("turma", turma);
        return "pages/turma/list";
    }

    @GetMapping("/turma/view/{id}")
    public String getTurmaById(@PathVariable("id") Long id, Model model){
        Optional<Turma> turma = turmaService.getTurmaById(id);
        if (turma.isPresent()){
            model.addAttribute("turma", turma.get());
            return "pages/turma/view";
        }
        return "redirect:/turma/list";

    }
    @GetMapping("/turma/new")
    public String addTurma(Model model){
        model.addAttribute("turma", new Turma());
        return "pages/turma/new";
    }
    @PostMapping("/turma/save")
    public String saveTurma(@Valid @ModelAttribute("turma") Turma turma, BindingResult result){
        if (result.hasErrors()){
            return "pages/turma/new";
        }

        // Verifica se a turma já existe
        if (turmaService.turmaExists(turma.getTurmanome())) {
            result.rejectValue("turmanome", "turma.duplicate", "Turma já existe no banco de dados.");
            return "pages/turma/new";
        }

        turmaService.saveTurma(turma);
        return "redirect:/turma/new";
    }

    @GetMapping("/turma/delete/{id}")
    public String deleteTurma(@PathVariable("id") Long id){
        turmaService.deleteTurma(id);
        return "redirect:/turma/list";
    }

    @GetMapping("/turma/edit/{id}")
    public String editTurma(@PathVariable("id") Long id, Model model){
        Optional<Turma> turma = turmaService.getTurmaById(id);
        if (turma.isPresent()){
            model.addAttribute("turma", turma.get());

            return "pages/turma/edit";
        }
        return "redirect:/turma/list";
    }
    @PostMapping("/turma/{id}/edit")
    public String updateTurma(@PathVariable("id") Long id, @Valid Turma turma, BindingResult result, Model model) {

        if (turmaService.turmaExistsExceptCurrent(id, turma.getTurmanome())) {
            result.rejectValue("turmanome", "turma.duplicate", "Turma já existe no banco de dados.");
            return "pages/turma/edit";
        }

        if (result.hasErrors()) {
            return "pages/turma/edit";
        }

        Turma updatedTurma = turmaService.updateTurma(id, turma);

        if (updatedTurma == null) {
            model.addAttribute("erro", "Algo correu mal.");
            return "pages/turma/edit";
        } else {
            model.addAttribute("sucesso", "Turma atualizada com sucesso.");
            return "pages/turma/edit";
        }
    }











}
