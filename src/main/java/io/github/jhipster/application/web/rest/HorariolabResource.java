package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Horariolab;
import io.github.jhipster.application.repository.HorariolabRepository;
import io.github.jhipster.application.repository.search.HorariolabSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Horariolab}.
 */
@RestController
@RequestMapping("/api")
public class HorariolabResource {

    private final Logger log = LoggerFactory.getLogger(HorariolabResource.class);

    private static final String ENTITY_NAME = "horariolab";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HorariolabRepository horariolabRepository;

    private final HorariolabSearchRepository horariolabSearchRepository;

    public HorariolabResource(HorariolabRepository horariolabRepository, HorariolabSearchRepository horariolabSearchRepository) {
        this.horariolabRepository = horariolabRepository;
        this.horariolabSearchRepository = horariolabSearchRepository;
    }

    /**
     * {@code POST  /horariolabs} : Create a new horariolab.
     *
     * @param horariolab the horariolab to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horariolab, or with status {@code 400 (Bad Request)} if the horariolab has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/horariolabs")
    public ResponseEntity<Horariolab> createHorariolab(@RequestBody Horariolab horariolab) throws URISyntaxException {
        log.debug("REST request to save Horariolab : {}", horariolab);
        if (horariolab.getId() != null) {
            throw new BadRequestAlertException("A new horariolab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Horariolab result = horariolabRepository.save(horariolab);
        horariolabSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/horariolabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /horariolabs} : Updates an existing horariolab.
     *
     * @param horariolab the horariolab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horariolab,
     * or with status {@code 400 (Bad Request)} if the horariolab is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horariolab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/horariolabs")
    public ResponseEntity<Horariolab> updateHorariolab(@RequestBody Horariolab horariolab) throws URISyntaxException {
        log.debug("REST request to update Horariolab : {}", horariolab);
        if (horariolab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Horariolab result = horariolabRepository.save(horariolab);
        horariolabSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, horariolab.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /horariolabs} : get all the horariolabs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horariolabs in body.
     */
    @GetMapping("/horariolabs")
    public List<Horariolab> getAllHorariolabs() {
        log.debug("REST request to get all Horariolabs");
        return horariolabRepository.findAll();
    }

    /**
     * {@code GET  /horariolabs/:id} : get the "id" horariolab.
     *
     * @param id the id of the horariolab to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horariolab, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/horariolabs/{id}")
    public ResponseEntity<Horariolab> getHorariolab(@PathVariable Long id) {
        log.debug("REST request to get Horariolab : {}", id);
        Optional<Horariolab> horariolab = horariolabRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(horariolab);
    }

    /**
     * {@code DELETE  /horariolabs/:id} : delete the "id" horariolab.
     *
     * @param id the id of the horariolab to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/horariolabs/{id}")
    public ResponseEntity<Void> deleteHorariolab(@PathVariable Long id) {
        log.debug("REST request to delete Horariolab : {}", id);
        horariolabRepository.deleteById(id);
        horariolabSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/horariolabs?query=:query} : search for the horariolab corresponding
     * to the query.
     *
     * @param query the query of the horariolab search.
     * @return the result of the search.
     */
    @GetMapping("/_search/horariolabs")
    public List<Horariolab> searchHorariolabs(@RequestParam String query) {
        log.debug("REST request to search Horariolabs for query {}", query);
        return StreamSupport
            .stream(horariolabSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
