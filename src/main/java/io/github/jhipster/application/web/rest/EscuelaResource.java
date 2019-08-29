package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Escuela;
import io.github.jhipster.application.repository.EscuelaRepository;
import io.github.jhipster.application.repository.search.EscuelaSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Escuela}.
 */
@RestController
@RequestMapping("/api")
public class EscuelaResource {

    private final Logger log = LoggerFactory.getLogger(EscuelaResource.class);

    private static final String ENTITY_NAME = "escuela";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EscuelaRepository escuelaRepository;

    private final EscuelaSearchRepository escuelaSearchRepository;

    public EscuelaResource(EscuelaRepository escuelaRepository, EscuelaSearchRepository escuelaSearchRepository) {
        this.escuelaRepository = escuelaRepository;
        this.escuelaSearchRepository = escuelaSearchRepository;
    }

    /**
     * {@code POST  /escuelas} : Create a new escuela.
     *
     * @param escuela the escuela to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new escuela, or with status {@code 400 (Bad Request)} if the escuela has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/escuelas")
    public ResponseEntity<Escuela> createEscuela(@RequestBody Escuela escuela) throws URISyntaxException {
        log.debug("REST request to save Escuela : {}", escuela);
        if (escuela.getId() != null) {
            throw new BadRequestAlertException("A new escuela cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Escuela result = escuelaRepository.save(escuela);
        escuelaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/escuelas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /escuelas} : Updates an existing escuela.
     *
     * @param escuela the escuela to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escuela,
     * or with status {@code 400 (Bad Request)} if the escuela is not valid,
     * or with status {@code 500 (Internal Server Error)} if the escuela couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/escuelas")
    public ResponseEntity<Escuela> updateEscuela(@RequestBody Escuela escuela) throws URISyntaxException {
        log.debug("REST request to update Escuela : {}", escuela);
        if (escuela.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Escuela result = escuelaRepository.save(escuela);
        escuelaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, escuela.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /escuelas} : get all the escuelas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of escuelas in body.
     */
    @GetMapping("/escuelas")
    public List<Escuela> getAllEscuelas() {
        log.debug("REST request to get all Escuelas");
        return escuelaRepository.findAll();
    }

    /**
     * {@code GET  /escuelas/:id} : get the "id" escuela.
     *
     * @param id the id of the escuela to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the escuela, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/escuelas/{id}")
    public ResponseEntity<Escuela> getEscuela(@PathVariable Long id) {
        log.debug("REST request to get Escuela : {}", id);
        Optional<Escuela> escuela = escuelaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(escuela);
    }

    /**
     * {@code DELETE  /escuelas/:id} : delete the "id" escuela.
     *
     * @param id the id of the escuela to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/escuelas/{id}")
    public ResponseEntity<Void> deleteEscuela(@PathVariable Long id) {
        log.debug("REST request to delete Escuela : {}", id);
        escuelaRepository.deleteById(id);
        escuelaSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/escuelas?query=:query} : search for the escuela corresponding
     * to the query.
     *
     * @param query the query of the escuela search.
     * @return the result of the search.
     */
    @GetMapping("/_search/escuelas")
    public List<Escuela> searchEscuelas(@RequestParam String query) {
        log.debug("REST request to search Escuelas for query {}", query);
        return StreamSupport
            .stream(escuelaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
