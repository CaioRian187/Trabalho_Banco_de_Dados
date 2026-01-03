package com.TrabalhoBD.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TrabalhoBD.clinica.models.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{
    
}
