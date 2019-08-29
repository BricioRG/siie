package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Ciclo;
import io.github.jhipster.application.repository.CicloRepository;
import io.github.jhipster.application.repository.search.CicloSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Ciclo}.
 */
@RestController
@RequestMapping("/api")
public class CicloResource {

    private final Logger log = LoggerFactory.getLogger(CicloResource.class);

    private static final String ENTITY_NAME = "ciclo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CicloRepository cicloRepository;

    private final CicloSearchRepository cicloSearchRepository;

    public CicloResource(CicloRepository cicloRepository, CicloSearchRepository cicloSearchRepository) {
        this.cicloRepository = cicloRepository;
        this.cicloSearchRepository = cicloSearchRepository;
    }

    /**
     * {@code POST  /ciclos} : Create a new ciclo.
     *
     * @param ciclo the ciclo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ciclo, or with status {@code 400 (Bad Request)} if the ciclo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ciclos")
    public ResponseEntity<Ciclo> createCiclo(@RequestBody Ciclo ciclo) throws URISyntaxException {
        log.debug("REST request to save Ciclo : {}", ciclo);
        if (ciclo.getId() != null) {
            throw new BadRequestAlertException("A new ciclo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ciclo result = cicloRepository.save(ciclo);
        cicloSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ciclos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ciclos} : Updates an existing ciclo.
     *
     * @param ciclo the ciclo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ciclo,
     * or with status {@code 400 (Bad Request)} if the ciclo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ciclo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ciclos")
    public ResponseEntity<Ciclo> updateCiclo(@RequestBody Ciclo ciclo) throws URISyntaxException {
        log.debug("REST request to update Ciclo : {}", ciclo);
        if (ciclo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ciclo result = cicloRepository.save(ciclo);
        cicloSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ciclo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ciclos} : get all the ciclos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ciclos in body.
     */
    @GetMapping("/ciclos")
    public List<Ciclo> getAllCiclos() {
        log.debug("REST request to get all Ciclos");
        return cicloRepository.findAll();
    }

    /**
     * {@code GET  /ciclos/:id} : get the "id" ciclo.
     *
     * @param id the id of the ciclo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ciclo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ciclos/{id}")
    public ResponseEntity<Ciclo> getCiclo(@PathVariable Long id) {
        log.debug("REST request to get Ciclo : {}", id);
        Optional<Ciclo> ciclo = cicloRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ciclo);
    }

    /**
     * {@code DELETE  /ciclos/:id} : delete the "id" ciclo.
     *
     * @param id the id of the ciclo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ciclos/{id}")
    public ResponseEntity<Void> deleteCiclo(@PathVariable Long id) {
        log.debug("REST request to delete Ciclo : {}", id);
        cicloRepository.deleteById(id);
        cicloSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ciclos?query=:query} : search for the ciclo corresponding
     * to the query.
     *
     * @param query the query of the ciclo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/ciclos")
    public List<Ciclo> searchCiclos(@RequestParam String query) {
        log.debug("REST request to search Ciclos for query {}", query);
        return StreamSupport
            .stream(cicloSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
