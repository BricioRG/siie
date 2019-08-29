package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Financiamiento;
import io.github.jhipster.application.repository.FinanciamientoRepository;
import io.github.jhipster.application.repository.search.FinanciamientoSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Financiamiento}.
 */
@RestController
@RequestMapping("/api")
public class FinanciamientoResource {

    private final Logger log = LoggerFactory.getLogger(FinanciamientoResource.class);

    private static final String ENTITY_NAME = "financiamiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinanciamientoRepository financiamientoRepository;

    private final FinanciamientoSearchRepository financiamientoSearchRepository;

    public FinanciamientoResource(FinanciamientoRepository financiamientoRepository, FinanciamientoSearchRepository financiamientoSearchRepository) {
        this.financiamientoRepository = financiamientoRepository;
        this.financiamientoSearchRepository = financiamientoSearchRepository;
    }

    /**
     * {@code POST  /financiamientos} : Create a new financiamiento.
     *
     * @param financiamiento the financiamiento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new financiamiento, or with status {@code 400 (Bad Request)} if the financiamiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/financiamientos")
    public ResponseEntity<Financiamiento> createFinanciamiento(@RequestBody Financiamiento financiamiento) throws URISyntaxException {
        log.debug("REST request to save Financiamiento : {}", financiamiento);
        if (financiamiento.getId() != null) {
            throw new BadRequestAlertException("A new financiamiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Financiamiento result = financiamientoRepository.save(financiamiento);
        financiamientoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/financiamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /financiamientos} : Updates an existing financiamiento.
     *
     * @param financiamiento the financiamiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financiamiento,
     * or with status {@code 400 (Bad Request)} if the financiamiento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the financiamiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/financiamientos")
    public ResponseEntity<Financiamiento> updateFinanciamiento(@RequestBody Financiamiento financiamiento) throws URISyntaxException {
        log.debug("REST request to update Financiamiento : {}", financiamiento);
        if (financiamiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Financiamiento result = financiamientoRepository.save(financiamiento);
        financiamientoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, financiamiento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /financiamientos} : get all the financiamientos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of financiamientos in body.
     */
    @GetMapping("/financiamientos")
    public List<Financiamiento> getAllFinanciamientos() {
        log.debug("REST request to get all Financiamientos");
        return financiamientoRepository.findAll();
    }

    /**
     * {@code GET  /financiamientos/:id} : get the "id" financiamiento.
     *
     * @param id the id of the financiamiento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the financiamiento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/financiamientos/{id}")
    public ResponseEntity<Financiamiento> getFinanciamiento(@PathVariable Long id) {
        log.debug("REST request to get Financiamiento : {}", id);
        Optional<Financiamiento> financiamiento = financiamientoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(financiamiento);
    }

    /**
     * {@code DELETE  /financiamientos/:id} : delete the "id" financiamiento.
     *
     * @param id the id of the financiamiento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/financiamientos/{id}")
    public ResponseEntity<Void> deleteFinanciamiento(@PathVariable Long id) {
        log.debug("REST request to delete Financiamiento : {}", id);
        financiamientoRepository.deleteById(id);
        financiamientoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/financiamientos?query=:query} : search for the financiamiento corresponding
     * to the query.
     *
     * @param query the query of the financiamiento search.
     * @return the result of the search.
     */
    @GetMapping("/_search/financiamientos")
    public List<Financiamiento> searchFinanciamientos(@RequestParam String query) {
        log.debug("REST request to search Financiamientos for query {}", query);
        return StreamSupport
            .stream(financiamientoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
