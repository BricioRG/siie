package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Funcionlaboral;
import io.github.jhipster.application.repository.FuncionlaboralRepository;
import io.github.jhipster.application.repository.search.FuncionlaboralSearchRepository;
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
 * Integration tests for the {@link FuncionlaboralResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class FuncionlaboralResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private FuncionlaboralRepository funcionlaboralRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.FuncionlaboralSearchRepositoryMockConfiguration
     */
    @Autowired
    private FuncionlaboralSearchRepository mockFuncionlaboralSearchRepository;

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

    private MockMvc restFuncionlaboralMockMvc;

    private Funcionlaboral funcionlaboral;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuncionlaboralResource funcionlaboralResource = new FuncionlaboralResource(funcionlaboralRepository, mockFuncionlaboralSearchRepository);
        this.restFuncionlaboralMockMvc = MockMvcBuilders.standaloneSetup(funcionlaboralResource)
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
    public static Funcionlaboral createEntity(EntityManager em) {
        Funcionlaboral funcionlaboral = new Funcionlaboral()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION);
        return funcionlaboral;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionlaboral createUpdatedEntity(EntityManager em) {
        Funcionlaboral funcionlaboral = new Funcionlaboral()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);
        return funcionlaboral;
    }

    @BeforeEach
    public void initTest() {
        funcionlaboral = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuncionlaboral() throws Exception {
        int databaseSizeBeforeCreate = funcionlaboralRepository.findAll().size();

        // Create the Funcionlaboral
        restFuncionlaboralMockMvc.perform(post("/api/funcionlaborals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionlaboral)))
            .andExpect(status().isCreated());

        // Validate the Funcionlaboral in the database
        List<Funcionlaboral> funcionlaboralList = funcionlaboralRepository.findAll();
        assertThat(funcionlaboralList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionlaboral testFuncionlaboral = funcionlaboralList.get(funcionlaboralList.size() - 1);
        assertThat(testFuncionlaboral.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testFuncionlaboral.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);

        // Validate the Funcionlaboral in Elasticsearch
        verify(mockFuncionlaboralSearchRepository, times(1)).save(testFuncionlaboral);
    }

    @Test
    @Transactional
    public void createFuncionlaboralWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = funcionlaboralRepository.findAll().size();

        // Create the Funcionlaboral with an existing ID
        funcionlaboral.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionlaboralMockMvc.perform(post("/api/funcionlaborals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionlaboral)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionlaboral in the database
        List<Funcionlaboral> funcionlaboralList = funcionlaboralRepository.findAll();
        assertThat(funcionlaboralList).hasSize(databaseSizeBeforeCreate);

        // Validate the Funcionlaboral in Elasticsearch
        verify(mockFuncionlaboralSearchRepository, times(0)).save(funcionlaboral);
    }


    @Test
    @Transactional
    public void getAllFuncionlaborals() throws Exception {
        // Initialize the database
        funcionlaboralRepository.saveAndFlush(funcionlaboral);

        // Get all the funcionlaboralList
        restFuncionlaboralMockMvc.perform(get("/api/funcionlaborals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionlaboral.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getFuncionlaboral() throws Exception {
        // Initialize the database
        funcionlaboralRepository.saveAndFlush(funcionlaboral);

        // Get the funcionlaboral
        restFuncionlaboralMockMvc.perform(get("/api/funcionlaborals/{id}", funcionlaboral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(funcionlaboral.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFuncionlaboral() throws Exception {
        // Get the funcionlaboral
        restFuncionlaboralMockMvc.perform(get("/api/funcionlaborals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuncionlaboral() throws Exception {
        // Initialize the database
        funcionlaboralRepository.saveAndFlush(funcionlaboral);

        int databaseSizeBeforeUpdate = funcionlaboralRepository.findAll().size();

        // Update the funcionlaboral
        Funcionlaboral updatedFuncionlaboral = funcionlaboralRepository.findById(funcionlaboral.getId()).get();
        // Disconnect from session so that the updates on updatedFuncionlaboral are not directly saved in db
        em.detach(updatedFuncionlaboral);
        updatedFuncionlaboral
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);

        restFuncionlaboralMockMvc.perform(put("/api/funcionlaborals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFuncionlaboral)))
            .andExpect(status().isOk());

        // Validate the Funcionlaboral in the database
        List<Funcionlaboral> funcionlaboralList = funcionlaboralRepository.findAll();
        assertThat(funcionlaboralList).hasSize(databaseSizeBeforeUpdate);
        Funcionlaboral testFuncionlaboral = funcionlaboralList.get(funcionlaboralList.size() - 1);
        assertThat(testFuncionlaboral.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testFuncionlaboral.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);

        // Validate the Funcionlaboral in Elasticsearch
        verify(mockFuncionlaboralSearchRepository, times(1)).save(testFuncionlaboral);
    }

    @Test
    @Transactional
    public void updateNonExistingFuncionlaboral() throws Exception {
        int databaseSizeBeforeUpdate = funcionlaboralRepository.findAll().size();

        // Create the Funcionlaboral

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionlaboralMockMvc.perform(put("/api/funcionlaborals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionlaboral)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionlaboral in the database
        List<Funcionlaboral> funcionlaboralList = funcionlaboralRepository.findAll();
        assertThat(funcionlaboralList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Funcionlaboral in Elasticsearch
        verify(mockFuncionlaboralSearchRepository, times(0)).save(funcionlaboral);
    }

    @Test
    @Transactional
    public void deleteFuncionlaboral() throws Exception {
        // Initialize the database
        funcionlaboralRepository.saveAndFlush(funcionlaboral);

        int databaseSizeBeforeDelete = funcionlaboralRepository.findAll().size();

        // Delete the funcionlaboral
        restFuncionlaboralMockMvc.perform(delete("/api/funcionlaborals/{id}", funcionlaboral.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Funcionlaboral> funcionlaboralList = funcionlaboralRepository.findAll();
        assertThat(funcionlaboralList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Funcionlaboral in Elasticsearch
        verify(mockFuncionlaboralSearchRepository, times(1)).deleteById(funcionlaboral.getId());
    }

    @Test
    @Transactional
    public void searchFuncionlaboral() throws Exception {
        // Initialize the database
        funcionlaboralRepository.saveAndFlush(funcionlaboral);
        when(mockFuncionlaboralSearchRepository.search(queryStringQuery("id:" + funcionlaboral.getId())))
            .thenReturn(Collections.singletonList(funcionlaboral));
        // Search the funcionlaboral
        restFuncionlaboralMockMvc.perform(get("/api/_search/funcionlaborals?query=id:" + funcionlaboral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionlaboral.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionlaboral.class);
        Funcionlaboral funcionlaboral1 = new Funcionlaboral();
        funcionlaboral1.setId(1L);
        Funcionlaboral funcionlaboral2 = new Funcionlaboral();
        funcionlaboral2.setId(funcionlaboral1.getId());
        assertThat(funcionlaboral1).isEqualTo(funcionlaboral2);
        funcionlaboral2.setId(2L);
        assertThat(funcionlaboral1).isNotEqualTo(funcionlaboral2);
        funcionlaboral1.setId(null);
        assertThat(funcionlaboral1).isNotEqualTo(funcionlaboral2);
    }
}
