package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Motivomov;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Motivomov entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotivomovRepository extends JpaRepository<Motivomov, Long> {

}
