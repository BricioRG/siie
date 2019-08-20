package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Rol;
import io.github.jhipster.application.repository.RolRepository;
import io.github.jhipster.application.repository.search.RolSearchRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Rol}.
 */
@RestController
@RequestMapping("/api")
public class RolResource {

    private final Logger log = LoggerFactory.getLogger(RolResource.class);

    private static final String ENTITY_NAME = "rol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RolRepository rolRepository;

    private final RolSearchRepository rolSearchRepository;

    public RolResource(RolRepository rolRepository, RolSearchRepository rolSearchRepository) {
        this.rolRepository = rolRepository;
        this.rolSearchRepository = rolSearchRepository;
    }

    /**
     * {@code POST  /rols} : Create a new rol.
     *
     * @param rol the rol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rol, or with status {@code 400 (Bad Request)} if the rol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rols")
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) throws URISyntaxException {
        log.debug("REST request to save Rol : {}", rol);
        if (rol.getId() != null) {
            throw new BadRequestAlertException("A new rol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rol result = rolRepository.save(rol);
        rolSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rols} : Updates an existing rol.
     *
     * @param rol the rol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rol,
     * or with status {@code 400 (Bad Request)} if the rol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rols")
    public ResponseEntity<Rol> updateRol(@RequestBody Rol rol) throws URISyntaxException {
        log.debug("REST request to update Rol : {}", rol);
        if (rol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rol result = rolRepository.save(rol);
        rolSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rol.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rols} : get all the rols.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rols in body.
     */
    @GetMapping("/rols")
    public List<Rol> getAllRols(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Rols");
        return rolRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /rols/:id} : get the "id" rol.
     *
     * @param id the id of the rol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rols/{id}")
    public ResponseEntity<Rol> getRol(@PathVariable Long id) {
        log.debug("REST request to get Rol : {}", id);
        Optional<Rol> rol = rolRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(rol);
    }

    /**
     * {@code DELETE  /rols/:id} : delete the "id" rol.
     *
     * @param id the id of the rol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rols/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        log.debug("REST request to delete Rol : {}", id);
        rolRepository.deleteById(id);
        rolSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/rols?query=:query} : search for the rol corresponding
     * to the query.
     *
     * @param query the query of the rol search.
     * @return the result of the search.
     */
    @GetMapping("/_search/rols")
    public List<Rol> searchRols(@RequestParam String query) {
        log.debug("REST request to search Rols for query {}", query);
        return StreamSupport
            .stream(rolSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
