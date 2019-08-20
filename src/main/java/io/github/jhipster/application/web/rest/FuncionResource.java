package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Funcion;
import io.github.jhipster.application.repository.FuncionRepository;
import io.github.jhipster.application.repository.search.FuncionSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Funcion}.
 */
@RestController
@RequestMapping("/api")
public class FuncionResource {

    private final Logger log = LoggerFactory.getLogger(FuncionResource.class);

    private static final String ENTITY_NAME = "funcion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionRepository funcionRepository;

    private final FuncionSearchRepository funcionSearchRepository;

    public FuncionResource(FuncionRepository funcionRepository, FuncionSearchRepository funcionSearchRepository) {
        this.funcionRepository = funcionRepository;
        this.funcionSearchRepository = funcionSearchRepository;
    }

    /**
     * {@code POST  /funcions} : Create a new funcion.
     *
     * @param funcion the funcion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcion, or with status {@code 400 (Bad Request)} if the funcion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funcions")
    public ResponseEntity<Funcion> createFuncion(@RequestBody Funcion funcion) throws URISyntaxException {
        log.debug("REST request to save Funcion : {}", funcion);
        if (funcion.getId() != null) {
            throw new BadRequestAlertException("A new funcion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Funcion result = funcionRepository.save(funcion);
        funcionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/funcions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funcions} : Updates an existing funcion.
     *
     * @param funcion the funcion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcion,
     * or with status {@code 400 (Bad Request)} if the funcion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funcions")
    public ResponseEntity<Funcion> updateFuncion(@RequestBody Funcion funcion) throws URISyntaxException {
        log.debug("REST request to update Funcion : {}", funcion);
        if (funcion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Funcion result = funcionRepository.save(funcion);
        funcionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, funcion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /funcions} : get all the funcions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcions in body.
     */
    @GetMapping("/funcions")
    public List<Funcion> getAllFuncions() {
        log.debug("REST request to get all Funcions");
        return funcionRepository.findAll();
    }

    /**
     * {@code GET  /funcions/:id} : get the "id" funcion.
     *
     * @param id the id of the funcion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funcions/{id}")
    public ResponseEntity<Funcion> getFuncion(@PathVariable Long id) {
        log.debug("REST request to get Funcion : {}", id);
        Optional<Funcion> funcion = funcionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(funcion);
    }

    /**
     * {@code DELETE  /funcions/:id} : delete the "id" funcion.
     *
     * @param id the id of the funcion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funcions/{id}")
    public ResponseEntity<Void> deleteFuncion(@PathVariable Long id) {
        log.debug("REST request to delete Funcion : {}", id);
        funcionRepository.deleteById(id);
        funcionSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/funcions?query=:query} : search for the funcion corresponding
     * to the query.
     *
     * @param query the query of the funcion search.
     * @return the result of the search.
     */
    @GetMapping("/_search/funcions")
    public List<Funcion> searchFuncions(@RequestParam String query) {
        log.debug("REST request to search Funcions for query {}", query);
        return StreamSupport
            .stream(funcionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
