package br.com.fiap.globalsolution.repository;

import br.com.fiap.globalsolution.model.SolucaoEspacial;
import br.com.fiap.globalsolution.model.StatusSolucao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolucaoEspacialRepository extends JpaRepository<SolucaoEspacial, Long> {

    List<SolucaoEspacial> findByAreaImpactoIgnoreCase(String areaImpacto);

    List<SolucaoEspacial> findByStatus(StatusSolucao status);

    List<SolucaoEspacial> findByOdsRelacionadoContainingIgnoreCase(String odsRelacionado);

    List<SolucaoEspacial> findByPrioridadeGreaterThanEqual(Integer prioridade);

    Long countByStatus(StatusSolucao status);

    Long countByAreaImpactoIgnoreCase(String areaImpacto);

    @Query("SELECT DISTINCT s.areaImpacto FROM SolucaoEspacial s")
    List<String> findDistinctAreaImpacto();

    @Query("SELECT DISTINCT s.odsRelacionado FROM SolucaoEspacial s")
    List<String> findDistinctOdsRelacionado();
}
