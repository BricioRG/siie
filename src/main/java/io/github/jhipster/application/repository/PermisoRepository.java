package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Permiso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Permiso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {

}
