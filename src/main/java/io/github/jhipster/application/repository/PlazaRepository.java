package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Plaza;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Plaza entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlazaRepository extends JpaRepository<Plaza, Long> {

}
