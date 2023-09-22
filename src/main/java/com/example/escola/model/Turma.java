package com.example.escola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="turma")
public class Turma extends BaseEntity{
    @Column(name="turma", nullable=false)
    @NotBlank(message="Turma é obrigatória")
    @Length(min = 1, max = 128, message = "O tamanho tem que ser entre 1 e 128 caracteres")
    private String turmanome;

    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos = new ArrayList<>();

    public Turma() {}

    @Override
    public String toString() {
        return turmanome; // Return the meaningful property for display
    }

    public String getTurmanome() {
        return turmanome;
    }

    public void setTurmanome(String turmanome) {
        this.turmanome = turmanome;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> Alunos) {
        this.alunos = alunos;
    }
}