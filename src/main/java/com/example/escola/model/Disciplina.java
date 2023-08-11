package com.example.escola.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Disciplinas")
public class Disciplina extends BaseEntity{
    @Column(name="nomeDisciplina", nullable = false)
    @NotBlank(message="Nome da disciplina é obrigatório.")
    private String nomeDisciplina;

    private String programaDisciplina;

    @ManyToMany(mappedBy = "disciplinas")
    private List<Professor> professores=new ArrayList<>();

    public Disciplina (){};

    public Disciplina(Long id, String nomeDisciplina, String programaDisciplina) {
        super(id);
        this.nomeDisciplina = nomeDisciplina;
        this.programaDisciplina = programaDisciplina;
    }

    public String getNomeDisciplina() {return nomeDisciplina;}

    public void setNomeDisciplina(String nomeDisciplina) {this.nomeDisciplina = nomeDisciplina;}

    public String getProgramaDisciplina() {return programaDisciplina;}

    public void setProgramaDisciplina(String programaDisciplina) {this.programaDisciplina = programaDisciplina;}

    public List<Professor> getProfessores(){return professores;}

    public void setProfessores(List<Professor> professores){this.professores=professores;}


}
