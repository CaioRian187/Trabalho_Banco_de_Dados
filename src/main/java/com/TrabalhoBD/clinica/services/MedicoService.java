package com.TrabalhoBD.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.dao.DataIntegrityViolationException;

import com.TrabalhoBD.clinica.exceptions.NotFoundException;
import com.TrabalhoBD.clinica.models.Medico;
import com.TrabalhoBD.clinica.repositories.MedicoRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Medico findById(Long id){
        Optional<Medico> medico = this.medicoRepository.findById(id);
        return medico.orElseThrow(() -> new NotFoundException("Médico não encontrado"));
    }

    public Medico findByNome(String nome){
        return medicoRepository.findByNome(nome).orElseThrow(() -> new NotFoundException("Médico não encontrado"));
    }

    public List<Medico> findAllMedicos (){
        List<Medico> list = this.medicoRepository.findAll();
        if (list.isEmpty()){
            throw new NotFoundException("Médico não encontrado");
        }
        return list;
    }

    @Transactional
    public void createMedico(Medico medico){

        this.medicoRepository.save(medico);
    }

    @Transactional
    public Medico updateMedico(Medico medico){
        Medico newMedico = findById(medico.getId());

        newMedico.setNome(medico.getNome());
        newMedico.setCrm(medico.getCrm());
        newMedico.setEspecialidade(medico.getEspecialidade());

        return this.medicoRepository.save(newMedico);
    }

    public void deleteMedico(Long id){
        findById(id);

        try {
            this.medicoRepository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException exception) {
            throw new com.TrabalhoBD.clinica.exceptions.DataIntegrityViolationException("Não é possível excluir, pois o médico possui vinculações");
        }
    }


}
