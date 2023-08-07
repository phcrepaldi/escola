package com.example.escola.model;

import com.example.escola.enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Professores")
@Comment(value="Tabela dos Professores")
public class Professor extends Pessoa{
    @NotNull(message = "O salário não pode ser nulo")
    @Basic(optional = false)
    @Min(value = 0, message = "Data de nascimento está abaixo do valor minimo")
    @Max(value = 100000, message = "Data de nascimento está acima do valor máximo")
    @Column(name = "salario")
    private Double salario;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="professores_disciplinas",
            joinColumns = @JoinColumn(name="professor_id"),
            inverseJoinColumns = @JoinColumn(name="disciplina_id")
    )
    private List<Disciplina> disciplinas=new ArrayList<>();*/

    public Professor() {}

    public Professor(Long id, String nome, String apelido, Date dataNascimento, Genero genero, String email, String nif, String telefone, String morada) {
        super(id, nome, apelido, dataNascimento, genero, email, nif, telefone, morada);
        this.salario=salario;
    }

    public Double getSalario() {return salario;}

    public void setSalario(Double salario) {this.salario = salario;}
}


