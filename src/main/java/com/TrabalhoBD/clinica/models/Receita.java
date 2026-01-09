package com.TrabalhoBD.clinica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "receitas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name ="data_emissão", nullable = false, length = 255)
    @NotBlank
    private String data_emissao;

    @Column(name = "medicamento", nullable = false, length = 255)
    @NotBlank
    private String medicamento;

    @Column(name = "dosagem", nullable = false, length = 255)
    @NotBlank
    private String dosagem;

    @Column(name = "instrucoes", nullable = false, length = 255)
    @NotBlank
    private String instrucoes;

}
