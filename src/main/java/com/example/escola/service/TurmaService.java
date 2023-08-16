package com.example.escola.service;

import com.example.escola.model.Funcao;
import com.example.escola.model.Funcionario;
import com.example.escola.model.Turma;
import com.example.escola.model.Aluno;
import com.example.escola.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TurmaService {
    private final TurmaRepository turmaRepository;
    @Autowired
    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public List<Turma> getAllTurma(){
        return turmaRepository.findAll();
    }
    public Turma saveTurma(Turma turmanome){
        return turmaRepository.save(turmanome);
    }

    public Optional<Turma> getTurmaById(Long id){
        Optional<Turma> turma = turmaRepository.findById(id);
        return turma;
    }
    public void deleteTurma(Long id){
        turmaRepository.deleteById(id);
    }

    public Turma updateTurma(Long id, Turma turma){
        Optional<Turma> turmaOptional = getTurmaById(id);

        if(turmaOptional.isPresent()){
            Turma _turma = turmaOptional.get();
            _turma.setTurmanome(turma.getTurmanome());

            return turmaRepository.save(_turma);
        }
        return null;
    }

    public List<Turma> getTurmasByAluno(Aluno aluno){
        return turmaRepository.findByAlunos(aluno);
    }

}
