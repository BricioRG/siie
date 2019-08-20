package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Rol entity.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    @Query(value = "select distinct rol from Rol rol left join fetch rol.funcions",
        countQuery = "select count(distinct rol) from Rol rol")
    Page<Rol> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct rol from Rol rol left join fetch rol.funcions")
    List<Rol> findAllWithEagerRelationships();

    @Query("select rol from Rol rol left join fetch rol.funcions where rol.id =:id")
    Optional<Rol> findOneWithEagerRelationships(@Param("id") Long id);

}
