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

import com.TrabalhoBD.clinica.models.Exame;
import com.TrabalhoBD.clinica.services.ExameService;
import com.TrabalhoBD.clinica.services.MedicoService;
import com.TrabalhoBD.clinica.services.PacienteService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/exame")
@Validated
public class ExameController {
    
    @Autowired
    private ExameService exameService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Exame> findById(@PathVariable Long id){
        Exame exame = this.exameService.findById(id);
        return ResponseEntity.ok().body(exame);
    }


    @GetMapping("/medico/{medico_id}")
    public ResponseEntity<List<Exame>> findByMedicoId(@PathVariable Long medico_id){
        this.medicoService.findById(medico_id);

        List<Exame> exames = this.exameService.findByMedicoid(medico_id);
        return ResponseEntity.ok().body(exames);
    }

    @GetMapping("/paciente/{paciente_id}")
    public ResponseEntity<List<Exame>> findByPacienteId(@PathVariable Long paciente_id){
        this.pacienteService.findById(paciente_id);
        List<Exame> exames = this.exameService.findByPacienteId(paciente_id);
        return ResponseEntity.ok().body(exames);
    }

    @GetMapping
    public ResponseEntity<List<Exame>> findAll(){
        List<Exame> list = this.exameService.findAll();
        return ResponseEntity.ok().body(list);
    }


    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Exame exame){
        this.exameService.create(exame);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(exame.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Exame exame, @PathVariable Long id){
        exame.setId(id);
        exame = this.exameService.update(exame);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.exameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
