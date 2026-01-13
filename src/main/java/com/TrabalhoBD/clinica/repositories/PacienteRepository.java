package com.TrabalhoBD.clinica.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.TrabalhoBD.clinica.models.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    Optional<Paciente> findByNome(String nome);

    // 1. LIKE + WHERE: Busca pacientes que contenham "parte" do nome (Ignorando maiúscula/minúscula)
    List<Paciente> findByNomeContainingIgnoreCase(String nome);

    // 2. MIN: Busca a data de nascimento do paciente mais velho
    @Query("SELECT MIN(p.dataNascimento) FROM Paciente p")
    LocalDate findDataNascimentoMaisAntiga();
}