package com.TrabalhoBD.clinica.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TrabalhoBD.clinica.models.Consulta;
import com.TrabalhoBD.clinica.models.Paciente;
import com.TrabalhoBD.clinica.repositories.ConsultaRepository;
import com.TrabalhoBD.clinica.repositories.PacienteRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    // 1. LIKE: Buscar pacientes por parte do nome
    @GetMapping("/pacientes/busca")
    public ResponseEntity<List<Paciente>> buscarPacientesPorParteNome(@RequestParam String termo) {
        List<Paciente> list = pacienteRepository.findByNomeContainingIgnoreCase(termo);
        return ResponseEntity.ok(list);
    }

    // 2. MIN: Data de nascimento do paciente mais velho
    @GetMapping("/pacientes/mais-idoso")
    public ResponseEntity<LocalDate> dataNascimentoMaisAntiga() {
        LocalDate data = pacienteRepository.findDataNascimentoMaisAntiga();
        return ResponseEntity.ok(data);
    }

    // 3. COUNT: Total de consultas de um médico
    @GetMapping("/medico/{id}/total-consultas")
    public ResponseEntity<Long> contarConsultasMedico(@PathVariable Long id) {
        Long total = consultaRepository.countConsultasPorMedico(id);
        return ResponseEntity.ok(total);
    }

    // 4. MAX: Data da última consulta agendada no sistema
    @GetMapping("/consultas/ultima-data")
    public ResponseEntity<LocalDateTime> ultimaDataConsulta() {
        LocalDateTime data = consultaRepository.findUltimaConsultaData();
        return ResponseEntity.ok(data);
    }

    // 5. JOIN: Consultas de uma especialidade específica (ex: Cardiologia)
    @GetMapping("/consultas/por-especialidade")
    public ResponseEntity<List<Consulta>> buscarPorEspecialidade(@RequestParam String nome) {
        List<Consulta> list = consultaRepository.findByEspecialidadeNome(nome);
        return ResponseEntity.ok(list);
    }
}