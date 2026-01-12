package com.TrabalhoBD.clinica.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TrabalhoBD.clinica.models.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, UUID> {
    Optional<Especialidade> findByNome(String nome);
}