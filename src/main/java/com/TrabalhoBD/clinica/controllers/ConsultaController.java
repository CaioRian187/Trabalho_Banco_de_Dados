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

import com.TrabalhoBD.clinica.models.Consulta;
import com.TrabalhoBD.clinica.services.ConsultaService;
import com.TrabalhoBD.clinica.services.MedicoService;
import com.TrabalhoBD.clinica.services.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/consulta")
@Validated
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> findById(@PathVariable Long id){
        Consulta consulta = this.consultaService.findById(id);
        return ResponseEntity.ok().body(consulta);
    }

    @GetMapping("/medico/{medico_id}")
    public ResponseEntity<List<Consulta>> findAllByMedicoId(@PathVariable Long medico_id){
        this.medicoService.findById(medico_id);
        List<Consulta> consultas = this.consultaService.findAllByMedicoId(medico_id);
        return ResponseEntity.ok().body(consultas);
    }

    @GetMapping("/paciente/{paciente_id}")
    public ResponseEntity<List<Consulta>> findAllByPacienteId(@PathVariable Long paciente_id){
        this.pacienteService.findById(paciente_id);
        List<Consulta> consultas = this.consultaService.findAllByPacienteId(paciente_id);
        return ResponseEntity.ok().body(consultas);
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> findAll(){
        List<Consulta> list = this.consultaService.findAll();

        return ResponseEntity.ok().body(list);
    }

    @PostMapping()
    public ResponseEntity<Void> createConsulta(@Valid @RequestBody Consulta consulta){
        this.consultaService.createConsulta(consulta);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(consulta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateConsulta(@Valid @RequestBody Consulta consulta, @PathVariable Long id){
        consulta.setId(id);
        consulta = this.consultaService.updateConsulta(consulta);
        return ResponseEntity.noContent().build();
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsulta(@PathVariable Long id){
        this.consultaService.deleteConsulta(id);
        return ResponseEntity.noContent().build();
    }
}
