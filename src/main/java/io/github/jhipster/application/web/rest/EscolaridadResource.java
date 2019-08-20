package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Escolaridad;
import io.github.jhipster.application.repository.EscolaridadRepository;
import io.github.jhipster.application.repository.search.EscolaridadSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Escolaridad}.
 */
@RestController
@RequestMapping("/api")
public class EscolaridadResource {

    private final Logger log = LoggerFactory.getLogger(EscolaridadResource.class);

    private static final String ENTITY_NAME = "escolaridad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EscolaridadRepository escolaridadRepository;

    private final EscolaridadSearchRepository escolaridadSearchRepository;

    public EscolaridadResource(EscolaridadRepository escolaridadRepository, EscolaridadSearchRepository escolaridadSearchRepository) {
        this.escolaridadRepository = escolaridadRepository;
        this.escolaridadSearchRepository = escolaridadSearchRepository;
    }

    /**
     * {@code POST  /escolaridads} : Create a new escolaridad.
     *
     * @param escolaridad the escolaridad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new escolaridad, or with status {@code 400 (Bad Request)} if the escolaridad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/escolaridads")
    public ResponseEntity<Escolaridad> createEscolaridad(@RequestBody Escolaridad escolaridad) throws URISyntaxException {
        log.debug("REST request to save Escolaridad : {}", escolaridad);
        if (escolaridad.getId() != null) {
            throw new BadRequestAlertException("A new escolaridad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Escolaridad result = escolaridadRepository.save(escolaridad);
        escolaridadSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/escolaridads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /escolaridads} : Updates an existing escolaridad.
     *
     * @param escolaridad the escolaridad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escolaridad,
     * or with status {@code 400 (Bad Request)} if the escolaridad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the escolaridad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/escolaridads")
    public ResponseEntity<Escolaridad> updateEscolaridad(@RequestBody Escolaridad escolaridad) throws URISyntaxException {
        log.debug("REST request to update Escolaridad : {}", escolaridad);
        if (escolaridad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Escolaridad result = escolaridadRepository.save(escolaridad);
        escolaridadSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, escolaridad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /escolaridads} : get all the escolaridads.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of escolaridads in body.
     */
    @GetMapping("/escolaridads")
    public List<Escolaridad> getAllEscolaridads() {
        log.debug("REST request to get all Escolaridads");
        return escolaridadRepository.findAll();
    }

    /**
     * {@code GET  /escolaridads/:id} : get the "id" escolaridad.
     *
     * @param id the id of the escolaridad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the escolaridad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/escolaridads/{id}")
    public ResponseEntity<Escolaridad> getEscolaridad(@PathVariable Long id) {
        log.debug("REST request to get Escolaridad : {}", id);
        Optional<Escolaridad> escolaridad = escolaridadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(escolaridad);
    }

    /**
     * {@code DELETE  /escolaridads/:id} : delete the "id" escolaridad.
     *
     * @param id the id of the escolaridad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/escolaridads/{id}")
    public ResponseEntity<Void> deleteEscolaridad(@PathVariable Long id) {
        log.debug("REST request to delete Escolaridad : {}", id);
        escolaridadRepository.deleteById(id);
        escolaridadSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/escolaridads?query=:query} : search for the escolaridad corresponding
     * to the query.
     *
     * @param query the query of the escolaridad search.
     * @return the result of the search.
     */
    @GetMapping("/_search/escolaridads")
    public List<Escolaridad> searchEscolaridads(@RequestParam String query) {
        log.debug("REST request to search Escolaridads for query {}", query);
        return StreamSupport
            .stream(escolaridadSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
