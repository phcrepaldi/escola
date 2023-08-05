package com.example.escola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.Length;
import com.example.escola.enums.Genero;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.time.LocalDate;

@MappedSuperclass
@Table(name = "pessoa")
@Comment(value="Tabela de Pessoas")
public abstract class Pessoa extends BaseEntity{

    @NotBlank(message = "Nome é obrigatório")
    //@Basic(optional = false)
    @Length(min = 1, max = 128, message = "Nome deve ter entre 1 e 128 caracteres")
    @Column(name = "nome")
    private String nome;
    @NotBlank(message = "Apelido é obrigatório")
    @Basic(optional = false)
    @Length(min = 1, max = 128, message = "Apelido deve ter entre 1 e 128 caracteres")
    @Column(name = "apelido")
    private String apelido;

    @NotNull(message = "Data de nascimento é obrigatório")
    @Past(message = "A data de nascimento deve estar no passado")
    @Column(name = "datanas")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Formato esperado da data
    private Date datanas;
    @Enumerated(EnumType.STRING)
    @Column(name="genero", nullable = false)
    private Genero genero;
    @NotBlank(message = "Email é obrigatório")
    @Basic(optional = false)
    @Length(min = 1, max = 128, message = "Email deve ter entre 1 e 128 caracteres")
    @Column(name = "email")
    private String email;
    @NotBlank(message = "NIF é obrigatório")
    @Basic(optional = false)
    @Length(min = 1, max = 9, message = "NIF deve ter entre 1 e 9 caracteres")
    @Column(name = "nif")
    private String nif;

    @NotBlank(message = "Telefone é obrigatório")
    @Basic(optional = false)
    @Length(min = 1, max = 9, message = "Telefone deve ter entre 1 e 9 caracteres")
    @Column(name = "telefone")
    private String telefone;

    @NotBlank(message = "Morada é obrigatório")
    @Basic(optional = false)
    @Length(min = 1, max = 128, message = "Morada deve ter entre 1 e 128 caracteres")
    @Column(name = "morada")
    private String morada;

/*    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tutorial> tutoriais = new ArrayList<>();*/


    public Pessoa() {
    }

    public Pessoa(Long id, String nome, String apelido, Date dataNascimento, Genero genero, String email, String nif, String telefone, String morada) {
        super(id);
        this.nome = nome;
        this.apelido = apelido;
        this.datanas = dataNascimento;
        this.genero = genero;
        this.email = email;
        this.nif = nif;
        this.telefone = telefone;
        this.morada = morada;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Date getDatanas() {
        return datanas;
    }

    public void setDatanas(Date datanas) {
        this.datanas = datanas;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }
}
