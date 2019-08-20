package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Escuela;
import io.github.jhipster.application.repository.EscuelaRepository;
import io.github.jhipster.application.repository.search.EscuelaSearchRepository;
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
 * Integration tests for the {@link EscuelaResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class EscuelaResourceIT {

    private static final String DEFAULT_CCT = "AAAAAAAAAA";
    private static final String UPDATED_CCT = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private EscuelaRepository escuelaRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.EscuelaSearchRepositoryMockConfiguration
     */
    @Autowired
    private EscuelaSearchRepository mockEscuelaSearchRepository;

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

    private MockMvc restEscuelaMockMvc;

    private Escuela escuela;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EscuelaResource escuelaResource = new EscuelaResource(escuelaRepository, mockEscuelaSearchRepository);
        this.restEscuelaMockMvc = MockMvcBuilders.standaloneSetup(escuelaResource)
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
    public static Escuela createEntity(EntityManager em) {
        Escuela escuela = new Escuela()
            .cct(DEFAULT_CCT)
            .nombre(DEFAULT_NOMBRE);
        return escuela;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Escuela createUpdatedEntity(EntityManager em) {
        Escuela escuela = new Escuela()
            .cct(UPDATED_CCT)
            .nombre(UPDATED_NOMBRE);
        return escuela;
    }

    @BeforeEach
    public void initTest() {
        escuela = createEntity(em);
    }

    @Test
    @Transactional
    public void createEscuela() throws Exception {
        int databaseSizeBeforeCreate = escuelaRepository.findAll().size();

        // Create the Escuela
        restEscuelaMockMvc.perform(post("/api/escuelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escuela)))
            .andExpect(status().isCreated());

        // Validate the Escuela in the database
        List<Escuela> escuelaList = escuelaRepository.findAll();
        assertThat(escuelaList).hasSize(databaseSizeBeforeCreate + 1);
        Escuela testEscuela = escuelaList.get(escuelaList.size() - 1);
        assertThat(testEscuela.getCct()).isEqualTo(DEFAULT_CCT);
        assertThat(testEscuela.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the Escuela in Elasticsearch
        verify(mockEscuelaSearchRepository, times(1)).save(testEscuela);
    }

    @Test
    @Transactional
    public void createEscuelaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = escuelaRepository.findAll().size();

        // Create the Escuela with an existing ID
        escuela.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscuelaMockMvc.perform(post("/api/escuelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escuela)))
            .andExpect(status().isBadRequest());

        // Validate the Escuela in the database
        List<Escuela> escuelaList = escuelaRepository.findAll();
        assertThat(escuelaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Escuela in Elasticsearch
        verify(mockEscuelaSearchRepository, times(0)).save(escuela);
    }


    @Test
    @Transactional
    public void getAllEscuelas() throws Exception {
        // Initialize the database
        escuelaRepository.saveAndFlush(escuela);

        // Get all the escuelaList
        restEscuelaMockMvc.perform(get("/api/escuelas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escuela.getId().intValue())))
            .andExpect(jsonPath("$.[*].cct").value(hasItem(DEFAULT_CCT.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getEscuela() throws Exception {
        // Initialize the database
        escuelaRepository.saveAndFlush(escuela);

        // Get the escuela
        restEscuelaMockMvc.perform(get("/api/escuelas/{id}", escuela.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(escuela.getId().intValue()))
            .andExpect(jsonPath("$.cct").value(DEFAULT_CCT.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEscuela() throws Exception {
        // Get the escuela
        restEscuelaMockMvc.perform(get("/api/escuelas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEscuela() throws Exception {
        // Initialize the database
        escuelaRepository.saveAndFlush(escuela);

        int databaseSizeBeforeUpdate = escuelaRepository.findAll().size();

        // Update the escuela
        Escuela updatedEscuela = escuelaRepository.findById(escuela.getId()).get();
        // Disconnect from session so that the updates on updatedEscuela are not directly saved in db
        em.detach(updatedEscuela);
        updatedEscuela
            .cct(UPDATED_CCT)
            .nombre(UPDATED_NOMBRE);

        restEscuelaMockMvc.perform(put("/api/escuelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEscuela)))
            .andExpect(status().isOk());

        // Validate the Escuela in the database
        List<Escuela> escuelaList = escuelaRepository.findAll();
        assertThat(escuelaList).hasSize(databaseSizeBeforeUpdate);
        Escuela testEscuela = escuelaList.get(escuelaList.size() - 1);
        assertThat(testEscuela.getCct()).isEqualTo(UPDATED_CCT);
        assertThat(testEscuela.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the Escuela in Elasticsearch
        verify(mockEscuelaSearchRepository, times(1)).save(testEscuela);
    }

    @Test
    @Transactional
    public void updateNonExistingEscuela() throws Exception {
        int databaseSizeBeforeUpdate = escuelaRepository.findAll().size();

        // Create the Escuela

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscuelaMockMvc.perform(put("/api/escuelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escuela)))
            .andExpect(status().isBadRequest());

        // Validate the Escuela in the database
        List<Escuela> escuelaList = escuelaRepository.findAll();
        assertThat(escuelaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Escuela in Elasticsearch
        verify(mockEscuelaSearchRepository, times(0)).save(escuela);
    }

    @Test
    @Transactional
    public void deleteEscuela() throws Exception {
        // Initialize the database
        escuelaRepository.saveAndFlush(escuela);

        int databaseSizeBeforeDelete = escuelaRepository.findAll().size();

        // Delete the escuela
        restEscuelaMockMvc.perform(delete("/api/escuelas/{id}", escuela.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Escuela> escuelaList = escuelaRepository.findAll();
        assertThat(escuelaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Escuela in Elasticsearch
        verify(mockEscuelaSearchRepository, times(1)).deleteById(escuela.getId());
    }

    @Test
    @Transactional
    public void searchEscuela() throws Exception {
        // Initialize the database
        escuelaRepository.saveAndFlush(escuela);
        when(mockEscuelaSearchRepository.search(queryStringQuery("id:" + escuela.getId())))
            .thenReturn(Collections.singletonList(escuela));
        // Search the escuela
        restEscuelaMockMvc.perform(get("/api/_search/escuelas?query=id:" + escuela.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escuela.getId().intValue())))
            .andExpect(jsonPath("$.[*].cct").value(hasItem(DEFAULT_CCT)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Escuela.class);
        Escuela escuela1 = new Escuela();
        escuela1.setId(1L);
        Escuela escuela2 = new Escuela();
        escuela2.setId(escuela1.getId());
        assertThat(escuela1).isEqualTo(escuela2);
        escuela2.setId(2L);
        assertThat(escuela1).isNotEqualTo(escuela2);
        escuela1.setId(null);
        assertThat(escuela1).isNotEqualTo(escuela2);
    }
}
