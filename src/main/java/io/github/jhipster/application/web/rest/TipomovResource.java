package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Tipomov;
import io.github.jhipster.application.repository.TipomovRepository;
import io.github.jhipster.application.repository.search.TipomovSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Tipomov}.
 */
@RestController
@RequestMapping("/api")
public class TipomovResource {

    private final Logger log = LoggerFactory.getLogger(TipomovResource.class);

    private static final String ENTITY_NAME = "tipomov";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipomovRepository tipomovRepository;

    private final TipomovSearchRepository tipomovSearchRepository;

    public TipomovResource(TipomovRepository tipomovRepository, TipomovSearchRepository tipomovSearchRepository) {
        this.tipomovRepository = tipomovRepository;
        this.tipomovSearchRepository = tipomovSearchRepository;
    }

    /**
     * {@code POST  /tipomovs} : Create a new tipomov.
     *
     * @param tipomov the tipomov to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipomov, or with status {@code 400 (Bad Request)} if the tipomov has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipomovs")
    public ResponseEntity<Tipomov> createTipomov(@RequestBody Tipomov tipomov) throws URISyntaxException {
        log.debug("REST request to save Tipomov : {}", tipomov);
        if (tipomov.getId() != null) {
            throw new BadRequestAlertException("A new tipomov cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tipomov result = tipomovRepository.save(tipomov);
        tipomovSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tipomovs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipomovs} : Updates an existing tipomov.
     *
     * @param tipomov the tipomov to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipomov,
     * or with status {@code 400 (Bad Request)} if the tipomov is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipomov couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipomovs")
    public ResponseEntity<Tipomov> updateTipomov(@RequestBody Tipomov tipomov) throws URISyntaxException {
        log.debug("REST request to update Tipomov : {}", tipomov);
        if (tipomov.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tipomov result = tipomovRepository.save(tipomov);
        tipomovSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipomov.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipomovs} : get all the tipomovs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipomovs in body.
     */
    @GetMapping("/tipomovs")
    public List<Tipomov> getAllTipomovs() {
        log.debug("REST request to get all Tipomovs");
        return tipomovRepository.findAll();
    }

    /**
     * {@code GET  /tipomovs/:id} : get the "id" tipomov.
     *
     * @param id the id of the tipomov to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipomov, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipomovs/{id}")
    public ResponseEntity<Tipomov> getTipomov(@PathVariable Long id) {
        log.debug("REST request to get Tipomov : {}", id);
        Optional<Tipomov> tipomov = tipomovRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipomov);
    }

    /**
     * {@code DELETE  /tipomovs/:id} : delete the "id" tipomov.
     *
     * @param id the id of the tipomov to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipomovs/{id}")
    public ResponseEntity<Void> deleteTipomov(@PathVariable Long id) {
        log.debug("REST request to delete Tipomov : {}", id);
        tipomovRepository.deleteById(id);
        tipomovSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipomovs?query=:query} : search for the tipomov corresponding
     * to the query.
     *
     * @param query the query of the tipomov search.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipomovs")
    public List<Tipomov> searchTipomovs(@RequestParam String query) {
        log.debug("REST request to search Tipomovs for query {}", query);
        return StreamSupport
            .stream(tipomovSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
