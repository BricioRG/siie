package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Plaza;
import io.github.jhipster.application.repository.PlazaRepository;
import io.github.jhipster.application.repository.search.PlazaSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Plaza}.
 */
@RestController
@RequestMapping("/api")
public class PlazaResource {

    private final Logger log = LoggerFactory.getLogger(PlazaResource.class);

    private static final String ENTITY_NAME = "plaza";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlazaRepository plazaRepository;

    private final PlazaSearchRepository plazaSearchRepository;

    public PlazaResource(PlazaRepository plazaRepository, PlazaSearchRepository plazaSearchRepository) {
        this.plazaRepository = plazaRepository;
        this.plazaSearchRepository = plazaSearchRepository;
    }

    /**
     * {@code POST  /plazas} : Create a new plaza.
     *
     * @param plaza the plaza to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaza, or with status {@code 400 (Bad Request)} if the plaza has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plazas")
    public ResponseEntity<Plaza> createPlaza(@RequestBody Plaza plaza) throws URISyntaxException {
        log.debug("REST request to save Plaza : {}", plaza);
        if (plaza.getId() != null) {
            throw new BadRequestAlertException("A new plaza cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plaza result = plazaRepository.save(plaza);
        plazaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/plazas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plazas} : Updates an existing plaza.
     *
     * @param plaza the plaza to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaza,
     * or with status {@code 400 (Bad Request)} if the plaza is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaza couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plazas")
    public ResponseEntity<Plaza> updatePlaza(@RequestBody Plaza plaza) throws URISyntaxException {
        log.debug("REST request to update Plaza : {}", plaza);
        if (plaza.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Plaza result = plazaRepository.save(plaza);
        plazaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plaza.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plazas} : get all the plazas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plazas in body.
     */
    @GetMapping("/plazas")
    public List<Plaza> getAllPlazas() {
        log.debug("REST request to get all Plazas");
        return plazaRepository.findAll();
    }

    /**
     * {@code GET  /plazas/:id} : get the "id" plaza.
     *
     * @param id the id of the plaza to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaza, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plazas/{id}")
    public ResponseEntity<Plaza> getPlaza(@PathVariable Long id) {
        log.debug("REST request to get Plaza : {}", id);
        Optional<Plaza> plaza = plazaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plaza);
    }

    /**
     * {@code DELETE  /plazas/:id} : delete the "id" plaza.
     *
     * @param id the id of the plaza to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plazas/{id}")
    public ResponseEntity<Void> deletePlaza(@PathVariable Long id) {
        log.debug("REST request to delete Plaza : {}", id);
        plazaRepository.deleteById(id);
        plazaSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/plazas?query=:query} : search for the plaza corresponding
     * to the query.
     *
     * @param query the query of the plaza search.
     * @return the result of the search.
     */
    @GetMapping("/_search/plazas")
    public List<Plaza> searchPlazas(@RequestParam String query) {
        log.debug("REST request to search Plazas for query {}", query);
        return StreamSupport
            .stream(plazaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
