package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Subciclo;
import io.github.jhipster.application.repository.SubcicloRepository;
import io.github.jhipster.application.repository.search.SubcicloSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Subciclo}.
 */
@RestController
@RequestMapping("/api")
public class SubcicloResource {

    private final Logger log = LoggerFactory.getLogger(SubcicloResource.class);

    private static final String ENTITY_NAME = "subciclo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubcicloRepository subcicloRepository;

    private final SubcicloSearchRepository subcicloSearchRepository;

    public SubcicloResource(SubcicloRepository subcicloRepository, SubcicloSearchRepository subcicloSearchRepository) {
        this.subcicloRepository = subcicloRepository;
        this.subcicloSearchRepository = subcicloSearchRepository;
    }

    /**
     * {@code POST  /subciclos} : Create a new subciclo.
     *
     * @param subciclo the subciclo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subciclo, or with status {@code 400 (Bad Request)} if the subciclo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subciclos")
    public ResponseEntity<Subciclo> createSubciclo(@RequestBody Subciclo subciclo) throws URISyntaxException {
        log.debug("REST request to save Subciclo : {}", subciclo);
        if (subciclo.getId() != null) {
            throw new BadRequestAlertException("A new subciclo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subciclo result = subcicloRepository.save(subciclo);
        subcicloSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/subciclos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subciclos} : Updates an existing subciclo.
     *
     * @param subciclo the subciclo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subciclo,
     * or with status {@code 400 (Bad Request)} if the subciclo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subciclo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subciclos")
    public ResponseEntity<Subciclo> updateSubciclo(@RequestBody Subciclo subciclo) throws URISyntaxException {
        log.debug("REST request to update Subciclo : {}", subciclo);
        if (subciclo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Subciclo result = subcicloRepository.save(subciclo);
        subcicloSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subciclo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subciclos} : get all the subciclos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subciclos in body.
     */
    @GetMapping("/subciclos")
    public List<Subciclo> getAllSubciclos() {
        log.debug("REST request to get all Subciclos");
        return subcicloRepository.findAll();
    }

    /**
     * {@code GET  /subciclos/:id} : get the "id" subciclo.
     *
     * @param id the id of the subciclo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subciclo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subciclos/{id}")
    public ResponseEntity<Subciclo> getSubciclo(@PathVariable Long id) {
        log.debug("REST request to get Subciclo : {}", id);
        Optional<Subciclo> subciclo = subcicloRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subciclo);
    }

    /**
     * {@code DELETE  /subciclos/:id} : delete the "id" subciclo.
     *
     * @param id the id of the subciclo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subciclos/{id}")
    public ResponseEntity<Void> deleteSubciclo(@PathVariable Long id) {
        log.debug("REST request to delete Subciclo : {}", id);
        subcicloRepository.deleteById(id);
        subcicloSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/subciclos?query=:query} : search for the subciclo corresponding
     * to the query.
     *
     * @param query the query of the subciclo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/subciclos")
    public List<Subciclo> searchSubciclos(@RequestParam String query) {
        log.debug("REST request to search Subciclos for query {}", query);
        return StreamSupport
            .stream(subcicloSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
