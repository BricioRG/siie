package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Tipomov;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tipomov entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipomovRepository extends JpaRepository<Tipomov, Long> {

}
