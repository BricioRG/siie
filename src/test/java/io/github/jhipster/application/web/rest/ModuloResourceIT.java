package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Modulo;
import io.github.jhipster.application.repository.ModuloRepository;
import io.github.jhipster.application.repository.search.ModuloSearchRepository;
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
 * Integration tests for the {@link ModuloResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class ModuloResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private ModuloRepository moduloRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ModuloSearchRepositoryMockConfiguration
     */
    @Autowired
    private ModuloSearchRepository mockModuloSearchRepository;

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

    private MockMvc restModuloMockMvc;

    private Modulo modulo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModuloResource moduloResource = new ModuloResource(moduloRepository, mockModuloSearchRepository);
        this.restModuloMockMvc = MockMvcBuilders.standaloneSetup(moduloResource)
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
    public static Modulo createEntity(EntityManager em) {
        Modulo modulo = new Modulo()
            .nombre(DEFAULT_NOMBRE);
        return modulo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modulo createUpdatedEntity(EntityManager em) {
        Modulo modulo = new Modulo()
            .nombre(UPDATED_NOMBRE);
        return modulo;
    }

    @BeforeEach
    public void initTest() {
        modulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createModulo() throws Exception {
        int databaseSizeBeforeCreate = moduloRepository.findAll().size();

        // Create the Modulo
        restModuloMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulo)))
            .andExpect(status().isCreated());

        // Validate the Modulo in the database
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeCreate + 1);
        Modulo testModulo = moduloList.get(moduloList.size() - 1);
        assertThat(testModulo.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the Modulo in Elasticsearch
        verify(mockModuloSearchRepository, times(1)).save(testModulo);
    }

    @Test
    @Transactional
    public void createModuloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduloRepository.findAll().size();

        // Create the Modulo with an existing ID
        modulo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuloMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulo)))
            .andExpect(status().isBadRequest());

        // Validate the Modulo in the database
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeCreate);

        // Validate the Modulo in Elasticsearch
        verify(mockModuloSearchRepository, times(0)).save(modulo);
    }


    @Test
    @Transactional
    public void getAllModulos() throws Exception {
        // Initialize the database
        moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList
        restModuloMockMvc.perform(get("/api/modulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getModulo() throws Exception {
        // Initialize the database
        moduloRepository.saveAndFlush(modulo);

        // Get the modulo
        restModuloMockMvc.perform(get("/api/modulos/{id}", modulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modulo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModulo() throws Exception {
        // Get the modulo
        restModuloMockMvc.perform(get("/api/modulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModulo() throws Exception {
        // Initialize the database
        moduloRepository.saveAndFlush(modulo);

        int databaseSizeBeforeUpdate = moduloRepository.findAll().size();

        // Update the modulo
        Modulo updatedModulo = moduloRepository.findById(modulo.getId()).get();
        // Disconnect from session so that the updates on updatedModulo are not directly saved in db
        em.detach(updatedModulo);
        updatedModulo
            .nombre(UPDATED_NOMBRE);

        restModuloMockMvc.perform(put("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModulo)))
            .andExpect(status().isOk());

        // Validate the Modulo in the database
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeUpdate);
        Modulo testModulo = moduloList.get(moduloList.size() - 1);
        assertThat(testModulo.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the Modulo in Elasticsearch
        verify(mockModuloSearchRepository, times(1)).save(testModulo);
    }

    @Test
    @Transactional
    public void updateNonExistingModulo() throws Exception {
        int databaseSizeBeforeUpdate = moduloRepository.findAll().size();

        // Create the Modulo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuloMockMvc.perform(put("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulo)))
            .andExpect(status().isBadRequest());

        // Validate the Modulo in the database
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Modulo in Elasticsearch
        verify(mockModuloSearchRepository, times(0)).save(modulo);
    }

    @Test
    @Transactional
    public void deleteModulo() throws Exception {
        // Initialize the database
        moduloRepository.saveAndFlush(modulo);

        int databaseSizeBeforeDelete = moduloRepository.findAll().size();

        // Delete the modulo
        restModuloMockMvc.perform(delete("/api/modulos/{id}", modulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Modulo in Elasticsearch
        verify(mockModuloSearchRepository, times(1)).deleteById(modulo.getId());
    }

    @Test
    @Transactional
    public void searchModulo() throws Exception {
        // Initialize the database
        moduloRepository.saveAndFlush(modulo);
        when(mockModuloSearchRepository.search(queryStringQuery("id:" + modulo.getId())))
            .thenReturn(Collections.singletonList(modulo));
        // Search the modulo
        restModuloMockMvc.perform(get("/api/_search/modulos?query=id:" + modulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modulo.class);
        Modulo modulo1 = new Modulo();
        modulo1.setId(1L);
        Modulo modulo2 = new Modulo();
        modulo2.setId(modulo1.getId());
        assertThat(modulo1).isEqualTo(modulo2);
        modulo2.setId(2L);
        assertThat(modulo1).isNotEqualTo(modulo2);
        modulo1.setId(null);
        assertThat(modulo1).isNotEqualTo(modulo2);
    }
}
