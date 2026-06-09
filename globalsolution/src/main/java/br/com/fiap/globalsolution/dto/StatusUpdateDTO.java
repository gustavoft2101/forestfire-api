package br.com.fiap.globalsolution.dto;

import br.com.fiap.globalsolution.model.StatusSolucao;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDTO {

    @NotNull(message = "Status é obrigatório")
    private StatusSolucao status;
}
