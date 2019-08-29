package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Modulo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Modulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {

}
