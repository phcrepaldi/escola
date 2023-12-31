package com.example.escola.repository;
import com.example.escola.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.escola.model.Professor;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Professor> findByNomeContainingIgnoreCase(String keyword);

    List<Professor> findByDisciplinas(Disciplina disciplina);

    boolean existsByNif(String nif);
    boolean existsByNifAndIdNot(String nif, Long id);


}
