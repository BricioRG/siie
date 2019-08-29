package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Escolaridad;
import io.github.jhipster.application.repository.EscolaridadRepository;
import io.github.jhipster.application.repository.search.EscolaridadSearchRepository;
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
 * Integration tests for the {@link EscolaridadResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class EscolaridadResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_PERIODO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONCLUYO = false;
    private static final Boolean UPDATED_CONCLUYO = true;

    private static final String DEFAULT_CLAVEDOC = "AAAAAAAAAA";
    private static final String UPDATED_CLAVEDOC = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    @Autowired
    private EscolaridadRepository escolaridadRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.EscolaridadSearchRepositoryMockConfiguration
     */
    @Autowired
    private EscolaridadSearchRepository mockEscolaridadSearchRepository;

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

    private MockMvc restEscolaridadMockMvc;

    private Escolaridad escolaridad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EscolaridadResource escolaridadResource = new EscolaridadResource(escolaridadRepository, mockEscolaridadSearchRepository);
        this.restEscolaridadMockMvc = MockMvcBuilders.standaloneSetup(escolaridadResource)
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
    public static Escolaridad createEntity(EntityManager em) {
        Escolaridad escolaridad = new Escolaridad()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .periodo(DEFAULT_PERIODO)
            .concluyo(DEFAULT_CONCLUYO)
            .clavedoc(DEFAULT_CLAVEDOC)
            .documento(DEFAULT_DOCUMENTO);
        return escolaridad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Escolaridad createUpdatedEntity(EntityManager em) {
        Escolaridad escolaridad = new Escolaridad()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .periodo(UPDATED_PERIODO)
            .concluyo(UPDATED_CONCLUYO)
            .clavedoc(UPDATED_CLAVEDOC)
            .documento(UPDATED_DOCUMENTO);
        return escolaridad;
    }

    @BeforeEach
    public void initTest() {
        escolaridad = createEntity(em);
    }

    @Test
    @Transactional
    public void createEscolaridad() throws Exception {
        int databaseSizeBeforeCreate = escolaridadRepository.findAll().size();

        // Create the Escolaridad
        restEscolaridadMockMvc.perform(post("/api/escolaridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaridad)))
            .andExpect(status().isCreated());

        // Validate the Escolaridad in the database
        List<Escolaridad> escolaridadList = escolaridadRepository.findAll();
        assertThat(escolaridadList).hasSize(databaseSizeBeforeCreate + 1);
        Escolaridad testEscolaridad = escolaridadList.get(escolaridadList.size() - 1);
        assertThat(testEscolaridad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEscolaridad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEscolaridad.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testEscolaridad.isConcluyo()).isEqualTo(DEFAULT_CONCLUYO);
        assertThat(testEscolaridad.getClavedoc()).isEqualTo(DEFAULT_CLAVEDOC);
        assertThat(testEscolaridad.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);

        // Validate the Escolaridad in Elasticsearch
        verify(mockEscolaridadSearchRepository, times(1)).save(testEscolaridad);
    }

    @Test
    @Transactional
    public void createEscolaridadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = escolaridadRepository.findAll().size();

        // Create the Escolaridad with an existing ID
        escolaridad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscolaridadMockMvc.perform(post("/api/escolaridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaridad)))
            .andExpect(status().isBadRequest());

        // Validate the Escolaridad in the database
        List<Escolaridad> escolaridadList = escolaridadRepository.findAll();
        assertThat(escolaridadList).hasSize(databaseSizeBeforeCreate);

        // Validate the Escolaridad in Elasticsearch
        verify(mockEscolaridadSearchRepository, times(0)).save(escolaridad);
    }


    @Test
    @Transactional
    public void getAllEscolaridads() throws Exception {
        // Initialize the database
        escolaridadRepository.saveAndFlush(escolaridad);

        // Get all the escolaridadList
        restEscolaridadMockMvc.perform(get("/api/escolaridads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escolaridad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())))
            .andExpect(jsonPath("$.[*].concluyo").value(hasItem(DEFAULT_CONCLUYO.booleanValue())))
            .andExpect(jsonPath("$.[*].clavedoc").value(hasItem(DEFAULT_CLAVEDOC.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getEscolaridad() throws Exception {
        // Initialize the database
        escolaridadRepository.saveAndFlush(escolaridad);

        // Get the escolaridad
        restEscolaridadMockMvc.perform(get("/api/escolaridads/{id}", escolaridad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(escolaridad.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.toString()))
            .andExpect(jsonPath("$.concluyo").value(DEFAULT_CONCLUYO.booleanValue()))
            .andExpect(jsonPath("$.clavedoc").value(DEFAULT_CLAVEDOC.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEscolaridad() throws Exception {
        // Get the escolaridad
        restEscolaridadMockMvc.perform(get("/api/escolaridads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEscolaridad() throws Exception {
        // Initialize the database
        escolaridadRepository.saveAndFlush(escolaridad);

        int databaseSizeBeforeUpdate = escolaridadRepository.findAll().size();

        // Update the escolaridad
        Escolaridad updatedEscolaridad = escolaridadRepository.findById(escolaridad.getId()).get();
        // Disconnect from session so that the updates on updatedEscolaridad are not directly saved in db
        em.detach(updatedEscolaridad);
        updatedEscolaridad
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .periodo(UPDATED_PERIODO)
            .concluyo(UPDATED_CONCLUYO)
            .clavedoc(UPDATED_CLAVEDOC)
            .documento(UPDATED_DOCUMENTO);

        restEscolaridadMockMvc.perform(put("/api/escolaridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEscolaridad)))
            .andExpect(status().isOk());

        // Validate the Escolaridad in the database
        List<Escolaridad> escolaridadList = escolaridadRepository.findAll();
        assertThat(escolaridadList).hasSize(databaseSizeBeforeUpdate);
        Escolaridad testEscolaridad = escolaridadList.get(escolaridadList.size() - 1);
        assertThat(testEscolaridad.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEscolaridad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEscolaridad.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testEscolaridad.isConcluyo()).isEqualTo(UPDATED_CONCLUYO);
        assertThat(testEscolaridad.getClavedoc()).isEqualTo(UPDATED_CLAVEDOC);
        assertThat(testEscolaridad.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);

        // Validate the Escolaridad in Elasticsearch
        verify(mockEscolaridadSearchRepository, times(1)).save(testEscolaridad);
    }

    @Test
    @Transactional
    public void updateNonExistingEscolaridad() throws Exception {
        int databaseSizeBeforeUpdate = escolaridadRepository.findAll().size();

        // Create the Escolaridad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscolaridadMockMvc.perform(put("/api/escolaridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaridad)))
            .andExpect(status().isBadRequest());

        // Validate the Escolaridad in the database
        List<Escolaridad> escolaridadList = escolaridadRepository.findAll();
        assertThat(escolaridadList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Escolaridad in Elasticsearch
        verify(mockEscolaridadSearchRepository, times(0)).save(escolaridad);
    }

    @Test
    @Transactional
    public void deleteEscolaridad() throws Exception {
        // Initialize the database
        escolaridadRepository.saveAndFlush(escolaridad);

        int databaseSizeBeforeDelete = escolaridadRepository.findAll().size();

        // Delete the escolaridad
        restEscolaridadMockMvc.perform(delete("/api/escolaridads/{id}", escolaridad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Escolaridad> escolaridadList = escolaridadRepository.findAll();
        assertThat(escolaridadList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Escolaridad in Elasticsearch
        verify(mockEscolaridadSearchRepository, times(1)).deleteById(escolaridad.getId());
    }

    @Test
    @Transactional
    public void searchEscolaridad() throws Exception {
        // Initialize the database
        escolaridadRepository.saveAndFlush(escolaridad);
        when(mockEscolaridadSearchRepository.search(queryStringQuery("id:" + escolaridad.getId())))
            .thenReturn(Collections.singletonList(escolaridad));
        // Search the escolaridad
        restEscolaridadMockMvc.perform(get("/api/_search/escolaridads?query=id:" + escolaridad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escolaridad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
            .andExpect(jsonPath("$.[*].concluyo").value(hasItem(DEFAULT_CONCLUYO.booleanValue())))
            .andExpect(jsonPath("$.[*].clavedoc").value(hasItem(DEFAULT_CLAVEDOC)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Escolaridad.class);
        Escolaridad escolaridad1 = new Escolaridad();
        escolaridad1.setId(1L);
        Escolaridad escolaridad2 = new Escolaridad();
        escolaridad2.setId(escolaridad1.getId());
        assertThat(escolaridad1).isEqualTo(escolaridad2);
        escolaridad2.setId(2L);
        assertThat(escolaridad1).isNotEqualTo(escolaridad2);
        escolaridad1.setId(null);
        assertThat(escolaridad1).isNotEqualTo(escolaridad2);
    }
}
