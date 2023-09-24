package com.example.escola.controller;

import com.example.escola.enums.Genero;
import com.example.escola.model.Funcao;
import com.example.escola.model.Funcionario;
import com.example.escola.service.FuncaoService;
import com.example.escola.service.FuncionarioService;
import com.example.escola.enums.Genero;
import com.example.escola.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.escola.repository.FuncionarioRepository;
import com.example.escola.repository.FuncaoRepository;


@Controller
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    private FuncaoService funcaoService;


    @Autowired
    private FuncionarioRepository funcionarioRepository; // Declare FuncionarioRepository

    @Autowired
    private FuncaoRepository funcaoRepository; // Declare FuncaoRepository


    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService,  FuncaoService funcaoService) {
        this.funcionarioService = funcionarioService;
        this.funcaoService = funcaoService;
    }

    @GetMapping("/funcionario/list")
    public String getAllFuncionario(Model model){
        List<Funcionario> funcionarios = funcionarioService.getAllFuncionario();
        model.addAttribute("funcionario", funcionarios);
        return "pages/funcionario/list";
    }


    @GetMapping("/funcionario/delete/{id}")
    public String deleteFuncionario(@PathVariable("id") Long id){
        funcionarioService.deleteFuncionario(id);
        return "redirect:/funcionario/list";
    }



    @GetMapping("/funcionario/new")
    public String addFuncionario(Model model){
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("generos", Genero.values());
//        List<Funcao> funcoes = funcaoService.getAllFuncao();
//        System.out.println("Number of funcoes retrieved: " + funcoes.size()); // Add this line
//        model.addAttribute("funcoes", funcoes);
        model.addAttribute("funcoes", funcaoService.getAllFuncao());
        return "pages/funcionario/new";
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

    @PostMapping("/funcionario/save")
    public String saveFuncionario(@Valid @ModelAttribute("funcionario") Funcionario funcionario,
                                  BindingResult funcionarioResult,
                                  Model model,
                                  @RequestParam("datanas") String datanasString,
                                  @RequestParam(value = "funcaoId", required = false) Long funcaoId) {

        if (funcionarioResult.hasErrors()) {
            model.addAttribute("generos", Genero.values());
            model.addAttribute("turmas", funcaoService.getAllFuncao());

            return "pages/funcionario/new";
        }

        Date dataNascimento = stringToDate(datanasString);
        funcionario.setDatanas(dataNascimento);

        if (funcaoId != null) {
            Funcao funcao = funcaoRepository.findById(funcaoId).orElse(null);
            if (funcao != null) {
                funcionario.setFuncao(funcao);
            }
        }

        if (funcionarioService.nifExists(funcionario.getNif())) {
            funcionarioResult.rejectValue("nif", "nif.duplicate", "NIF já existe no banco de dados.");
            model.addAttribute("generos", Genero.values());
            return "pages/funcionario/new";
        }

        Funcionario _funcionario = funcionarioService.saveFuncionario(funcionario);

        return "redirect:/funcionario/new";
    }







    @GetMapping("/funcionario/edit/{id}")
    public String editFuncionario(@PathVariable("id") Long id, Model model){
        Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(id);
        if (funcionario.isPresent()){
            model.addAttribute("funcionario", funcionario.get());
            model.addAttribute("generos", Genero.values());
            model.addAttribute("funcoes", funcaoService.getAllFuncao());


            return "pages/funcionario/edit";
        }
        return "redirect:/funcionario/list";
    }
    @PostMapping("/funcionario/{id}/edit")
    public String updateFuncionario(@PathVariable("id") Long id, @Valid @ModelAttribute("funcionario") Funcionario funcionario,
                                    BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("generos", Genero.values());
            model.addAttribute("funcoes", funcaoService.getAllFuncao());

            return "pages/funcionario/edit";
        }

        if (funcionarioService.nifExistsExceptCurrent(id, funcionario.getNif())) {
            result.rejectValue("nif", "nif.duplicate", "NIF já existe no banco de dados.");
            model.addAttribute("generos", Genero.values());
            return "pages/funcionario/new";
        }

        Funcionario updatedFuncionario = funcionarioService.updateFuncionario(id,funcionario);

        if (updatedFuncionario == null){
            model.addAttribute("generos", Genero.values());
            return "pages/funcionario/edit";
        }else{
            model.addAttribute("generos", Genero.values());
            model.addAttribute("funcoes", funcaoService.getAllFuncao());
            model.addAttribute("sucesso", "Funcionario atualizado com sucesso!");
            return "pages/funcionario/edit";   //return "redirect:/funcionario/list";
        }

    }

    @GetMapping("/funcionario/search")
    public String pesquisaFuncionario(@RequestParam("keyword") String keyword, Model model){
        List<Funcionario> funcionario= funcionarioService.getFuncionarioByNome(keyword);
        model.addAttribute("funcionario", funcionario);
        model.addAttribute("keyword", keyword);

        return "pages/funcionario/list";
    }




}