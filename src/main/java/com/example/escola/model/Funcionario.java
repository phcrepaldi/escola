package com.example.escola.model;

import com.example.escola.enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "funcionario")
public class Funcionario extends Pessoa{
    @NotNull(message = "O salário não pode ser nulo")
    @Basic(optional = false)
    @Min(value = 0, message = "O saláririo está abaixo do valor minimo")
    @Max(value = 100000, message = "O saláririo está acima do valor máximo")
    @Column(name = "salario")
    private Double salario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcao_id", nullable = true)
    private Funcao funcao;



    public Funcionario() {
    }

    public Funcionario(Long id, String nome, String apelido, Date dataNascimento, Genero genero, String email, String nif, String telefone, String morada, Double salario) {
        super(id, nome, apelido, dataNascimento, genero, email, nif, telefone, morada);
        this.salario = salario;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }
}
