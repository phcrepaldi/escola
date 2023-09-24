package com.example.escola.service;

import com.example.escola.model.Aluno;
import com.example.escola.model.Funcionario;
import com.example.escola.repository.AlunoRepository;
import com.example.escola.repository.TurmaRepository;
import com.example.escola.repository.FuncaoRepository;
import com.example.escola.repository.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;


    @Autowired
    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Autowired
    private FuncaoRepository funcaoRepository;

    public Aluno updateAluno(Long id, Aluno aluno){
        Optional<Aluno> alunoOptional = getAlunoById(id);

        if(alunoOptional.isPresent()){
            Aluno _aluno = alunoOptional.get();
            _aluno.setNome(aluno.getNome());
            _aluno.setApelido(aluno.getApelido());
            _aluno.setDatanas(aluno.getDatanas());
            _aluno.setGenero(aluno.getGenero());
            _aluno.setEmail(aluno.getEmail());
            _aluno.setNif(aluno.getNif());
            _aluno.setTelefone(aluno.getTelefone());
            _aluno.setMorada(aluno.getMorada());
            _aluno.setDiretor(aluno.getDiretor());
            _aluno.setTurma(aluno.getTurma());
            _aluno.setDisciplinas(aluno.getDisciplinas());


            return alunoRepository.save(_aluno);
        }
        return null;
    }

    public boolean nifExistsExceptCurrent(Long id, String nif) {
        return alunoRepository.existsByNifAndIdNot(nif, id);
    }
    public boolean nifExists(String nif) {
        return alunoRepository.existsByNif(nif);
    }
    public Aluno saveAluno(@Valid Aluno aluno){
        return alunoRepository.save(aluno);
    }


    public List<Aluno> getAllAluno(){
        return alunoRepository.findAll();
    }


    public Optional<Aluno> getAlunoById(Long id){
        Optional<Aluno> aluno = alunoRepository.findById(id);
        return aluno;
    }

    public void deleteAluno(Long id){alunoRepository.deleteById(id);
    }

    public List<Aluno> getAlunoByNome(String keyword){
        return alunoRepository.findByNomeContainingIgnoreCase(keyword);
    }

}
