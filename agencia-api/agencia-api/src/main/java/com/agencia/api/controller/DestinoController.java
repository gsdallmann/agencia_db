package com.agencia.api.controller;

import com.agencia.api.dto.DestinoDTO;
import com.agencia.api.model.Destino;
import com.agencia.api.service.DestinoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/destinos")
@Validated
public class DestinoController {

    private final DestinoService destinoService;

    public DestinoController(DestinoService destinoService) {
        this.destinoService = destinoService;
    }

    // Apenas ADMIN pode cadastrar destinos
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Destino> cadastrarDestino(@Valid @RequestBody DestinoDTO destinoDTO) {
        Destino destino = new Destino(null, destinoDTO.getNome(), destinoDTO.getLocalizacao(), destinoDTO.getDescricao());
        Destino novoDestino = destinoService.cadastrarDestino(destino);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDestino);
    }


    // Acesso público para listar destinos
    @GetMapping
    public ResponseEntity<List<Destino>> listarDestinos() {
        return ResponseEntity.ok(destinoService.listarDestinos());
    }

    // Acesso público para pesquisar destinos
    @GetMapping("/pesquisa")
    public ResponseEntity<List<Destino>> pesquisarDestinos(@RequestParam String termo) {
        return ResponseEntity.ok(destinoService.pesquisarPorTermo(termo));
    }

    // Acesso público para visualizar um destino por ID
    @GetMapping("/{id}")
    public ResponseEntity<Destino> buscarDestinoPorId(@PathVariable Long id) {
        return destinoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // Apenas USER pode avaliar destinos
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<Void> avaliarDestino(@PathVariable Long id, @RequestParam double nota) {
        if (nota < 0 || nota > 10) {
            return ResponseEntity.badRequest().build();
        }
        if (destinoService.avaliarDestino(id, nota)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Apenas ADMIN pode excluir destinos
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDestino(@PathVariable Long id) {
        if (destinoService.excluirDestino(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}