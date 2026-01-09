package com.TrabalhoBD.clinica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TrabalhoBD.clinica.models.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

    List<Consulta> findByMedico_id(Long id);
    List<Consulta> findByPaciente_id(Long id);

}
