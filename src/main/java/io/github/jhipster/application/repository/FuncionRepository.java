package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Funcion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Funcion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Long> {

}
