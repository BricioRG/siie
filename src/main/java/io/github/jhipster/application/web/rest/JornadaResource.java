package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Jornada;
import io.github.jhipster.application.repository.JornadaRepository;
import io.github.jhipster.application.repository.search.JornadaSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Jornada}.
 */
@RestController
@RequestMapping("/api")
public class JornadaResource {

    private final Logger log = LoggerFactory.getLogger(JornadaResource.class);

    private static final String ENTITY_NAME = "jornada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JornadaRepository jornadaRepository;

    private final JornadaSearchRepository jornadaSearchRepository;

    public JornadaResource(JornadaRepository jornadaRepository, JornadaSearchRepository jornadaSearchRepository) {
        this.jornadaRepository = jornadaRepository;
        this.jornadaSearchRepository = jornadaSearchRepository;
    }

    /**
     * {@code POST  /jornadas} : Create a new jornada.
     *
     * @param jornada the jornada to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jornada, or with status {@code 400 (Bad Request)} if the jornada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jornadas")
    public ResponseEntity<Jornada> createJornada(@RequestBody Jornada jornada) throws URISyntaxException {
        log.debug("REST request to save Jornada : {}", jornada);
        if (jornada.getId() != null) {
            throw new BadRequestAlertException("A new jornada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jornada result = jornadaRepository.save(jornada);
        jornadaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jornadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jornadas} : Updates an existing jornada.
     *
     * @param jornada the jornada to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jornada,
     * or with status {@code 400 (Bad Request)} if the jornada is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jornada couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jornadas")
    public ResponseEntity<Jornada> updateJornada(@RequestBody Jornada jornada) throws URISyntaxException {
        log.debug("REST request to update Jornada : {}", jornada);
        if (jornada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Jornada result = jornadaRepository.save(jornada);
        jornadaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jornada.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jornadas} : get all the jornadas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jornadas in body.
     */
    @GetMapping("/jornadas")
    public List<Jornada> getAllJornadas() {
        log.debug("REST request to get all Jornadas");
        return jornadaRepository.findAll();
    }

    /**
     * {@code GET  /jornadas/:id} : get the "id" jornada.
     *
     * @param id the id of the jornada to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jornada, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jornadas/{id}")
    public ResponseEntity<Jornada> getJornada(@PathVariable Long id) {
        log.debug("REST request to get Jornada : {}", id);
        Optional<Jornada> jornada = jornadaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jornada);
    }

    /**
     * {@code DELETE  /jornadas/:id} : delete the "id" jornada.
     *
     * @param id the id of the jornada to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jornadas/{id}")
    public ResponseEntity<Void> deleteJornada(@PathVariable Long id) {
        log.debug("REST request to delete Jornada : {}", id);
        jornadaRepository.deleteById(id);
        jornadaSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/jornadas?query=:query} : search for the jornada corresponding
     * to the query.
     *
     * @param query the query of the jornada search.
     * @return the result of the search.
     */
    @GetMapping("/_search/jornadas")
    public List<Jornada> searchJornadas(@RequestParam String query) {
        log.debug("REST request to search Jornadas for query {}", query);
        return StreamSupport
            .stream(jornadaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
