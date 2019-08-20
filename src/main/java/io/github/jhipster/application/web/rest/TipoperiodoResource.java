package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Tipoperiodo;
import io.github.jhipster.application.repository.TipoperiodoRepository;
import io.github.jhipster.application.repository.search.TipoperiodoSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Tipoperiodo}.
 */
@RestController
@RequestMapping("/api")
public class TipoperiodoResource {

    private final Logger log = LoggerFactory.getLogger(TipoperiodoResource.class);

    private static final String ENTITY_NAME = "tipoperiodo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoperiodoRepository tipoperiodoRepository;

    private final TipoperiodoSearchRepository tipoperiodoSearchRepository;

    public TipoperiodoResource(TipoperiodoRepository tipoperiodoRepository, TipoperiodoSearchRepository tipoperiodoSearchRepository) {
        this.tipoperiodoRepository = tipoperiodoRepository;
        this.tipoperiodoSearchRepository = tipoperiodoSearchRepository;
    }

    /**
     * {@code POST  /tipoperiodos} : Create a new tipoperiodo.
     *
     * @param tipoperiodo the tipoperiodo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoperiodo, or with status {@code 400 (Bad Request)} if the tipoperiodo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipoperiodos")
    public ResponseEntity<Tipoperiodo> createTipoperiodo(@RequestBody Tipoperiodo tipoperiodo) throws URISyntaxException {
        log.debug("REST request to save Tipoperiodo : {}", tipoperiodo);
        if (tipoperiodo.getId() != null) {
            throw new BadRequestAlertException("A new tipoperiodo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tipoperiodo result = tipoperiodoRepository.save(tipoperiodo);
        tipoperiodoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tipoperiodos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipoperiodos} : Updates an existing tipoperiodo.
     *
     * @param tipoperiodo the tipoperiodo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoperiodo,
     * or with status {@code 400 (Bad Request)} if the tipoperiodo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoperiodo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipoperiodos")
    public ResponseEntity<Tipoperiodo> updateTipoperiodo(@RequestBody Tipoperiodo tipoperiodo) throws URISyntaxException {
        log.debug("REST request to update Tipoperiodo : {}", tipoperiodo);
        if (tipoperiodo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tipoperiodo result = tipoperiodoRepository.save(tipoperiodo);
        tipoperiodoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoperiodo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipoperiodos} : get all the tipoperiodos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoperiodos in body.
     */
    @GetMapping("/tipoperiodos")
    public List<Tipoperiodo> getAllTipoperiodos() {
        log.debug("REST request to get all Tipoperiodos");
        return tipoperiodoRepository.findAll();
    }

    /**
     * {@code GET  /tipoperiodos/:id} : get the "id" tipoperiodo.
     *
     * @param id the id of the tipoperiodo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoperiodo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipoperiodos/{id}")
    public ResponseEntity<Tipoperiodo> getTipoperiodo(@PathVariable Long id) {
        log.debug("REST request to get Tipoperiodo : {}", id);
        Optional<Tipoperiodo> tipoperiodo = tipoperiodoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoperiodo);
    }

    /**
     * {@code DELETE  /tipoperiodos/:id} : delete the "id" tipoperiodo.
     *
     * @param id the id of the tipoperiodo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipoperiodos/{id}")
    public ResponseEntity<Void> deleteTipoperiodo(@PathVariable Long id) {
        log.debug("REST request to delete Tipoperiodo : {}", id);
        tipoperiodoRepository.deleteById(id);
        tipoperiodoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipoperiodos?query=:query} : search for the tipoperiodo corresponding
     * to the query.
     *
     * @param query the query of the tipoperiodo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipoperiodos")
    public List<Tipoperiodo> searchTipoperiodos(@RequestParam String query) {
        log.debug("REST request to search Tipoperiodos for query {}", query);
        return StreamSupport
            .stream(tipoperiodoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
