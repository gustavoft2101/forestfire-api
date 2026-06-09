package br.com.fiap.globalsolution.service;

import br.com.fiap.globalsolution.dto.*;
import br.com.fiap.globalsolution.exception.*;
import br.com.fiap.globalsolution.model.*;
import br.com.fiap.globalsolution.repository.SolucaoEspacialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolucaoEspacialService {

    private final SolucaoEspacialRepository repository;

    // ============================================================
    // 1. CADASTRAR
    // ============================================================
    public SolucaoResponseDTO cadastrar(SolucaoRequestDTO dto) {
        SolucaoEspacial solucao = toEntity(dto);
        SolucaoEspacial salva = repository.save(solucao);
        return toResponseDTO(salva);
    }

    // ============================================================
    // 2. LISTAR TODAS
    // ============================================================
    public List<SolucaoResponseDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // 3. BUSCAR POR ID
    // ============================================================
    public SolucaoResponseDTO buscarPorId(Long id) {
        SolucaoEspacial solucao = repository.findById(id)
                .orElseThrow(() -> new SolucaoNotFoundException(id));
        return toResponseDTO(solucao);
    }

    // ============================================================
    // 4. BUSCAR POR ÁREA DE IMPACTO
    // ============================================================
    public List<SolucaoResponseDTO> buscarPorAreaImpacto(String areaImpacto) {
        List<SolucaoEspacial> solucoes =
                repository.findByAreaImpactoIgnoreCase(areaImpacto);

        if (solucoes.isEmpty()) {
            throw new SolucaoNotFoundException(
                    "Nenhuma solução encontrada para a área: " + areaImpacto);
        }

        return solucoes.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // 5. ATUALIZAR COMPLETO (PUT)
    // ============================================================
    public SolucaoResponseDTO atualizar(Long id, SolucaoRequestDTO dto) {
        SolucaoEspacial existente = repository.findById(id)
                .orElseThrow(() -> new SolucaoNotFoundException(id));

        if (existente.getStatus() == StatusSolucao.CANCELADA) {
            throw new SolucaoCanceladaException();
        }

        existente.setNome(dto.getNome());
        existente.setDescricao(dto.getDescricao());
        existente.setAreaImpacto(dto.getAreaImpacto());
        existente.setStatus(dto.getStatus());
        existente.setUrgencia(dto.getUrgencia());
        existente.setNivelImpacto(dto.getNivelImpacto());
        existente.setOdsRelacionado(dto.getOdsRelacionado());
        existente.setFonteDados(dto.getFonteDados());
        existente.setRegiaoMonitorada(dto.getRegiaoMonitorada());

        SolucaoEspacial atualizada = repository.save(existente);
        return toResponseDTO(atualizada);
    }

    // ============================================================
    // 6. ALTERAR APENAS O STATUS (PATCH)
    // ============================================================
    public SolucaoResponseDTO atualizarStatus(Long id, StatusUpdateDTO dto) {
        SolucaoEspacial existente = repository.findById(id)
                .orElseThrow(() -> new SolucaoNotFoundException(id));

        if (existente.getStatus() == StatusSolucao.CANCELADA) {
            throw new SolucaoCanceladaException();
        }

        existente.setStatus(dto.getStatus());
        SolucaoEspacial atualizada = repository.save(existente);
        return toResponseDTO(atualizada);
    }

    // ============================================================
    // 7. EXCLUIR
    // ============================================================
    public void excluir(Long id) {
        SolucaoEspacial existente = repository.findById(id)
                .orElseThrow(() -> new SolucaoNotFoundException(id));

        if (existente.getStatus() == StatusSolucao.VALIDADA) {
            throw new SolucaoValidadaException();
        }

        repository.delete(existente);
    }

    // ============================================================
    // 8. LISTAR POR ODS
    // ============================================================
    public List<SolucaoResponseDTO> buscarPorOds(String ods) {
        List<SolucaoEspacial> solucoes =
                repository.findByOdsRelacionadoContainingIgnoreCase(ods);

        if (solucoes.isEmpty()) {
            throw new SolucaoNotFoundException(
                    "Nenhuma solução encontrada para o ODS: " + ods);
        }

        return solucoes.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // 9. RESUMO GERAL
    // ============================================================
    public ResumoDTO gerarResumo() {
        List<SolucaoEspacial> todas = repository.findAll();

        long total = todas.size();

        Map<String, Long> porStatus = todas.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStatus().name(),
                        Collectors.counting()
                ));

        Map<String, Long> porArea = todas.stream()
                .collect(Collectors.groupingBy(
                        SolucaoEspacial::getAreaImpacto,
                        Collectors.counting()
                ));

        List<SolucaoResponseDTO> altaPrioridade = todas.stream()
                .filter(s -> s.getPrioridade() != null && s.getPrioridade() >= 17)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResumoDTO.builder()
                .totalSolucoes(total)
                .quantidadePorStatus(porStatus)
                .quantidadePorAreaImpacto(porArea)
                .solucoesAltaPrioridade(altaPrioridade)
                .build();
    }

    // ============================================================
    // CONVERSORES PRIVADOS (Entity ↔ DTO)
    // ============================================================

    private SolucaoEspacial toEntity(SolucaoRequestDTO dto) {
        return SolucaoEspacial.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .areaImpacto(dto.getAreaImpacto())
                .status(dto.getStatus())
                .urgencia(dto.getUrgencia())
                .nivelImpacto(dto.getNivelImpacto())
                .odsRelacionado(dto.getOdsRelacionado())
                .fonteDados(dto.getFonteDados())
                .regiaoMonitorada(dto.getRegiaoMonitorada())
                .build();
    }

    private SolucaoResponseDTO toResponseDTO(SolucaoEspacial s) {
        return SolucaoResponseDTO.builder()
                .id(s.getId())
                .nome(s.getNome())
                .descricao(s.getDescricao())
                .areaImpacto(s.getAreaImpacto())
                .status(s.getStatus())
                .urgencia(s.getUrgencia())
                .nivelImpacto(s.getNivelImpacto())
                .prioridade(s.getPrioridade())
                .classificacaoPrioridade(classificarPrioridade(s.getPrioridade()))
                .odsRelacionado(s.getOdsRelacionado())
                .fonteDados(s.getFonteDados())
                .regiaoMonitorada(s.getRegiaoMonitorada())
                .dataCadastro(s.getDataCadastro())
                .dataAtualizacao(s.getDataAtualizacao())
                .build();
    }

    private String classificarPrioridade(Integer prioridade) {
        if (prioridade == null) return "NÃO DEFINIDA";
        if (prioridade >= 17) return "ALTA";
        if (prioridade >= 11) return "MÉDIA";
        return "BAIXA";
    }
}
