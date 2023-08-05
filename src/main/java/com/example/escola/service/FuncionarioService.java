package com.example.escola.service;

import com.example.escola.repository.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.escola.model.Funcionario;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario saveFuncionario(@Valid Funcionario funcionario){
        return funcionarioRepository.save(funcionario);
    }


}
