package com.example.escola.service;

import com.example.escola.model.*;
import com.example.escola.repository.AlunoRepository;
import com.example.escola.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TurmaService {
    private final TurmaRepository turmaRepository;
    private final AlunoRepository alunoRepository;
    @Autowired
    public TurmaService(TurmaRepository turmaRepository, AlunoRepository alunoRepository) {
        this.turmaRepository = turmaRepository;
        this.alunoRepository = alunoRepository;
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

    public void deleteTurma(Long id) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);

        if (turmaOptional.isPresent()) {
            Turma turma = turmaOptional.get();

            List<Aluno> alunos = turma.getAlunos();
            if (alunos != null && !alunos.isEmpty()) {
                for (Aluno aluno : alunos) {
                    aluno.setTurma(null);
                    alunoRepository.save(aluno);
                }
            }
            turmaRepository.delete(turma);
        }
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
