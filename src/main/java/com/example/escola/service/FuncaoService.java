package com.example.escola.service;

import com.example.escola.model.Funcao;
import com.example.escola.model.Funcionario;
import com.example.escola.repository.FuncaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class FuncaoService {
    private final FuncaoRepository funcaoRepository;
    @Autowired
    public FuncaoService(FuncaoRepository funcaoRepository) {
        this.funcaoRepository = funcaoRepository;
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
    public void deleteFuncao(Long id){
        funcaoRepository.deleteById(id);
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
