package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Motivomov;
import io.github.jhipster.application.repository.MotivomovRepository;
import io.github.jhipster.application.repository.search.MotivomovSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Motivomov}.
 */
@RestController
@RequestMapping("/api")
public class MotivomovResource {

    private final Logger log = LoggerFactory.getLogger(MotivomovResource.class);

    private static final String ENTITY_NAME = "motivomov";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotivomovRepository motivomovRepository;

    private final MotivomovSearchRepository motivomovSearchRepository;

    public MotivomovResource(MotivomovRepository motivomovRepository, MotivomovSearchRepository motivomovSearchRepository) {
        this.motivomovRepository = motivomovRepository;
        this.motivomovSearchRepository = motivomovSearchRepository;
    }

    /**
     * {@code POST  /motivomovs} : Create a new motivomov.
     *
     * @param motivomov the motivomov to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motivomov, or with status {@code 400 (Bad Request)} if the motivomov has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/motivomovs")
    public ResponseEntity<Motivomov> createMotivomov(@RequestBody Motivomov motivomov) throws URISyntaxException {
        log.debug("REST request to save Motivomov : {}", motivomov);
        if (motivomov.getId() != null) {
            throw new BadRequestAlertException("A new motivomov cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Motivomov result = motivomovRepository.save(motivomov);
        motivomovSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/motivomovs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /motivomovs} : Updates an existing motivomov.
     *
     * @param motivomov the motivomov to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivomov,
     * or with status {@code 400 (Bad Request)} if the motivomov is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motivomov couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/motivomovs")
    public ResponseEntity<Motivomov> updateMotivomov(@RequestBody Motivomov motivomov) throws URISyntaxException {
        log.debug("REST request to update Motivomov : {}", motivomov);
        if (motivomov.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Motivomov result = motivomovRepository.save(motivomov);
        motivomovSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, motivomov.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /motivomovs} : get all the motivomovs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of motivomovs in body.
     */
    @GetMapping("/motivomovs")
    public List<Motivomov> getAllMotivomovs() {
        log.debug("REST request to get all Motivomovs");
        return motivomovRepository.findAll();
    }

    /**
     * {@code GET  /motivomovs/:id} : get the "id" motivomov.
     *
     * @param id the id of the motivomov to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motivomov, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/motivomovs/{id}")
    public ResponseEntity<Motivomov> getMotivomov(@PathVariable Long id) {
        log.debug("REST request to get Motivomov : {}", id);
        Optional<Motivomov> motivomov = motivomovRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(motivomov);
    }

    /**
     * {@code DELETE  /motivomovs/:id} : delete the "id" motivomov.
     *
     * @param id the id of the motivomov to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/motivomovs/{id}")
    public ResponseEntity<Void> deleteMotivomov(@PathVariable Long id) {
        log.debug("REST request to delete Motivomov : {}", id);
        motivomovRepository.deleteById(id);
        motivomovSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/motivomovs?query=:query} : search for the motivomov corresponding
     * to the query.
     *
     * @param query the query of the motivomov search.
     * @return the result of the search.
     */
    @GetMapping("/_search/motivomovs")
    public List<Motivomov> searchMotivomovs(@RequestParam String query) {
        log.debug("REST request to search Motivomovs for query {}", query);
        return StreamSupport
            .stream(motivomovSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
