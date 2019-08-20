package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Funcionlaboral;
import io.github.jhipster.application.repository.FuncionlaboralRepository;
import io.github.jhipster.application.repository.search.FuncionlaboralSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Funcionlaboral}.
 */
@RestController
@RequestMapping("/api")
public class FuncionlaboralResource {

    private final Logger log = LoggerFactory.getLogger(FuncionlaboralResource.class);

    private static final String ENTITY_NAME = "funcionlaboral";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionlaboralRepository funcionlaboralRepository;

    private final FuncionlaboralSearchRepository funcionlaboralSearchRepository;

    public FuncionlaboralResource(FuncionlaboralRepository funcionlaboralRepository, FuncionlaboralSearchRepository funcionlaboralSearchRepository) {
        this.funcionlaboralRepository = funcionlaboralRepository;
        this.funcionlaboralSearchRepository = funcionlaboralSearchRepository;
    }

    /**
     * {@code POST  /funcionlaborals} : Create a new funcionlaboral.
     *
     * @param funcionlaboral the funcionlaboral to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionlaboral, or with status {@code 400 (Bad Request)} if the funcionlaboral has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funcionlaborals")
    public ResponseEntity<Funcionlaboral> createFuncionlaboral(@RequestBody Funcionlaboral funcionlaboral) throws URISyntaxException {
        log.debug("REST request to save Funcionlaboral : {}", funcionlaboral);
        if (funcionlaboral.getId() != null) {
            throw new BadRequestAlertException("A new funcionlaboral cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Funcionlaboral result = funcionlaboralRepository.save(funcionlaboral);
        funcionlaboralSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/funcionlaborals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funcionlaborals} : Updates an existing funcionlaboral.
     *
     * @param funcionlaboral the funcionlaboral to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionlaboral,
     * or with status {@code 400 (Bad Request)} if the funcionlaboral is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionlaboral couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funcionlaborals")
    public ResponseEntity<Funcionlaboral> updateFuncionlaboral(@RequestBody Funcionlaboral funcionlaboral) throws URISyntaxException {
        log.debug("REST request to update Funcionlaboral : {}", funcionlaboral);
        if (funcionlaboral.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Funcionlaboral result = funcionlaboralRepository.save(funcionlaboral);
        funcionlaboralSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, funcionlaboral.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /funcionlaborals} : get all the funcionlaborals.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionlaborals in body.
     */
    @GetMapping("/funcionlaborals")
    public List<Funcionlaboral> getAllFuncionlaborals() {
        log.debug("REST request to get all Funcionlaborals");
        return funcionlaboralRepository.findAll();
    }

    /**
     * {@code GET  /funcionlaborals/:id} : get the "id" funcionlaboral.
     *
     * @param id the id of the funcionlaboral to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionlaboral, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funcionlaborals/{id}")
    public ResponseEntity<Funcionlaboral> getFuncionlaboral(@PathVariable Long id) {
        log.debug("REST request to get Funcionlaboral : {}", id);
        Optional<Funcionlaboral> funcionlaboral = funcionlaboralRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(funcionlaboral);
    }

    /**
     * {@code DELETE  /funcionlaborals/:id} : delete the "id" funcionlaboral.
     *
     * @param id the id of the funcionlaboral to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funcionlaborals/{id}")
    public ResponseEntity<Void> deleteFuncionlaboral(@PathVariable Long id) {
        log.debug("REST request to delete Funcionlaboral : {}", id);
        funcionlaboralRepository.deleteById(id);
        funcionlaboralSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/funcionlaborals?query=:query} : search for the funcionlaboral corresponding
     * to the query.
     *
     * @param query the query of the funcionlaboral search.
     * @return the result of the search.
     */
    @GetMapping("/_search/funcionlaborals")
    public List<Funcionlaboral> searchFuncionlaborals(@RequestParam String query) {
        log.debug("REST request to search Funcionlaborals for query {}", query);
        return StreamSupport
            .stream(funcionlaboralSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
