package com.TrabalhoBD.clinica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TrabalhoBD.clinica.models.Medico;
import com.TrabalhoBD.clinica.services.MedicoService;

@RestController
@RequestMapping("/medico")

public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/{id}")
    public ResponseEntity<Medico> findById(@PathVariable Long id){
        Medico medico = this.medicoService.findById(id);
        return ResponseEntity.ok().body(medico);
    } 

    @GetMapping
    public ResponseEntity<List<Medico>> findAllMedicos(){
        List<Medico> list = this.medicoService.findAllMedicos();

        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> createMedico (@RequestBody Medico medico){
        this.medicoService.createMedico(medico);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMedico(@RequestBody Medico medico, @PathVariable Long id){
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
