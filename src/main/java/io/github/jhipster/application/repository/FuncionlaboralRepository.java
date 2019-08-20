package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Funcionlaboral;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Funcionlaboral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuncionlaboralRepository extends JpaRepository<Funcionlaboral, Long> {

}
