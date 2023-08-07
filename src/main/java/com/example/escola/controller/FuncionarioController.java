package com.example.escola.controller;

import com.example.escola.enums.Genero;
import com.example.escola.model.Funcionario;
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

@Controller
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/funcionario/list")
    public String getAllFuncionario(Model model){
        List<Funcionario> funcionario = funcionarioService.getAllFuncionario();
        model.addAttribute("funcionario", funcionario);
        return "pages/funcionario/list";
    }






    @GetMapping("/funcionario/new")
    public String addFuncionario(Model model){
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("generos", Genero.values());
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
    //   public String saveFuncionario(@Valid Funcionario funcionario){
    public String saveFuncionario(@Valid @ModelAttribute("funcionario") Funcionario funcionario,
                            BindingResult funcionarioResult,
                            Model model,
                            @RequestParam("datanas") String datanasString){
        if (funcionarioResult.hasErrors()){
            model.addAttribute("generos", Genero.values());
            //model.addAttribute("detalhes", new FuncionarioDetalhes());
            return "pages/funcionario/new";
        }
        Date dataNascimento = stringToDate(datanasString);
        funcionario.setDatanas(dataNascimento);

        Funcionario _funcionario = funcionarioService.saveFuncionario(funcionario);
        return "redirect:/funcionario/new";
    }

}
