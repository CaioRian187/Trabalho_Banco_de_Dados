package com.TrabalhoBD.clinica.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.TrabalhoBD.clinica.models.Medico;
import com.TrabalhoBD.clinica.services.MedicoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*") // Adicione isto acima de @RestController
@RestController
@RequestMapping("/medico")
@Validated
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/{id}")
    public ResponseEntity<Medico> findById(@PathVariable Long id){
        Medico medico = this.medicoService.findById(id);
        return ResponseEntity.ok().body(medico);
    } 

    @GetMapping("nome/{nome}")
    public ResponseEntity<Medico> findByNome(@Valid @PathVariable String nome){
        Medico medico = this.medicoService.findByNome(nome);
        return ResponseEntity.ok().body(medico);
    } 

    @GetMapping
    public ResponseEntity<List<Medico>> findAllMedicos(){
        List<Medico> list = this.medicoService.findAllMedicos();

        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> createMedico (@Valid @RequestBody Medico medico){
        this.medicoService.createMedico(medico);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMedico(@Valid @RequestBody Medico medico, @PathVariable Long id){
        medico.setId(id);
        medico = this.medicoService.updateMedico(medico);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id){
        this.medicoService.deleteMedico(id);
        return ResponseEntity.noContent().build();
    }
    
}
