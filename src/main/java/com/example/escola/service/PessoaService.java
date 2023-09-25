package com.example.escola.service;

import com.example.escola.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private ProfessorService professorService;
    private AlunoService alunoService;
    private FuncionarioService funcionarioService;

    @Autowired
    public PessoaService(
            ProfessorService professorService,
            AlunoService alunoService,
            FuncionarioService funcionarioService) {
        this.professorService = professorService;
        this.alunoService = alunoService;
        this.funcionarioService = funcionarioService;
    }

    public boolean nifExistsInAnyPerson(String nif) {
        boolean nifExistsInProfessores = professorService.nifExists(nif);
        boolean nifExistsInAlunos = alunoService.nifExists(nif);
        boolean nifExistsInFuncionarios = funcionarioService.nifExists(nif);

        return nifExistsInProfessores || nifExistsInAlunos || nifExistsInFuncionarios;
    }
}
