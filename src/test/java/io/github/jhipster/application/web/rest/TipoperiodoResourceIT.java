package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Tipoperiodo;
import io.github.jhipster.application.repository.TipoperiodoRepository;
import io.github.jhipster.application.repository.search.TipoperiodoSearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoperiodoResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class TipoperiodoResourceIT {

    @Autowired
    private TipoperiodoRepository tipoperiodoRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TipoperiodoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoperiodoSearchRepository mockTipoperiodoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTipoperiodoMockMvc;

    private Tipoperiodo tipoperiodo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoperiodoResource tipoperiodoResource = new TipoperiodoResource(tipoperiodoRepository, mockTipoperiodoSearchRepository);
        this.restTipoperiodoMockMvc = MockMvcBuilders.standaloneSetup(tipoperiodoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tipoperiodo createEntity(EntityManager em) {
        Tipoperiodo tipoperiodo = new Tipoperiodo();
        return tipoperiodo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tipoperiodo createUpdatedEntity(EntityManager em) {
        Tipoperiodo tipoperiodo = new Tipoperiodo();
        return tipoperiodo;
    }

    @BeforeEach
    public void initTest() {
        tipoperiodo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoperiodo() throws Exception {
        int databaseSizeBeforeCreate = tipoperiodoRepository.findAll().size();

        // Create the Tipoperiodo
        restTipoperiodoMockMvc.perform(post("/api/tipoperiodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoperiodo)))
            .andExpect(status().isCreated());

        // Validate the Tipoperiodo in the database
        List<Tipoperiodo> tipoperiodoList = tipoperiodoRepository.findAll();
        assertThat(tipoperiodoList).hasSize(databaseSizeBeforeCreate + 1);
        Tipoperiodo testTipoperiodo = tipoperiodoList.get(tipoperiodoList.size() - 1);

        // Validate the Tipoperiodo in Elasticsearch
        verify(mockTipoperiodoSearchRepository, times(1)).save(testTipoperiodo);
    }

    @Test
    @Transactional
    public void createTipoperiodoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoperiodoRepository.findAll().size();

        // Create the Tipoperiodo with an existing ID
        tipoperiodo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoperiodoMockMvc.perform(post("/api/tipoperiodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoperiodo)))
            .andExpect(status().isBadRequest());

        // Validate the Tipoperiodo in the database
        List<Tipoperiodo> tipoperiodoList = tipoperiodoRepository.findAll();
        assertThat(tipoperiodoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Tipoperiodo in Elasticsearch
        verify(mockTipoperiodoSearchRepository, times(0)).save(tipoperiodo);
    }


    @Test
    @Transactional
    public void getAllTipoperiodos() throws Exception {
        // Initialize the database
        tipoperiodoRepository.saveAndFlush(tipoperiodo);

        // Get all the tipoperiodoList
        restTipoperiodoMockMvc.perform(get("/api/tipoperiodos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoperiodo.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTipoperiodo() throws Exception {
        // Initialize the database
        tipoperiodoRepository.saveAndFlush(tipoperiodo);

        // Get the tipoperiodo
        restTipoperiodoMockMvc.perform(get("/api/tipoperiodos/{id}", tipoperiodo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoperiodo.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoperiodo() throws Exception {
        // Get the tipoperiodo
        restTipoperiodoMockMvc.perform(get("/api/tipoperiodos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoperiodo() throws Exception {
        // Initialize the database
        tipoperiodoRepository.saveAndFlush(tipoperiodo);

        int databaseSizeBeforeUpdate = tipoperiodoRepository.findAll().size();

        // Update the tipoperiodo
        Tipoperiodo updatedTipoperiodo = tipoperiodoRepository.findById(tipoperiodo.getId()).get();
        // Disconnect from session so that the updates on updatedTipoperiodo are not directly saved in db
        em.detach(updatedTipoperiodo);

        restTipoperiodoMockMvc.perform(put("/api/tipoperiodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoperiodo)))
            .andExpect(status().isOk());

        // Validate the Tipoperiodo in the database
        List<Tipoperiodo> tipoperiodoList = tipoperiodoRepository.findAll();
        assertThat(tipoperiodoList).hasSize(databaseSizeBeforeUpdate);
        Tipoperiodo testTipoperiodo = tipoperiodoList.get(tipoperiodoList.size() - 1);

        // Validate the Tipoperiodo in Elasticsearch
        verify(mockTipoperiodoSearchRepository, times(1)).save(testTipoperiodo);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoperiodo() throws Exception {
        int databaseSizeBeforeUpdate = tipoperiodoRepository.findAll().size();

        // Create the Tipoperiodo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoperiodoMockMvc.perform(put("/api/tipoperiodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoperiodo)))
            .andExpect(status().isBadRequest());

        // Validate the Tipoperiodo in the database
        List<Tipoperiodo> tipoperiodoList = tipoperiodoRepository.findAll();
        assertThat(tipoperiodoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Tipoperiodo in Elasticsearch
        verify(mockTipoperiodoSearchRepository, times(0)).save(tipoperiodo);
    }

    @Test
    @Transactional
    public void deleteTipoperiodo() throws Exception {
        // Initialize the database
        tipoperiodoRepository.saveAndFlush(tipoperiodo);

        int databaseSizeBeforeDelete = tipoperiodoRepository.findAll().size();

        // Delete the tipoperiodo
        restTipoperiodoMockMvc.perform(delete("/api/tipoperiodos/{id}", tipoperiodo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tipoperiodo> tipoperiodoList = tipoperiodoRepository.findAll();
        assertThat(tipoperiodoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Tipoperiodo in Elasticsearch
        verify(mockTipoperiodoSearchRepository, times(1)).deleteById(tipoperiodo.getId());
    }

    @Test
    @Transactional
    public void searchTipoperiodo() throws Exception {
        // Initialize the database
        tipoperiodoRepository.saveAndFlush(tipoperiodo);
        when(mockTipoperiodoSearchRepository.search(queryStringQuery("id:" + tipoperiodo.getId())))
            .thenReturn(Collections.singletonList(tipoperiodo));
        // Search the tipoperiodo
        restTipoperiodoMockMvc.perform(get("/api/_search/tipoperiodos?query=id:" + tipoperiodo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoperiodo.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tipoperiodo.class);
        Tipoperiodo tipoperiodo1 = new Tipoperiodo();
        tipoperiodo1.setId(1L);
        Tipoperiodo tipoperiodo2 = new Tipoperiodo();
        tipoperiodo2.setId(tipoperiodo1.getId());
        assertThat(tipoperiodo1).isEqualTo(tipoperiodo2);
        tipoperiodo2.setId(2L);
        assertThat(tipoperiodo1).isNotEqualTo(tipoperiodo2);
        tipoperiodo1.setId(null);
        assertThat(tipoperiodo1).isNotEqualTo(tipoperiodo2);
    }
}
