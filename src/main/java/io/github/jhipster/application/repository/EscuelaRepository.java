package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Escuela;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Escuela entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscuelaRepository extends JpaRepository<Escuela, Long> {

}
