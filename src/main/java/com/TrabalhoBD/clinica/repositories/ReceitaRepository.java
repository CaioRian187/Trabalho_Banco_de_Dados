package com.TrabalhoBD.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TrabalhoBD.clinica.models.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{
    
}
