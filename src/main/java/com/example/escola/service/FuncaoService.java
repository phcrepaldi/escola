package com.example.escola.service;

import com.example.escola.model.*;
import com.example.escola.repository.FuncaoRepository;
import com.example.escola.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class FuncaoService {
    private final FuncaoRepository funcaoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public FuncaoService(FuncaoRepository funcaoRepository, FuncionarioRepository funcionarioRepository) {
        this.funcaoRepository = funcaoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }
    public List<Funcao> getAllFuncao(){
        return funcaoRepository.findAll();
    }
    public Funcao saveFuncao(Funcao funcaonome){
        return funcaoRepository.save(funcaonome);
    }

    public Optional<Funcao> getFuncaoById(Long id){
        Optional<Funcao> funcao = funcaoRepository.findById(id);
        return funcao;
    }


    @Transactional
    public void deleteFuncao(Long id) {
        Optional<Funcao> funcaoOptional = funcaoRepository.findById(id);

        if (funcaoOptional.isPresent()) {
            Funcao funcao = funcaoOptional.get();

            List<Funcionario> funcionarios = funcao.getFuncionarios();
            for (Funcionario funcionario : funcionarios) {
                funcionario.setFuncao(null);
                funcionarioRepository.save(funcionario);
            }

            funcaoRepository.delete(funcao);
        }
    }

    public Funcao updateFuncao(Long id, Funcao funcao){
        Optional<Funcao> funcaoOptional = getFuncaoById(id);

        if(funcaoOptional.isPresent()){
            Funcao _funcao = funcaoOptional.get();
            _funcao.setFuncaonome(funcao.getFuncaonome());
            _funcao.setFuncionarios(funcao.getFuncionarios());

            return funcaoRepository.save(_funcao);
        }
        return null;
    }

    public List<Funcao> getFuncoesByFuncionario(Funcionario funcionario){
        return funcaoRepository.findByFuncionarios(funcionario);
    }


}
