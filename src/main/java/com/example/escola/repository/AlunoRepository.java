package com.example.escola.repository;
import com.example.escola.model.Aluno;
import com.example.escola.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByNomeContainingIgnoreCase(String keyword);
    boolean existsByNif(String nif);
    boolean existsByNifAndIdNot(String nif, Long id);


}