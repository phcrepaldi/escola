package com.example.escola.service;

import com.example.escola.model.Funcao;
import com.example.escola.repository.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.escola.repository.FuncaoRepository;
import com.example.escola.model.Funcao;
import com.example.escola.repository.FuncionarioRepository;
import com.example.escola.model.Funcionario;
import com.example.escola.model.Funcao;

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

    @Autowired
    private FuncaoRepository funcaoRepository;

    public Funcionario updateFuncionario(Long id, Funcionario funcionario){
        Optional<Funcionario> funcionarioOptional = getFuncionarioById(id);

        if(funcionarioOptional.isPresent()){
            Funcionario _funcionario = funcionarioOptional.get();
            _funcionario.setNome(funcionario.getNome());
            _funcionario.setApelido(funcionario.getApelido());
            _funcionario.setDatanas(funcionario.getDatanas());
            _funcionario.setGenero(funcionario.getGenero());
            _funcionario.setEmail(funcionario.getEmail());
            _funcionario.setNif(funcionario.getNif());
            _funcionario.setTelefone(funcionario.getTelefone());
            _funcionario.setMorada(funcionario.getMorada());
            _funcionario.setSalario(funcionario.getSalario());
            _funcionario.setFuncao(funcionario.getFuncao());

            return funcionarioRepository.save(_funcionario);
        }
        return null;
    }


    public Funcionario saveFuncionario(@Valid Funcionario funcionario){
        return funcionarioRepository.save(funcionario);
    }


    public List<Funcionario> getAllFuncionario(){
        return funcionarioRepository.findAll();
    }


    public Optional<Funcionario> getFuncionarioById(Long id){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return funcionario;
    }

    public void deleteFuncionario(Long id){funcionarioRepository.deleteById(id);
    }

    public List<Funcionario> getFuncionarioByNome(String keyword){
        return funcionarioRepository.findByNomeContainingIgnoreCase(keyword);
    }

}
