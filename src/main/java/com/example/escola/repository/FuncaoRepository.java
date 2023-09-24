package com.example.escola.repository;

import com.example.escola.model.Funcao;
import com.example.escola.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncaoRepository extends JpaRepository<Funcao, Long> {
    List<Funcao> findByFuncionarios(Funcionario funcionario);
    Funcao findByFuncaonome(String funcaonome);

    boolean existsByFuncaonome(String funcaonome);

}




