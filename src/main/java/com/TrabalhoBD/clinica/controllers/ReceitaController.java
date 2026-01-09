package com.TrabalhoBD.clinica.controllers;

import java.net.URI;
import java.util.List;

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

import com.TrabalhoBD.clinica.models.Receita;
import com.TrabalhoBD.clinica.services.ReceitaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/receita")
@Validated
public class ReceitaController {
    
    private ReceitaService receitaService;

    @GetMapping("/{id}")
    public ResponseEntity<Receita> findById(@PathVariable Long id){
        Receita receita = this.receitaService.findById(id);
        return ResponseEntity.ok().body(receita);
    }

    @GetMapping
    public ResponseEntity<List<Receita>> findAllReceitas(){
        List<Receita> list = this.receitaService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> createReceita(@Valid @RequestBody Receita receita){
        this.receitaService.create(receita);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(receita.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReceita(@Valid @RequestBody Receita receita, @PathVariable Long id){
        receita.setId(id);
        receita = this.receitaService.update(receita);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceita(@PathVariable Long id){
        this.receitaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
