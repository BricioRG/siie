package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Financiamiento;
import io.github.jhipster.application.repository.FinanciamientoRepository;
import io.github.jhipster.application.repository.search.FinanciamientoSearchRepository;
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
 * Integration tests for the {@link FinanciamientoResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class FinanciamientoResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private FinanciamientoRepository financiamientoRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.FinanciamientoSearchRepositoryMockConfiguration
     */
    @Autowired
    private FinanciamientoSearchRepository mockFinanciamientoSearchRepository;

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

    private MockMvc restFinanciamientoMockMvc;

    private Financiamiento financiamiento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinanciamientoResource financiamientoResource = new FinanciamientoResource(financiamientoRepository, mockFinanciamientoSearchRepository);
        this.restFinanciamientoMockMvc = MockMvcBuilders.standaloneSetup(financiamientoResource)
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
    public static Financiamiento createEntity(EntityManager em) {
        Financiamiento financiamiento = new Financiamiento()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION);
        return financiamiento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Financiamiento createUpdatedEntity(EntityManager em) {
        Financiamiento financiamiento = new Financiamiento()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);
        return financiamiento;
    }

    @BeforeEach
    public void initTest() {
        financiamiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinanciamiento() throws Exception {
        int databaseSizeBeforeCreate = financiamientoRepository.findAll().size();

        // Create the Financiamiento
        restFinanciamientoMockMvc.perform(post("/api/financiamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financiamiento)))
            .andExpect(status().isCreated());

        // Validate the Financiamiento in the database
        List<Financiamiento> financiamientoList = financiamientoRepository.findAll();
        assertThat(financiamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Financiamiento testFinanciamiento = financiamientoList.get(financiamientoList.size() - 1);
        assertThat(testFinanciamiento.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testFinanciamiento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);

        // Validate the Financiamiento in Elasticsearch
        verify(mockFinanciamientoSearchRepository, times(1)).save(testFinanciamiento);
    }

    @Test
    @Transactional
    public void createFinanciamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financiamientoRepository.findAll().size();

        // Create the Financiamiento with an existing ID
        financiamiento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanciamientoMockMvc.perform(post("/api/financiamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financiamiento)))
            .andExpect(status().isBadRequest());

        // Validate the Financiamiento in the database
        List<Financiamiento> financiamientoList = financiamientoRepository.findAll();
        assertThat(financiamientoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Financiamiento in Elasticsearch
        verify(mockFinanciamientoSearchRepository, times(0)).save(financiamiento);
    }


    @Test
    @Transactional
    public void getAllFinanciamientos() throws Exception {
        // Initialize the database
        financiamientoRepository.saveAndFlush(financiamiento);

        // Get all the financiamientoList
        restFinanciamientoMockMvc.perform(get("/api/financiamientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financiamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getFinanciamiento() throws Exception {
        // Initialize the database
        financiamientoRepository.saveAndFlush(financiamiento);

        // Get the financiamiento
        restFinanciamientoMockMvc.perform(get("/api/financiamientos/{id}", financiamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financiamiento.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFinanciamiento() throws Exception {
        // Get the financiamiento
        restFinanciamientoMockMvc.perform(get("/api/financiamientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinanciamiento() throws Exception {
        // Initialize the database
        financiamientoRepository.saveAndFlush(financiamiento);

        int databaseSizeBeforeUpdate = financiamientoRepository.findAll().size();

        // Update the financiamiento
        Financiamiento updatedFinanciamiento = financiamientoRepository.findById(financiamiento.getId()).get();
        // Disconnect from session so that the updates on updatedFinanciamiento are not directly saved in db
        em.detach(updatedFinanciamiento);
        updatedFinanciamiento
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);

        restFinanciamientoMockMvc.perform(put("/api/financiamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinanciamiento)))
            .andExpect(status().isOk());

        // Validate the Financiamiento in the database
        List<Financiamiento> financiamientoList = financiamientoRepository.findAll();
        assertThat(financiamientoList).hasSize(databaseSizeBeforeUpdate);
        Financiamiento testFinanciamiento = financiamientoList.get(financiamientoList.size() - 1);
        assertThat(testFinanciamiento.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testFinanciamiento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);

        // Validate the Financiamiento in Elasticsearch
        verify(mockFinanciamientoSearchRepository, times(1)).save(testFinanciamiento);
    }

    @Test
    @Transactional
    public void updateNonExistingFinanciamiento() throws Exception {
        int databaseSizeBeforeUpdate = financiamientoRepository.findAll().size();

        // Create the Financiamiento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanciamientoMockMvc.perform(put("/api/financiamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financiamiento)))
            .andExpect(status().isBadRequest());

        // Validate the Financiamiento in the database
        List<Financiamiento> financiamientoList = financiamientoRepository.findAll();
        assertThat(financiamientoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Financiamiento in Elasticsearch
        verify(mockFinanciamientoSearchRepository, times(0)).save(financiamiento);
    }

    @Test
    @Transactional
    public void deleteFinanciamiento() throws Exception {
        // Initialize the database
        financiamientoRepository.saveAndFlush(financiamiento);

        int databaseSizeBeforeDelete = financiamientoRepository.findAll().size();

        // Delete the financiamiento
        restFinanciamientoMockMvc.perform(delete("/api/financiamientos/{id}", financiamiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Financiamiento> financiamientoList = financiamientoRepository.findAll();
        assertThat(financiamientoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Financiamiento in Elasticsearch
        verify(mockFinanciamientoSearchRepository, times(1)).deleteById(financiamiento.getId());
    }

    @Test
    @Transactional
    public void searchFinanciamiento() throws Exception {
        // Initialize the database
        financiamientoRepository.saveAndFlush(financiamiento);
        when(mockFinanciamientoSearchRepository.search(queryStringQuery("id:" + financiamiento.getId())))
            .thenReturn(Collections.singletonList(financiamiento));
        // Search the financiamiento
        restFinanciamientoMockMvc.perform(get("/api/_search/financiamientos?query=id:" + financiamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financiamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Financiamiento.class);
        Financiamiento financiamiento1 = new Financiamiento();
        financiamiento1.setId(1L);
        Financiamiento financiamiento2 = new Financiamiento();
        financiamiento2.setId(financiamiento1.getId());
        assertThat(financiamiento1).isEqualTo(financiamiento2);
        financiamiento2.setId(2L);
        assertThat(financiamiento1).isNotEqualTo(financiamiento2);
        financiamiento1.setId(null);
        assertThat(financiamiento1).isNotEqualTo(financiamiento2);
    }
}
