package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Ciclo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ciclo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CicloRepository extends JpaRepository<Ciclo, Long> {

}
