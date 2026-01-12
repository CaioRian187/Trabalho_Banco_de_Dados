package com.TrabalhoBD.clinica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TrabalhoBD.clinica.models.Exame;

public interface ExameRepository extends JpaRepository<Exame,Long>{

    List<Exame> findByMedico_id(Long id);
    List<Exame> findByPaciente_id(Long id);
}
