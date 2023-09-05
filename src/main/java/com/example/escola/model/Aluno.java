package com.example.escola.model;

import com.example.escola.enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Entity
@Table(name = "aluno")
public class Aluno extends Pessoa{

    @NotBlank(message = "O responsável é obrigatório")
    //@Basic(optional = false)
    @Length(min = 1, max = 128, message = "Nome deve ter entre 1 e 128 caracteres")
    @Column(name = "responsavel")
    private String responsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;



    public Aluno() {
    }

    public Aluno(Long id, String nome, String apelido, Date dataNascimento, Genero genero, String email, String nif, String telefone, String morada, String responsavel) {
        super(id, nome, apelido, dataNascimento, genero, email, nif, telefone, morada);
        this.responsavel = responsavel;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

}