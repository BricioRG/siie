package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Subciclo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Subciclo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcicloRepository extends JpaRepository<Subciclo, Long> {

}
