package br.com.fiap.globalsolution.config;

import br.com.fiap.globalsolution.model.SolucaoEspacial;
import br.com.fiap.globalsolution.model.StatusSolucao;
import br.com.fiap.globalsolution.repository.SolucaoEspacialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SolucaoEspacialRepository repository;

    @Override
    public void run(String... args) throws Exception {

        if (repository.count() == 0) {

            repository.save(SolucaoEspacial.builder()
                    .nome("SentinelFire - Detecção por Satélite")
                    .descricao("Sistema de detecção precoce de focos de incêndio " +
                            "na Amazônia usando imagens do satélite Sentinel-2 " +
                            "com análise de temperatura e vegetação.")
                    .areaImpacto("Amazônia")
                    .status(StatusSolucao.EM_OPERACAO)
                    .urgencia(5)
                    .nivelImpacto(5)
                    .odsRelacionado("ODS 15 - Vida Terrestre")
                    .fonteDados("ESA Sentinel-2 / INPE BDQueimadas")
                    .regiaoMonitorada("Pará - PA")
                    .build());

            repository.save(SolucaoEspacial.builder()
                    .nome("CerradoWatch - Monitoramento Térmico")
                    .descricao("Plataforma de monitoramento térmico do Cerrado " +
                            "utilizando dados MODIS da NASA para identificar " +
                            "variações anômalas de temperatura.")
                    .areaImpacto("Cerrado")
                    .status(StatusSolucao.VALIDADA)
                    .urgencia(4)
                    .nivelImpacto(4)
                    .odsRelacionado("ODS 13 - Ação Climática")
                    .fonteDados("NASA FIRMS / MODIS")
                    .regiaoMonitorada("Mato Grosso - MT")
                    .build());

            repository.save(SolucaoEspacial.builder()
                    .nome("PantanalAlert - Prevenção de Queimadas")
                    .descricao("Solução integrada de alertas para prevenção de " +
                            "queimadas no Pantanal com dados combinados de " +
                            "múltiplos satélites e estações meteorológicas.")
                    .areaImpacto("Pantanal")
                    .status(StatusSolucao.EM_DESENVOLVIMENTO)
                    .urgencia(5)
                    .nivelImpacto(4)
                    .odsRelacionado("ODS 15 - Vida Terrestre")
                    .fonteDados("INPE / NOAA")
                    .regiaoMonitorada("Mato Grosso do Sul - MS")
                    .build());

            repository.save(SolucaoEspacial.builder()
                    .nome("AtlânticaGuard - Proteção da Mata Atlântica")
                    .descricao("Sistema de proteção e monitoramento da Mata " +
                            "Atlântica com foco em detecção de desmatamento " +
                            "e risco de incêndio em fragmentos florestais.")
                    .areaImpacto("Mata Atlântica")
                    .status(StatusSolucao.SUSPENSA)
                    .urgencia(3)
                    .nivelImpacto(3)
                    .odsRelacionado("ODS 15 - Vida Terrestre")
                    .fonteDados("Landsat 8 / USGS")
                    .regiaoMonitorada("São Paulo - SP")
                    .build());

            repository.save(SolucaoEspacial.builder()
                    .nome("CaatingaMonitor - Semiárido")
                    .descricao("Monitoramento de risco de incêndio na Caatinga " +
                            "durante períodos de seca extrema usando índices " +
                            "de vegetação NDVI de satélites.")
                    .areaImpacto("Caatinga")
                    .status(StatusSolucao.EM_DESENVOLVIMENTO)
                    .urgencia(3)
                    .nivelImpacto(2)
                    .odsRelacionado("ODS 13 - Ação Climática")
                    .fonteDados("CBERS-4A / INPE")
                    .regiaoMonitorada("Bahia - BA")
                    .build());

            System.out.println("✅ Dados de exemplo carregados com sucesso!");
        }
    }
}
