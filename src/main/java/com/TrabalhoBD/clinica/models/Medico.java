package com.TrabalhoBD.clinica.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_medico", unique = true)
    private Long id;

    @Column(name = "nome_medico", nullable = false, length = 255)
    @NotBlank
    private String nome;

    @Column(name = "crm" , nullable = false, length = 255)
    @NotBlank
    private String crm;

    @Column(name = "telefone", nullable = false, length = 20)
    @NotBlank
    private String telefone;

    @ManyToMany
    @JoinTable(
            name = "medico_especialidade",
            joinColumns = @JoinColumn(name = "id_medico"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade")
    )
    private Set<Especialidade> especialidades = new HashSet<>();
    
    @OneToMany(mappedBy = "medico") 
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Consulta> consultas = new ArrayList<Consulta>();

    @OneToMany(mappedBy = "medico")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Exame> exames = new ArrayList<Exame>();
    
}
