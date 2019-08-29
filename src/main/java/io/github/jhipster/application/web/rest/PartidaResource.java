package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Partida;
import io.github.jhipster.application.repository.PartidaRepository;
import io.github.jhipster.application.repository.search.PartidaSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Partida}.
 */
@RestController
@RequestMapping("/api")
public class PartidaResource {

    private final Logger log = LoggerFactory.getLogger(PartidaResource.class);

    private static final String ENTITY_NAME = "partida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartidaRepository partidaRepository;

    private final PartidaSearchRepository partidaSearchRepository;

    public PartidaResource(PartidaRepository partidaRepository, PartidaSearchRepository partidaSearchRepository) {
        this.partidaRepository = partidaRepository;
        this.partidaSearchRepository = partidaSearchRepository;
    }

    /**
     * {@code POST  /partidas} : Create a new partida.
     *
     * @param partida the partida to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partida, or with status {@code 400 (Bad Request)} if the partida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partidas")
    public ResponseEntity<Partida> createPartida(@RequestBody Partida partida) throws URISyntaxException {
        log.debug("REST request to save Partida : {}", partida);
        if (partida.getId() != null) {
            throw new BadRequestAlertException("A new partida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Partida result = partidaRepository.save(partida);
        partidaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/partidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partidas} : Updates an existing partida.
     *
     * @param partida the partida to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partida,
     * or with status {@code 400 (Bad Request)} if the partida is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partida couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partidas")
    public ResponseEntity<Partida> updatePartida(@RequestBody Partida partida) throws URISyntaxException {
        log.debug("REST request to update Partida : {}", partida);
        if (partida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Partida result = partidaRepository.save(partida);
        partidaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partida.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /partidas} : get all the partidas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partidas in body.
     */
    @GetMapping("/partidas")
    public List<Partida> getAllPartidas() {
        log.debug("REST request to get all Partidas");
        return partidaRepository.findAll();
    }

    /**
     * {@code GET  /partidas/:id} : get the "id" partida.
     *
     * @param id the id of the partida to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partida, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partidas/{id}")
    public ResponseEntity<Partida> getPartida(@PathVariable Long id) {
        log.debug("REST request to get Partida : {}", id);
        Optional<Partida> partida = partidaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partida);
    }

    /**
     * {@code DELETE  /partidas/:id} : delete the "id" partida.
     *
     * @param id the id of the partida to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partidas/{id}")
    public ResponseEntity<Void> deletePartida(@PathVariable Long id) {
        log.debug("REST request to delete Partida : {}", id);
        partidaRepository.deleteById(id);
        partidaSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/partidas?query=:query} : search for the partida corresponding
     * to the query.
     *
     * @param query the query of the partida search.
     * @return the result of the search.
     */
    @GetMapping("/_search/partidas")
    public List<Partida> searchPartidas(@RequestParam String query) {
        log.debug("REST request to search Partidas for query {}", query);
        return StreamSupport
            .stream(partidaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
