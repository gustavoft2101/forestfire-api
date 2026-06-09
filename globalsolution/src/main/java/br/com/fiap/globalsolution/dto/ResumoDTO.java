package br.com.fiap.globalsolution.dto;

import lombok.*;
import java.util.Map;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumoDTO {

    private Long totalSolucoes;

    private Map<String, Long> quantidadePorStatus;

    private Map<String, Long> quantidadePorAreaImpacto;

    private List<SolucaoResponseDTO> solucoesAltaPrioridade;
}
