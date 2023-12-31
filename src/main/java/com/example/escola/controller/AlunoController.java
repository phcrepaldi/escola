package com.example.escola.controller;

import com.example.escola.enums.Genero;
import com.example.escola.model.Funcao;
import com.example.escola.model.Aluno;
import com.example.escola.model.Turma;
import com.example.escola.repository.FuncaoRepository;
import com.example.escola.repository.AlunoRepository;
import com.example.escola.repository.TurmaRepository;
import com.example.escola.service.DisciplinaService;
import com.example.escola.service.FuncaoService;
import com.example.escola.service.AlunoService;
import com.example.escola.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.escola.service.PessoaService;



@Controller
public class AlunoController {

    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final PessoaService pessoaService;


    private TurmaService turmaService;


    @Autowired
    private AlunoRepository alunoRepository; // Declare AlunoRepository


    @Autowired
    private TurmaRepository turmaRepository; // Declare TurmaRepository


    @Autowired
    public AlunoController(AlunoService alunoService, TurmaService turmaService, DisciplinaService disciplinaService, PessoaService pessoaService) {
        this.alunoService = alunoService;
        this.turmaService = turmaService;
        this.disciplinaService=disciplinaService;
        this.pessoaService = pessoaService;

    }

    @GetMapping("/aluno/list")
    public String getAllAluno(Model model){
        List<Aluno> alunos = alunoService.getAllAluno();
        model.addAttribute("aluno", alunos);
        return "pages/aluno/list";
    }


    @GetMapping("/aluno/delete/{id}")
    public String deleteAluno(@PathVariable("id") Long id){
        alunoService.deleteAluno(id);
        return "redirect:/aluno/list";
    }



    @GetMapping("/aluno/new")
    public String addAluno(Model model){
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("generos", Genero.values());
        model.addAttribute("turmas", turmaService.getAllTurma());
        model.addAttribute("disciplinas", disciplinaService.getAllDisciplinas());

        return "pages/aluno/new";
    }

    // Método auxiliar para converter String em Date
    private Date stringToDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/aluno/save")
    public String saveAluno(@Valid @ModelAttribute("aluno") Aluno aluno,
                            BindingResult alunoResult,
                            Model model,
                            @RequestParam("datanas") String datanasString) {

        if (alunoResult.hasErrors()) {
            model.addAttribute("generos", Genero.values());
            model.addAttribute("turmas", turmaService.getAllTurma());
            model.addAttribute("disciplinas", disciplinaService.getAllDisciplinas());
            return "pages/aluno/new";
        }

        Turma turmaDoAluno = aluno.getTurma();
        if (turmaDoAluno == null) {
            aluno.setTurma(null); // Certifique-se de que a turma seja nula se não estiver definida
        } else if (turmaDoAluno.getTurmanome() == null || turmaDoAluno.getTurmanome().isEmpty()) {
            alunoResult.rejectValue("turma.turmanome", "field.required", "A turma é obrigatória.");
            model.addAttribute("generos", Genero.values());
            model.addAttribute("disciplinas", disciplinaService.getAllDisciplinas());
            return "pages/aluno/new";
        }

        Long turmaId = turmaDoAluno != null ? turmaDoAluno.getId() : null;
        Date dataNascimento = stringToDate(datanasString);
        aluno.setDatanas(dataNascimento);

        if (turmaId != null) {
            Turma turma = turmaRepository.findById(turmaId).orElse(null);
            if (turma != null) {
                aluno.setTurma(turma);
            }
        } else {
            aluno.setTurma(null); // Defina a turma como nula se não houver turma selecionada
        }

        if (alunoService.nifExists(aluno.getNif()) || pessoaService.nifExistsInAnyPerson(aluno.getNif())) {
            alunoResult.rejectValue("nif", "nif.duplicate", "NIF já existe no banco de dados.");
            model.addAttribute("generos", Genero.values());
            model.addAttribute("turmas", turmaService.getAllTurma());
            model.addAttribute("disciplinas", disciplinaService.getAllDisciplinas());
            return "pages/aluno/new";
        }

        Aluno _aluno = alunoService.saveAluno(aluno);

        return "redirect:/aluno/new";
    }



    @GetMapping("/aluno/edit/{id}")
    public String editAluno(@PathVariable("id") Long id, Model model){
        Optional<Aluno> aluno = alunoService.getAlunoById(id);
        if (aluno.isPresent()){
            model.addAttribute("aluno", aluno.get());
            model.addAttribute("generos", Genero.values());
            model.addAttribute("turmas", turmaService.getAllTurma());
            model.addAttribute("allDisciplinas", disciplinaService.getAllDisciplinas());



            return "pages/aluno/edit";
        }
        return "redirect:/aluno/list";
    }


    @PostMapping("/aluno/{id}/edit")
    public String updateAluno(@PathVariable("id") Long id, @Valid @ModelAttribute("aluno") Aluno aluno,
                              BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("generos", Genero.values());
            model.addAttribute("turmas", turmaService.getAllTurma());
            model.addAttribute("allDisciplinas", disciplinaService.getAllDisciplinas());


            return "pages/aluno/edit";
        }

        Optional<Aluno> existingAluno = alunoService.getAlunoById(id);
        if (!existingAluno.isPresent()) {
            return "redirect:/aluno/list";
        }

        if (!aluno.getNif().equals(existingAluno.get().getNif())) {
            if (alunoService.nifExists(aluno.getNif()) || pessoaService.nifExistsInAnyPerson(aluno.getNif())) {
                result.rejectValue("nif", "nif.duplicate", "NIF já existe no banco de dados.");
                model.addAttribute("generos", Genero.values());
                return "pages/aluno/edit";
            }
        }

        Aluno updatedAluno = alunoService.updateAluno(id,aluno);

        if (updatedAluno == null){
            model.addAttribute("generos", Genero.values());
            model.addAttribute("allDisciplinas", disciplinaService.getAllDisciplinas());

            return "pages/aluno/edit";
        }else{
            model.addAttribute("generos", Genero.values());
            model.addAttribute("turmas", turmaService.getAllTurma());
            model.addAttribute("allDisciplinas", disciplinaService.getAllDisciplinas());

            model.addAttribute("sucesso", "Aluno atualizado com sucesso!");
            return "pages/aluno/edit";   //return "redirect:/aluno/list";
        }

    }

    @GetMapping("/aluno/search")
    public String pesquisaAluno(@RequestParam("keyword") String keyword, Model model){
        List<Aluno> aluno= alunoService.getAlunoByNome(keyword);
        model.addAttribute("aluno", aluno);
        model.addAttribute("keyword", keyword);

        return "pages/aluno/list";
    }




}
