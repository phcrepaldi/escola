package com.example.escola.model;

import com.example.escola.enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "aluno")
public class Aluno extends Pessoa{

    @NotBlank(message = "O diretor de turma é obrigatório")
    //@Basic(optional = false)
    @Length(min = 1, max = 128, message = "Nome deve ter entre 1 e 128 caracteres")
    @Column(name = "diretor")
    private String diretor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="alunos_disciplinas",
            joinColumns = @JoinColumn(name="aluno_id"),
            inverseJoinColumns = @JoinColumn(name="disciplina_id")
    )
    private List<Disciplina> disciplinas=new ArrayList<>();

    public Aluno() {
    }

    public Aluno(Long id, String nome, String apelido, Date dataNascimento, Genero genero, String email, String nif, String telefone, String morada, String diretor) {
        super(id, nome, apelido, dataNascimento, genero, email, nif, telefone, morada);
        this.diretor = diretor;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Disciplina> getDisciplinas() {return disciplinas;}

    public void setDisciplinas(List<Disciplina> disciplinas){this.disciplinas=disciplinas;}

}
