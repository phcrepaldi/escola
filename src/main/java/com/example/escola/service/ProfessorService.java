package com.example.escola.service;

import com.example.escola.model.Disciplina;
import com.example.escola.model.Professor;
import com.example.escola.repository.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    private ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor saveProfessor(@Valid Professor professor) {
        return professorRepository.save(professor);
    }

    public List<Professor> getallProfessores() {return professorRepository.findAll();}

    public void deleteProfessor(Long id){professorRepository.deleteById(id);}

    public Optional<Professor> getProfessorById(Long id){
        Optional<Professor> professor=professorRepository.findById(id);
        return professor;
    }

    public Professor updateProfessor(Long id, Professor professor){
        Optional<Professor> professorOptional=getProfessorById(id);
        if(professorOptional.isPresent()){
            Professor _professor=professorOptional.get();
            _professor.setNome(professor.getNome());
            _professor.setApelido(professor.getApelido());
            _professor.setDatanas(professor.getDatanas());
            _professor.setGenero(professor.getGenero());
            _professor.setEmail(professor.getEmail());
            _professor.setNif(professor.getNif());
            _professor.setTelefone(professor.getTelefone());
            _professor.setMorada(professor.getMorada());
            _professor.setSalario(professor.getSalario());
            _professor.setDisciplinas(professor.getDisciplinas());

            return professorRepository.save(_professor);
        }
        return null;
    }

    public List<Professor> getProfessoresByDisciplina(Disciplina disciplina){
        return professorRepository.findByDisciplinas(disciplina);}

    public List<Professor> getProfessoresByNome(String keyword){
        return professorRepository.findByNomeContainingIgnoreCase(keyword);
    }

}
