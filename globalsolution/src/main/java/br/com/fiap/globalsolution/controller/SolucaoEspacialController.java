package br.com.fiap.globalsolution.controller;

import br.com.fiap.globalsolution.dto.*;
import br.com.fiap.globalsolution.service.SolucaoEspacialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solucoes")
@RequiredArgsConstructor
public class SolucaoEspacialController {

    private final SolucaoEspacialService service;

    // ============================================================
    // 1. CADASTRAR — POST /api/solucoes
    // ============================================================
    @PostMapping
    public ResponseEntity<SolucaoResponseDTO> cadastrar(
            @RequestBody @Valid SolucaoRequestDTO dto) {

        SolucaoResponseDTO resposta = service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    // ============================================================
    // 2. LISTAR TODAS — GET /api/solucoes
    // ============================================================
    @GetMapping
    public ResponseEntity<List<SolucaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // ============================================================
    // 3. BUSCAR POR ID — GET /api/solucoes/{id}
    // ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<SolucaoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // ============================================================
    // 4. BUSCAR POR ÁREA DE IMPACTO — GET /api/solucoes/area?nome=Amazonia
    // ============================================================
    @GetMapping("/area")
    public ResponseEntity<List<SolucaoResponseDTO>> buscarPorArea(
            @RequestParam String nome) {

        return ResponseEntity.ok(service.buscarPorAreaImpacto(nome));
    }

    // ============================================================
    // 5. ATUALIZAR COMPLETO — PUT /api/solucoes/{id}
    // ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<SolucaoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SolucaoRequestDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // ============================================================
    // 6. ALTERAR APENAS STATUS — PATCH /api/solucoes/{id}/status
    // ============================================================
    @PatchMapping("/{id}/status")
    public ResponseEntity<SolucaoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid StatusUpdateDTO dto) {

        return ResponseEntity.ok(service.atualizarStatus(id, dto));
    }

    // ============================================================
    // 7. EXCLUIR — DELETE /api/solucoes/{id}
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // 8. LISTAR POR ODS — GET /api/solucoes/ods?nome=ODS 15
    // ============================================================
    @GetMapping("/ods")
    public ResponseEntity<List<SolucaoResponseDTO>> buscarPorOds(
            @RequestParam String nome) {

        return ResponseEntity.ok(service.buscarPorOds(nome));
    }

    // ============================================================
    // 9. RESUMO GERAL — GET /api/solucoes/resumo
    // ============================================================
    @GetMapping("/resumo")
    public ResponseEntity<ResumoDTO> resumo() {
        return ResponseEntity.ok(service.gerarResumo());
    }
}
