package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Jornada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Jornada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {

}
