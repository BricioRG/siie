package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Financiamiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Financiamiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanciamientoRepository extends JpaRepository<Financiamiento, Long> {

}
