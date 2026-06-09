package br.com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_SOLUCAO_ESPACIAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolucaoEspacial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @NotBlank(message = "Área de impacto é obrigatória")
    @Column(name = "area_impacto", nullable = false, length = 100)
    private String areaImpacto;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSolucao status;

    @NotNull(message = "Nível de urgência é obrigatório")
    @Min(value = 1, message = "Urgência mínima é 1")
    @Max(value = 5, message = "Urgência máxima é 5")
    @Column(name = "urgencia", nullable = false)
    private Integer urgencia;

    @NotNull(message = "Nível de impacto é obrigatório")
    @Min(value = 1, message = "Impacto mínimo é 1")
    @Max(value = 5, message = "Impacto máximo é 5")
    @Column(name = "nivel_impacto", nullable = false)
    private Integer nivelImpacto;

    @Column(name = "prioridade", nullable = false)
    private Integer prioridade;

    @NotBlank(message = "ODS relacionado é obrigatório")
    @Column(name = "ods_relacionado", nullable = false, length = 100)
    private String odsRelacionado;

    @Column(name = "fonte_dados", length = 200)
    private String fonteDados;

    @Column(name = "regiao_monitorada", length = 200)
    private String regiaoMonitorada;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Calcula prioridade automaticamente antes de salvar
    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        calcularPrioridade();
    }

    // Atualiza data e recalcula prioridade antes de atualizar
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
        calcularPrioridade();
    }

    // Regra de negócio: prioridade = urgencia * nivelImpacto
    // Resultado entre 1-10 = BAIXA, 11-16 = MEDIA, 17-25 = ALTA
    private void calcularPrioridade() {
        if (this.urgencia != null && this.nivelImpacto != null) {
            this.prioridade = this.urgencia * this.nivelImpacto;
        }
    }
}
