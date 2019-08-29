package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Horariolab;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Horariolab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorariolabRepository extends JpaRepository<Horariolab, Long> {

}
