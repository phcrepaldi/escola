package com.example.escola.controller;

import com.example.escola.enums.Genero;
import com.example.escola.model.Professor;
import com.example.escola.service.DisciplinaService;
import com.example.escola.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfessorController {

    private final ProfessorService professorService;

    private final DisciplinaService disciplinaService;
    @Autowired
    public ProfessorController(ProfessorService professorService, DisciplinaService disciplinaService){
        this.professorService=professorService;
        this.disciplinaService=disciplinaService;
    }

//    @GetMapping("/index")
//    public String index() {
//        return "pages/index";
//    }

    @GetMapping("/professores/list")
    public String getAllProfessores(Model model){
        List<Professor> professores=professorService.getallProfessores();
        model.addAttribute("professores", professores);
        model.addAttribute("disciplinas", disciplinaService.getAllDisciplinas());

        return "pages/professores/list";
    }

    @GetMapping("/professores/vew/{id}")
    public String getProfessorById(@PathVariable("id") Long id, Model model){
        Optional<Professor> professor = professorService.getProfessorById(id);

        if(professor.isPresent()){
            model.addAttribute("professor", professor.get());
            model.addAttribute("disciplinas", disciplinaService.getDisciplinasByProfessor(professor.get()));

            return "pages/professores/vew";
        }
        return "redirect:/professores/list";
    }

    @GetMapping("professores/new")
    public String addProfessor(Model model){
        model.addAttribute("professor", new Professor());
        model.addAttribute("generos", Genero.values());
        model.addAttribute("disciplinas", disciplinaService.getAllDisciplinas());

        return "pages/professores/new";

    }

    @PostMapping("/professores/save")
    public String saveProfessor(@Valid @ModelAttribute("professor") Professor professor,
                                BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("generos", Genero.values());
            model.addAttribute("disciplinas", disciplinaService.getAllDisciplinas());
            return "pages/professores/new";
        }

        professorService.saveProfessor(professor);

        return "redirect:/professores/new";
    }

    @GetMapping("professores/delete/{id}")
    public String deleteProfessor(@PathVariable("id") Long id){
        professorService.deleteProfessor(id);

        return "redirect:/professores/list";
    }

    @GetMapping("/professores/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Optional <Professor> optionalProfessor = professorService.getProfessorById(id);
        if(optionalProfessor.isPresent()){
            Professor professor = optionalProfessor.get();
            model.addAttribute("professor", optionalProfessor.get());
            model.addAttribute("generos", Genero.values());
            model.addAttribute("disciplinas", disciplinaService.getAllDisciplinas());

            return "pages/professores/edit";
        }
        return "redirect:/professores/list";
    }

    @PostMapping("professores/edit/{id}")
    public String updateProfessor(@PathVariable("id") Long id, @Valid @ModelAttribute("professor")
                                Professor professor,
                               BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("generos", Genero.values());
            model.addAttribute("disciplinas", disciplinaService.getAllDisciplinas());
            return "pages/professores/edit";
        }
        Professor updateProfessor=professorService.updateProfessor(id, professor);

        if(updateProfessor!=null){
            model.addAttribute("disciplinas",disciplinaService.getAllDisciplinas());
            model.addAttribute("generos", Genero.values());
            model.addAttribute("sucesso", "Modelo atualizado com sucesso");
            return "pages/professores/edit";
        }

        return "redirect:/professores/list";
    }

    @GetMapping("professores/search")
    public String pesquisaProfessores(@RequestParam("keyword") String keyword, Model model){

        List<Professor> professores = professorService.getProfessoresByNome(keyword);
        model.addAttribute("professores", professores);
        model.addAttribute("keyword", keyword);

        return "pages/professores/list";
    }

}
