package com.TrabalhoBD.clinica.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.TrabalhoBD.clinica.models.Paciente;
import com.TrabalhoBD.clinica.services.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/paciente")
@Validated
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> findById(@PathVariable Long id){
        Paciente paciente = this.pacienteService.findById(id);

        return ResponseEntity.ok().body(paciente);
    }

    @GetMapping("nome/{nome}")
    public ResponseEntity<Paciente> findByNome(@Valid @PathVariable String nome){
        Paciente paciente = this.pacienteService.findByNome(nome);

        return ResponseEntity.ok().body(paciente);
    } 

    @GetMapping
    public ResponseEntity<List<Paciente>> findAll(){
        List<Paciente> list = this.pacienteService.findAll();

        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> createPaciente(@Valid @RequestBody Paciente paciente){
        this.pacienteService.createPaciente(paciente);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(@Valid @RequestBody Paciente paciente, @PathVariable Long id){
        paciente.setId(id);
        paciente = this.pacienteService.updatePaciente(paciente);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id){
        this.pacienteService.deletePaciente(id);
        return ResponseEntity.noContent().build();
    }
}
