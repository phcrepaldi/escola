package com.example.escola.repository;

import com.example.escola.model.Aluno;
import com.example.escola.model.Funcao;
import com.example.escola.model.Funcionario;
import com.example.escola.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByAlunos(Aluno aluno);
    Turma findByTurmanome(String turmanome);
    boolean existsByTurmanome(String turmanome);


}




