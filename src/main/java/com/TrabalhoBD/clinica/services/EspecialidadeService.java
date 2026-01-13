package com.TrabalhoBD.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TrabalhoBD.clinica.exceptions.NotFoundException;
import com.TrabalhoBD.clinica.models.Especialidade;
import com.TrabalhoBD.clinica.repositories.EspecialidadeRepository;

@Service
public class EspecialidadeService {
    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public Especialidade findById(long id){
        Optional<Especialidade> especialidade = this.especialidadeRepository.findById(id);
        return especialidade.orElseThrow(() -> new NotFoundException("Especialidade de id = " + id + " não encontrada"));
    }

    public Especialidade findByNome(String nome){
        return especialidadeRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Especialidade não encontrada"));
    }

    public List<Especialidade> findAll(){
        List<Especialidade> list = this.especialidadeRepository.findAll();
        if (list.isEmpty()){
            throw new NotFoundException("Nenhuma especialidade encontrada");
        }
        return list;
    }

    @Transactional
    public void create(Especialidade especialidade){
        this.especialidadeRepository.save(especialidade);
    }

    @Transactional
    public Especialidade update(Especialidade especialidade){
        Especialidade newEspecialidade = findById(especialidade.getId());
        newEspecialidade.setNome(especialidade.getNome());
        return this.especialidadeRepository.save(newEspecialidade);
    }

    public void delete(long id){
        findById(id);
        try {
            this.especialidadeRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("Não é possível excluir, pois a especialidade possui vinculações");
        }
    }
}