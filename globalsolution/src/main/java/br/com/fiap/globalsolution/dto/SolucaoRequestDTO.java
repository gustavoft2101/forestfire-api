package br.com.fiap.globalsolution.dto;

import br.com.fiap.globalsolution.model.StatusSolucao;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolucaoRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    private String descricao;

    @NotBlank(message = "Área de impacto é obrigatória")
    private String areaImpacto;

    @NotNull(message = "Status é obrigatório")
    private StatusSolucao status;

    @NotNull(message = "Nível de urgência é obrigatório")
    @Min(value = 1, message = "Urgência mínima é 1")
    @Max(value = 5, message = "Urgência máxima é 5")
    private Integer urgencia;

    @NotNull(message = "Nível de impacto é obrigatório")
    @Min(value = 1, message = "Impacto mínimo é 1")
    @Max(value = 5, message = "Impacto máximo é 5")
    private Integer nivelImpacto;

    @NotBlank(message = "ODS relacionado é obrigatório")
    private String odsRelacionado;

    private String fonteDados;

    private String regiaoMonitorada;
}
