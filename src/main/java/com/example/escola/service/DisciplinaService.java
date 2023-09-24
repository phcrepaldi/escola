package com.example.escola.service;

import com.example.escola.model.Aluno;
import com.example.escola.model.Disciplina;
import com.example.escola.model.Professor;
import com.example.escola.model.Turma;
import com.example.escola.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    @Autowired
    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina saveDisciplina(Disciplina disciplina){
        Disciplina _disciplina=disciplinaRepository.save(disciplina);
        return _disciplina;
    }

    public List<Disciplina> getAllDisciplinas(){return disciplinaRepository.findAll();}

    public Optional<Disciplina> getDisciplinaById(Long id){
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        return disciplina;
    }

    public List<Disciplina> getDisciplinasByProfessor(Professor professor){
        return disciplinaRepository.findByProfessores(professor);}

    public Disciplina updateDisciplina(Long id, Disciplina disciplina){
        Optional<Disciplina> disciplinaOptional=getDisciplinaById(id);

        if(disciplinaOptional.isPresent()){
            Disciplina _disciplina=disciplinaOptional.get();
            _disciplina.setNomeDisciplina(disciplina.getNomeDisciplina());
            _disciplina.setProgramaDisciplina(disciplina.getProgramaDisciplina());
            _disciplina.setProfessores(disciplina.getProfessores());

            return disciplinaRepository.save(_disciplina);
        }
        return null;
    }

    public void deleteDisciplina(Long id){
        Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(id);

        if (disciplinaOptional.isPresent()) {
            Disciplina disciplina = disciplinaOptional.get();

            List<Professor> professores = disciplina.getProfessores();
            if (professores != null && !professores.isEmpty()) {
                for (Professor professor : professores) {
                    professor.getDisciplinas().remove(disciplina);
                }
            }

            List<Aluno> alunos = disciplina.getAlunos();
            if (alunos != null && !alunos.isEmpty()) {
                for (Aluno aluno : alunos) {
                    aluno.getDisciplinas().remove(disciplina);
                }
            }

            disciplinaRepository.delete(disciplina);
        }
    }
    public boolean disciplinaExists(String nomeDisciplina) {
        return disciplinaRepository.existsByNomeDisciplina(nomeDisciplina);
    }

    public boolean disciplinaExistsExceptCurrent(Long id, String nomeDisciplina) {
        List<Disciplina> existingDisciplinas = disciplinaRepository.findByNomeDisciplinaContainingIgnoreCase(nomeDisciplina);

        for (Disciplina existingDisciplina : existingDisciplinas) {
            if (!existingDisciplina.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    public List<Disciplina> getDisciplinasByNomeDisciplina(String keyword){
        return disciplinaRepository.findByNomeDisciplinaContainingIgnoreCase(keyword);
    }

}
