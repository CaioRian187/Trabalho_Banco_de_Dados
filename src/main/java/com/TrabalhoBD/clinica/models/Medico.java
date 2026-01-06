package com.TrabalhoBD.clinica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="medico")
@Table(name="medicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true)
    private Long id;

    @Column(name="nome", unique = true)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(name = "crm" , nullable = false, length = 255)
    @NotBlank(message = "CRM é obrigatório")
    private String crm;

    @Column(name = "especialidade" , nullable = false, length = 255)
    @NotBlank(message = "Especialidade é obrigatório")
    private String especialidade;
    
}
