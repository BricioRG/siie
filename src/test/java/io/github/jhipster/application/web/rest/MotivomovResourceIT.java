package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Motivomov;
import io.github.jhipster.application.repository.MotivomovRepository;
import io.github.jhipster.application.repository.search.MotivomovSearchRepository;
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
 * Integration tests for the {@link MotivomovResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class MotivomovResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private MotivomovRepository motivomovRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.MotivomovSearchRepositoryMockConfiguration
     */
    @Autowired
    private MotivomovSearchRepository mockMotivomovSearchRepository;

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

    private MockMvc restMotivomovMockMvc;

    private Motivomov motivomov;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MotivomovResource motivomovResource = new MotivomovResource(motivomovRepository, mockMotivomovSearchRepository);
        this.restMotivomovMockMvc = MockMvcBuilders.standaloneSetup(motivomovResource)
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
    public static Motivomov createEntity(EntityManager em) {
        Motivomov motivomov = new Motivomov()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION);
        return motivomov;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Motivomov createUpdatedEntity(EntityManager em) {
        Motivomov motivomov = new Motivomov()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);
        return motivomov;
    }

    @BeforeEach
    public void initTest() {
        motivomov = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotivomov() throws Exception {
        int databaseSizeBeforeCreate = motivomovRepository.findAll().size();

        // Create the Motivomov
        restMotivomovMockMvc.perform(post("/api/motivomovs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivomov)))
            .andExpect(status().isCreated());

        // Validate the Motivomov in the database
        List<Motivomov> motivomovList = motivomovRepository.findAll();
        assertThat(motivomovList).hasSize(databaseSizeBeforeCreate + 1);
        Motivomov testMotivomov = motivomovList.get(motivomovList.size() - 1);
        assertThat(testMotivomov.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testMotivomov.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);

        // Validate the Motivomov in Elasticsearch
        verify(mockMotivomovSearchRepository, times(1)).save(testMotivomov);
    }

    @Test
    @Transactional
    public void createMotivomovWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motivomovRepository.findAll().size();

        // Create the Motivomov with an existing ID
        motivomov.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotivomovMockMvc.perform(post("/api/motivomovs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivomov)))
            .andExpect(status().isBadRequest());

        // Validate the Motivomov in the database
        List<Motivomov> motivomovList = motivomovRepository.findAll();
        assertThat(motivomovList).hasSize(databaseSizeBeforeCreate);

        // Validate the Motivomov in Elasticsearch
        verify(mockMotivomovSearchRepository, times(0)).save(motivomov);
    }


    @Test
    @Transactional
    public void getAllMotivomovs() throws Exception {
        // Initialize the database
        motivomovRepository.saveAndFlush(motivomov);

        // Get all the motivomovList
        restMotivomovMockMvc.perform(get("/api/motivomovs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivomov.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getMotivomov() throws Exception {
        // Initialize the database
        motivomovRepository.saveAndFlush(motivomov);

        // Get the motivomov
        restMotivomovMockMvc.perform(get("/api/motivomovs/{id}", motivomov.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(motivomov.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMotivomov() throws Exception {
        // Get the motivomov
        restMotivomovMockMvc.perform(get("/api/motivomovs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotivomov() throws Exception {
        // Initialize the database
        motivomovRepository.saveAndFlush(motivomov);

        int databaseSizeBeforeUpdate = motivomovRepository.findAll().size();

        // Update the motivomov
        Motivomov updatedMotivomov = motivomovRepository.findById(motivomov.getId()).get();
        // Disconnect from session so that the updates on updatedMotivomov are not directly saved in db
        em.detach(updatedMotivomov);
        updatedMotivomov
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);

        restMotivomovMockMvc.perform(put("/api/motivomovs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMotivomov)))
            .andExpect(status().isOk());

        // Validate the Motivomov in the database
        List<Motivomov> motivomovList = motivomovRepository.findAll();
        assertThat(motivomovList).hasSize(databaseSizeBeforeUpdate);
        Motivomov testMotivomov = motivomovList.get(motivomovList.size() - 1);
        assertThat(testMotivomov.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testMotivomov.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);

        // Validate the Motivomov in Elasticsearch
        verify(mockMotivomovSearchRepository, times(1)).save(testMotivomov);
    }

    @Test
    @Transactional
    public void updateNonExistingMotivomov() throws Exception {
        int databaseSizeBeforeUpdate = motivomovRepository.findAll().size();

        // Create the Motivomov

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotivomovMockMvc.perform(put("/api/motivomovs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivomov)))
            .andExpect(status().isBadRequest());

        // Validate the Motivomov in the database
        List<Motivomov> motivomovList = motivomovRepository.findAll();
        assertThat(motivomovList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Motivomov in Elasticsearch
        verify(mockMotivomovSearchRepository, times(0)).save(motivomov);
    }

    @Test
    @Transactional
    public void deleteMotivomov() throws Exception {
        // Initialize the database
        motivomovRepository.saveAndFlush(motivomov);

        int databaseSizeBeforeDelete = motivomovRepository.findAll().size();

        // Delete the motivomov
        restMotivomovMockMvc.perform(delete("/api/motivomovs/{id}", motivomov.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Motivomov> motivomovList = motivomovRepository.findAll();
        assertThat(motivomovList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Motivomov in Elasticsearch
        verify(mockMotivomovSearchRepository, times(1)).deleteById(motivomov.getId());
    }

    @Test
    @Transactional
    public void searchMotivomov() throws Exception {
        // Initialize the database
        motivomovRepository.saveAndFlush(motivomov);
        when(mockMotivomovSearchRepository.search(queryStringQuery("id:" + motivomov.getId())))
            .thenReturn(Collections.singletonList(motivomov));
        // Search the motivomov
        restMotivomovMockMvc.perform(get("/api/_search/motivomovs?query=id:" + motivomov.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivomov.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Motivomov.class);
        Motivomov motivomov1 = new Motivomov();
        motivomov1.setId(1L);
        Motivomov motivomov2 = new Motivomov();
        motivomov2.setId(motivomov1.getId());
        assertThat(motivomov1).isEqualTo(motivomov2);
        motivomov2.setId(2L);
        assertThat(motivomov1).isNotEqualTo(motivomov2);
        motivomov1.setId(null);
        assertThat(motivomov1).isNotEqualTo(motivomov2);
    }
}
