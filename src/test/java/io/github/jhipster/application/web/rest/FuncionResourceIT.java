package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Funcion;
import io.github.jhipster.application.repository.FuncionRepository;
import io.github.jhipster.application.repository.search.FuncionSearchRepository;
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
 * Integration tests for the {@link FuncionResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class FuncionResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private FuncionRepository funcionRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.FuncionSearchRepositoryMockConfiguration
     */
    @Autowired
    private FuncionSearchRepository mockFuncionSearchRepository;

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

    private MockMvc restFuncionMockMvc;

    private Funcion funcion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuncionResource funcionResource = new FuncionResource(funcionRepository, mockFuncionSearchRepository);
        this.restFuncionMockMvc = MockMvcBuilders.standaloneSetup(funcionResource)
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
    public static Funcion createEntity(EntityManager em) {
        Funcion funcion = new Funcion()
            .nombre(DEFAULT_NOMBRE);
        return funcion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcion createUpdatedEntity(EntityManager em) {
        Funcion funcion = new Funcion()
            .nombre(UPDATED_NOMBRE);
        return funcion;
    }

    @BeforeEach
    public void initTest() {
        funcion = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuncion() throws Exception {
        int databaseSizeBeforeCreate = funcionRepository.findAll().size();

        // Create the Funcion
        restFuncionMockMvc.perform(post("/api/funcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcion)))
            .andExpect(status().isCreated());

        // Validate the Funcion in the database
        List<Funcion> funcionList = funcionRepository.findAll();
        assertThat(funcionList).hasSize(databaseSizeBeforeCreate + 1);
        Funcion testFuncion = funcionList.get(funcionList.size() - 1);
        assertThat(testFuncion.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the Funcion in Elasticsearch
        verify(mockFuncionSearchRepository, times(1)).save(testFuncion);
    }

    @Test
    @Transactional
    public void createFuncionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = funcionRepository.findAll().size();

        // Create the Funcion with an existing ID
        funcion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionMockMvc.perform(post("/api/funcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcion)))
            .andExpect(status().isBadRequest());

        // Validate the Funcion in the database
        List<Funcion> funcionList = funcionRepository.findAll();
        assertThat(funcionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Funcion in Elasticsearch
        verify(mockFuncionSearchRepository, times(0)).save(funcion);
    }


    @Test
    @Transactional
    public void getAllFuncions() throws Exception {
        // Initialize the database
        funcionRepository.saveAndFlush(funcion);

        // Get all the funcionList
        restFuncionMockMvc.perform(get("/api/funcions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getFuncion() throws Exception {
        // Initialize the database
        funcionRepository.saveAndFlush(funcion);

        // Get the funcion
        restFuncionMockMvc.perform(get("/api/funcions/{id}", funcion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(funcion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFuncion() throws Exception {
        // Get the funcion
        restFuncionMockMvc.perform(get("/api/funcions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuncion() throws Exception {
        // Initialize the database
        funcionRepository.saveAndFlush(funcion);

        int databaseSizeBeforeUpdate = funcionRepository.findAll().size();

        // Update the funcion
        Funcion updatedFuncion = funcionRepository.findById(funcion.getId()).get();
        // Disconnect from session so that the updates on updatedFuncion are not directly saved in db
        em.detach(updatedFuncion);
        updatedFuncion
            .nombre(UPDATED_NOMBRE);

        restFuncionMockMvc.perform(put("/api/funcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFuncion)))
            .andExpect(status().isOk());

        // Validate the Funcion in the database
        List<Funcion> funcionList = funcionRepository.findAll();
        assertThat(funcionList).hasSize(databaseSizeBeforeUpdate);
        Funcion testFuncion = funcionList.get(funcionList.size() - 1);
        assertThat(testFuncion.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the Funcion in Elasticsearch
        verify(mockFuncionSearchRepository, times(1)).save(testFuncion);
    }

    @Test
    @Transactional
    public void updateNonExistingFuncion() throws Exception {
        int databaseSizeBeforeUpdate = funcionRepository.findAll().size();

        // Create the Funcion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionMockMvc.perform(put("/api/funcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcion)))
            .andExpect(status().isBadRequest());

        // Validate the Funcion in the database
        List<Funcion> funcionList = funcionRepository.findAll();
        assertThat(funcionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Funcion in Elasticsearch
        verify(mockFuncionSearchRepository, times(0)).save(funcion);
    }

    @Test
    @Transactional
    public void deleteFuncion() throws Exception {
        // Initialize the database
        funcionRepository.saveAndFlush(funcion);

        int databaseSizeBeforeDelete = funcionRepository.findAll().size();

        // Delete the funcion
        restFuncionMockMvc.perform(delete("/api/funcions/{id}", funcion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Funcion> funcionList = funcionRepository.findAll();
        assertThat(funcionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Funcion in Elasticsearch
        verify(mockFuncionSearchRepository, times(1)).deleteById(funcion.getId());
    }

    @Test
    @Transactional
    public void searchFuncion() throws Exception {
        // Initialize the database
        funcionRepository.saveAndFlush(funcion);
        when(mockFuncionSearchRepository.search(queryStringQuery("id:" + funcion.getId())))
            .thenReturn(Collections.singletonList(funcion));
        // Search the funcion
        restFuncionMockMvc.perform(get("/api/_search/funcions?query=id:" + funcion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcion.class);
        Funcion funcion1 = new Funcion();
        funcion1.setId(1L);
        Funcion funcion2 = new Funcion();
        funcion2.setId(funcion1.getId());
        assertThat(funcion1).isEqualTo(funcion2);
        funcion2.setId(2L);
        assertThat(funcion1).isNotEqualTo(funcion2);
        funcion1.setId(null);
        assertThat(funcion1).isNotEqualTo(funcion2);
    }
}
