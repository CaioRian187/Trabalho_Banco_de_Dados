package com.TrabalhoBD.clinica.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank()
    private String nome;

    @Column(name = "crm" , nullable = false, length = 255)
    @NotBlank()
    private String crm;

    @Column(name = "especialidade" , nullable = false, length = 255)
    @NotBlank()
    private String especialidade;
    
    @OneToMany(mappedBy = "medico") 
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Consulta> consultas = new ArrayList<Consulta>();
}
