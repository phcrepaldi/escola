package com.example.escola.repository;

import com.example.escola.model.Disciplina;
import com.example.escola.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByProfessores(Professor professor);
}
