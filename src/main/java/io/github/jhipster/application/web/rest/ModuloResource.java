package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Modulo;
import io.github.jhipster.application.repository.ModuloRepository;
import io.github.jhipster.application.repository.search.ModuloSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Modulo}.
 */
@RestController
@RequestMapping("/api")
public class ModuloResource {

    private final Logger log = LoggerFactory.getLogger(ModuloResource.class);

    private static final String ENTITY_NAME = "modulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuloRepository moduloRepository;

    private final ModuloSearchRepository moduloSearchRepository;

    public ModuloResource(ModuloRepository moduloRepository, ModuloSearchRepository moduloSearchRepository) {
        this.moduloRepository = moduloRepository;
        this.moduloSearchRepository = moduloSearchRepository;
    }

    /**
     * {@code POST  /modulos} : Create a new modulo.
     *
     * @param modulo the modulo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modulo, or with status {@code 400 (Bad Request)} if the modulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modulos")
    public ResponseEntity<Modulo> createModulo(@RequestBody Modulo modulo) throws URISyntaxException {
        log.debug("REST request to save Modulo : {}", modulo);
        if (modulo.getId() != null) {
            throw new BadRequestAlertException("A new modulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Modulo result = moduloRepository.save(modulo);
        moduloSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/modulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modulos} : Updates an existing modulo.
     *
     * @param modulo the modulo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modulo,
     * or with status {@code 400 (Bad Request)} if the modulo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modulo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modulos")
    public ResponseEntity<Modulo> updateModulo(@RequestBody Modulo modulo) throws URISyntaxException {
        log.debug("REST request to update Modulo : {}", modulo);
        if (modulo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Modulo result = moduloRepository.save(modulo);
        moduloSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, modulo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /modulos} : get all the modulos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modulos in body.
     */
    @GetMapping("/modulos")
    public List<Modulo> getAllModulos() {
        log.debug("REST request to get all Modulos");
        return moduloRepository.findAll();
    }

    /**
     * {@code GET  /modulos/:id} : get the "id" modulo.
     *
     * @param id the id of the modulo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modulo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modulos/{id}")
    public ResponseEntity<Modulo> getModulo(@PathVariable Long id) {
        log.debug("REST request to get Modulo : {}", id);
        Optional<Modulo> modulo = moduloRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(modulo);
    }

    /**
     * {@code DELETE  /modulos/:id} : delete the "id" modulo.
     *
     * @param id the id of the modulo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modulos/{id}")
    public ResponseEntity<Void> deleteModulo(@PathVariable Long id) {
        log.debug("REST request to delete Modulo : {}", id);
        moduloRepository.deleteById(id);
        moduloSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/modulos?query=:query} : search for the modulo corresponding
     * to the query.
     *
     * @param query the query of the modulo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/modulos")
    public List<Modulo> searchModulos(@RequestParam String query) {
        log.debug("REST request to search Modulos for query {}", query);
        return StreamSupport
            .stream(moduloSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
