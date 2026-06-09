package br.com.fiap.globalsolution.dto;

import br.com.fiap.globalsolution.model.StatusSolucao;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolucaoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String areaImpacto;
    private StatusSolucao status;
    private Integer urgencia;
    private Integer nivelImpacto;
    private Integer prioridade;
    private String classificacaoPrioridade;
    private String odsRelacionado;
    private String fonteDados;
    private String regiaoMonitorada;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}
