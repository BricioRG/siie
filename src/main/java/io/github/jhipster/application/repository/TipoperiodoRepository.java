package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Tipoperiodo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tipoperiodo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoperiodoRepository extends JpaRepository<Tipoperiodo, Long> {

}
