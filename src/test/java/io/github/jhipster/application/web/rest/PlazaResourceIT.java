package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Plaza;
import io.github.jhipster.application.repository.PlazaRepository;
import io.github.jhipster.application.repository.search.PlazaSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PlazaResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class PlazaResourceIT {

    private static final String DEFAULT_HORAS = "AAAAAAAAAA";
    private static final String UPDATED_HORAS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHAINI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHAINI = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHAINI = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHAFIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHAFIN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHAFIN = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PlazaRepository plazaRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.PlazaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlazaSearchRepository mockPlazaSearchRepository;

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

    private MockMvc restPlazaMockMvc;

    private Plaza plaza;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlazaResource plazaResource = new PlazaResource(plazaRepository, mockPlazaSearchRepository);
        this.restPlazaMockMvc = MockMvcBuilders.standaloneSetup(plazaResource)
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
    public static Plaza createEntity(EntityManager em) {
        Plaza plaza = new Plaza()
            .horas(DEFAULT_HORAS)
            .fechaini(DEFAULT_FECHAINI)
            .fechafin(DEFAULT_FECHAFIN);
        return plaza;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plaza createUpdatedEntity(EntityManager em) {
        Plaza plaza = new Plaza()
            .horas(UPDATED_HORAS)
            .fechaini(UPDATED_FECHAINI)
            .fechafin(UPDATED_FECHAFIN);
        return plaza;
    }

    @BeforeEach
    public void initTest() {
        plaza = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaza() throws Exception {
        int databaseSizeBeforeCreate = plazaRepository.findAll().size();

        // Create the Plaza
        restPlazaMockMvc.perform(post("/api/plazas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plaza)))
            .andExpect(status().isCreated());

        // Validate the Plaza in the database
        List<Plaza> plazaList = plazaRepository.findAll();
        assertThat(plazaList).hasSize(databaseSizeBeforeCreate + 1);
        Plaza testPlaza = plazaList.get(plazaList.size() - 1);
        assertThat(testPlaza.getHoras()).isEqualTo(DEFAULT_HORAS);
        assertThat(testPlaza.getFechaini()).isEqualTo(DEFAULT_FECHAINI);
        assertThat(testPlaza.getFechafin()).isEqualTo(DEFAULT_FECHAFIN);

        // Validate the Plaza in Elasticsearch
        verify(mockPlazaSearchRepository, times(1)).save(testPlaza);
    }

    @Test
    @Transactional
    public void createPlazaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plazaRepository.findAll().size();

        // Create the Plaza with an existing ID
        plaza.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlazaMockMvc.perform(post("/api/plazas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plaza)))
            .andExpect(status().isBadRequest());

        // Validate the Plaza in the database
        List<Plaza> plazaList = plazaRepository.findAll();
        assertThat(plazaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Plaza in Elasticsearch
        verify(mockPlazaSearchRepository, times(0)).save(plaza);
    }


    @Test
    @Transactional
    public void getAllPlazas() throws Exception {
        // Initialize the database
        plazaRepository.saveAndFlush(plaza);

        // Get all the plazaList
        restPlazaMockMvc.perform(get("/api/plazas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaza.getId().intValue())))
            .andExpect(jsonPath("$.[*].horas").value(hasItem(DEFAULT_HORAS.toString())))
            .andExpect(jsonPath("$.[*].fechaini").value(hasItem(DEFAULT_FECHAINI.toString())))
            .andExpect(jsonPath("$.[*].fechafin").value(hasItem(DEFAULT_FECHAFIN.toString())));
    }
    
    @Test
    @Transactional
    public void getPlaza() throws Exception {
        // Initialize the database
        plazaRepository.saveAndFlush(plaza);

        // Get the plaza
        restPlazaMockMvc.perform(get("/api/plazas/{id}", plaza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plaza.getId().intValue()))
            .andExpect(jsonPath("$.horas").value(DEFAULT_HORAS.toString()))
            .andExpect(jsonPath("$.fechaini").value(DEFAULT_FECHAINI.toString()))
            .andExpect(jsonPath("$.fechafin").value(DEFAULT_FECHAFIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaza() throws Exception {
        // Get the plaza
        restPlazaMockMvc.perform(get("/api/plazas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaza() throws Exception {
        // Initialize the database
        plazaRepository.saveAndFlush(plaza);

        int databaseSizeBeforeUpdate = plazaRepository.findAll().size();

        // Update the plaza
        Plaza updatedPlaza = plazaRepository.findById(plaza.getId()).get();
        // Disconnect from session so that the updates on updatedPlaza are not directly saved in db
        em.detach(updatedPlaza);
        updatedPlaza
            .horas(UPDATED_HORAS)
            .fechaini(UPDATED_FECHAINI)
            .fechafin(UPDATED_FECHAFIN);

        restPlazaMockMvc.perform(put("/api/plazas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlaza)))
            .andExpect(status().isOk());

        // Validate the Plaza in the database
        List<Plaza> plazaList = plazaRepository.findAll();
        assertThat(plazaList).hasSize(databaseSizeBeforeUpdate);
        Plaza testPlaza = plazaList.get(plazaList.size() - 1);
        assertThat(testPlaza.getHoras()).isEqualTo(UPDATED_HORAS);
        assertThat(testPlaza.getFechaini()).isEqualTo(UPDATED_FECHAINI);
        assertThat(testPlaza.getFechafin()).isEqualTo(UPDATED_FECHAFIN);

        // Validate the Plaza in Elasticsearch
        verify(mockPlazaSearchRepository, times(1)).save(testPlaza);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaza() throws Exception {
        int databaseSizeBeforeUpdate = plazaRepository.findAll().size();

        // Create the Plaza

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlazaMockMvc.perform(put("/api/plazas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plaza)))
            .andExpect(status().isBadRequest());

        // Validate the Plaza in the database
        List<Plaza> plazaList = plazaRepository.findAll();
        assertThat(plazaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Plaza in Elasticsearch
        verify(mockPlazaSearchRepository, times(0)).save(plaza);
    }

    @Test
    @Transactional
    public void deletePlaza() throws Exception {
        // Initialize the database
        plazaRepository.saveAndFlush(plaza);

        int databaseSizeBeforeDelete = plazaRepository.findAll().size();

        // Delete the plaza
        restPlazaMockMvc.perform(delete("/api/plazas/{id}", plaza.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plaza> plazaList = plazaRepository.findAll();
        assertThat(plazaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Plaza in Elasticsearch
        verify(mockPlazaSearchRepository, times(1)).deleteById(plaza.getId());
    }

    @Test
    @Transactional
    public void searchPlaza() throws Exception {
        // Initialize the database
        plazaRepository.saveAndFlush(plaza);
        when(mockPlazaSearchRepository.search(queryStringQuery("id:" + plaza.getId())))
            .thenReturn(Collections.singletonList(plaza));
        // Search the plaza
        restPlazaMockMvc.perform(get("/api/_search/plazas?query=id:" + plaza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaza.getId().intValue())))
            .andExpect(jsonPath("$.[*].horas").value(hasItem(DEFAULT_HORAS)))
            .andExpect(jsonPath("$.[*].fechaini").value(hasItem(DEFAULT_FECHAINI.toString())))
            .andExpect(jsonPath("$.[*].fechafin").value(hasItem(DEFAULT_FECHAFIN.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plaza.class);
        Plaza plaza1 = new Plaza();
        plaza1.setId(1L);
        Plaza plaza2 = new Plaza();
        plaza2.setId(plaza1.getId());
        assertThat(plaza1).isEqualTo(plaza2);
        plaza2.setId(2L);
        assertThat(plaza1).isNotEqualTo(plaza2);
        plaza1.setId(null);
        assertThat(plaza1).isNotEqualTo(plaza2);
    }
}
