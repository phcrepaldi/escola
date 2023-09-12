package com.example.escola.repository;
import com.example.escola.model.Funcao;
import com.example.escola.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByNomeContainingIgnoreCase(String keyword);
    List<Funcionario> findByFuncao(Funcao funcao);
}