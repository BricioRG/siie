package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Jornada;
import io.github.jhipster.application.repository.JornadaRepository;
import io.github.jhipster.application.repository.search.JornadaSearchRepository;
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
 * Integration tests for the {@link JornadaResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class JornadaResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private JornadaRepository jornadaRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.JornadaSearchRepositoryMockConfiguration
     */
    @Autowired
    private JornadaSearchRepository mockJornadaSearchRepository;

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

    private MockMvc restJornadaMockMvc;

    private Jornada jornada;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JornadaResource jornadaResource = new JornadaResource(jornadaRepository, mockJornadaSearchRepository);
        this.restJornadaMockMvc = MockMvcBuilders.standaloneSetup(jornadaResource)
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
    public static Jornada createEntity(EntityManager em) {
        Jornada jornada = new Jornada()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION);
        return jornada;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jornada createUpdatedEntity(EntityManager em) {
        Jornada jornada = new Jornada()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);
        return jornada;
    }

    @BeforeEach
    public void initTest() {
        jornada = createEntity(em);
    }

    @Test
    @Transactional
    public void createJornada() throws Exception {
        int databaseSizeBeforeCreate = jornadaRepository.findAll().size();

        // Create the Jornada
        restJornadaMockMvc.perform(post("/api/jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jornada)))
            .andExpect(status().isCreated());

        // Validate the Jornada in the database
        List<Jornada> jornadaList = jornadaRepository.findAll();
        assertThat(jornadaList).hasSize(databaseSizeBeforeCreate + 1);
        Jornada testJornada = jornadaList.get(jornadaList.size() - 1);
        assertThat(testJornada.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testJornada.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);

        // Validate the Jornada in Elasticsearch
        verify(mockJornadaSearchRepository, times(1)).save(testJornada);
    }

    @Test
    @Transactional
    public void createJornadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jornadaRepository.findAll().size();

        // Create the Jornada with an existing ID
        jornada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJornadaMockMvc.perform(post("/api/jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jornada)))
            .andExpect(status().isBadRequest());

        // Validate the Jornada in the database
        List<Jornada> jornadaList = jornadaRepository.findAll();
        assertThat(jornadaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Jornada in Elasticsearch
        verify(mockJornadaSearchRepository, times(0)).save(jornada);
    }


    @Test
    @Transactional
    public void getAllJornadas() throws Exception {
        // Initialize the database
        jornadaRepository.saveAndFlush(jornada);

        // Get all the jornadaList
        restJornadaMockMvc.perform(get("/api/jornadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jornada.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getJornada() throws Exception {
        // Initialize the database
        jornadaRepository.saveAndFlush(jornada);

        // Get the jornada
        restJornadaMockMvc.perform(get("/api/jornadas/{id}", jornada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jornada.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJornada() throws Exception {
        // Get the jornada
        restJornadaMockMvc.perform(get("/api/jornadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJornada() throws Exception {
        // Initialize the database
        jornadaRepository.saveAndFlush(jornada);

        int databaseSizeBeforeUpdate = jornadaRepository.findAll().size();

        // Update the jornada
        Jornada updatedJornada = jornadaRepository.findById(jornada.getId()).get();
        // Disconnect from session so that the updates on updatedJornada are not directly saved in db
        em.detach(updatedJornada);
        updatedJornada
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);

        restJornadaMockMvc.perform(put("/api/jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJornada)))
            .andExpect(status().isOk());

        // Validate the Jornada in the database
        List<Jornada> jornadaList = jornadaRepository.findAll();
        assertThat(jornadaList).hasSize(databaseSizeBeforeUpdate);
        Jornada testJornada = jornadaList.get(jornadaList.size() - 1);
        assertThat(testJornada.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testJornada.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);

        // Validate the Jornada in Elasticsearch
        verify(mockJornadaSearchRepository, times(1)).save(testJornada);
    }

    @Test
    @Transactional
    public void updateNonExistingJornada() throws Exception {
        int databaseSizeBeforeUpdate = jornadaRepository.findAll().size();

        // Create the Jornada

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJornadaMockMvc.perform(put("/api/jornadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jornada)))
            .andExpect(status().isBadRequest());

        // Validate the Jornada in the database
        List<Jornada> jornadaList = jornadaRepository.findAll();
        assertThat(jornadaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Jornada in Elasticsearch
        verify(mockJornadaSearchRepository, times(0)).save(jornada);
    }

    @Test
    @Transactional
    public void deleteJornada() throws Exception {
        // Initialize the database
        jornadaRepository.saveAndFlush(jornada);

        int databaseSizeBeforeDelete = jornadaRepository.findAll().size();

        // Delete the jornada
        restJornadaMockMvc.perform(delete("/api/jornadas/{id}", jornada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jornada> jornadaList = jornadaRepository.findAll();
        assertThat(jornadaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Jornada in Elasticsearch
        verify(mockJornadaSearchRepository, times(1)).deleteById(jornada.getId());
    }

    @Test
    @Transactional
    public void searchJornada() throws Exception {
        // Initialize the database
        jornadaRepository.saveAndFlush(jornada);
        when(mockJornadaSearchRepository.search(queryStringQuery("id:" + jornada.getId())))
            .thenReturn(Collections.singletonList(jornada));
        // Search the jornada
        restJornadaMockMvc.perform(get("/api/_search/jornadas?query=id:" + jornada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jornada.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jornada.class);
        Jornada jornada1 = new Jornada();
        jornada1.setId(1L);
        Jornada jornada2 = new Jornada();
        jornada2.setId(jornada1.getId());
        assertThat(jornada1).isEqualTo(jornada2);
        jornada2.setId(2L);
        assertThat(jornada1).isNotEqualTo(jornada2);
        jornada1.setId(null);
        assertThat(jornada1).isNotEqualTo(jornada2);
    }
}
