package com.example.escola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="funcao")
public class Funcao extends BaseEntity{
    @Column(name="funcao", nullable=false)
    @NotBlank(message="Função é obrigatória")
    @Length(min = 1, max = 128, message = "O tamanho tem que ser entre 1 e 128 caracteres")
    private String funcaonome;

    @OneToMany(mappedBy = "funcao")
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Funcao() {}

    @Override
    public String toString() {
        return funcaonome; // Return the meaningful property for display
    }

    public String getFuncaonome() {
        return funcaonome;
    }

    public void setFuncaonome(String funcaonome) {
        this.funcaonome = funcaonome;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
}