package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Direccion;
import io.github.jhipster.application.repository.DireccionRepository;
import io.github.jhipster.application.repository.search.DireccionSearchRepository;
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
 * Integration tests for the {@link DireccionResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class DireccionResourceIT {

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMEROINT = "AAAAAAAAAA";
    private static final String UPDATED_NUMEROINT = "BBBBBBBBBB";

    private static final String DEFAULT_NUMEROEXT = "AAAAAAAAAA";
    private static final String UPDATED_NUMEROEXT = "BBBBBBBBBB";

    private static final String DEFAULT_COLONIA = "AAAAAAAAAA";
    private static final String UPDATED_COLONIA = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRECALLE = "AAAAAAAAAA";
    private static final String UPDATED_ENTRECALLE = "BBBBBBBBBB";

    @Autowired
    private DireccionRepository direccionRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.DireccionSearchRepositoryMockConfiguration
     */
    @Autowired
    private DireccionSearchRepository mockDireccionSearchRepository;

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

    private MockMvc restDireccionMockMvc;

    private Direccion direccion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DireccionResource direccionResource = new DireccionResource(direccionRepository, mockDireccionSearchRepository);
        this.restDireccionMockMvc = MockMvcBuilders.standaloneSetup(direccionResource)
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
    public static Direccion createEntity(EntityManager em) {
        Direccion direccion = new Direccion()
            .calle(DEFAULT_CALLE)
            .numeroint(DEFAULT_NUMEROINT)
            .numeroext(DEFAULT_NUMEROEXT)
            .colonia(DEFAULT_COLONIA)
            .ciudad(DEFAULT_CIUDAD)
            .municipio(DEFAULT_MUNICIPIO)
            .estado(DEFAULT_ESTADO)
            .referencia(DEFAULT_REFERENCIA)
            .entrecalle(DEFAULT_ENTRECALLE);
        return direccion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Direccion createUpdatedEntity(EntityManager em) {
        Direccion direccion = new Direccion()
            .calle(UPDATED_CALLE)
            .numeroint(UPDATED_NUMEROINT)
            .numeroext(UPDATED_NUMEROEXT)
            .colonia(UPDATED_COLONIA)
            .ciudad(UPDATED_CIUDAD)
            .municipio(UPDATED_MUNICIPIO)
            .estado(UPDATED_ESTADO)
            .referencia(UPDATED_REFERENCIA)
            .entrecalle(UPDATED_ENTRECALLE);
        return direccion;
    }

    @BeforeEach
    public void initTest() {
        direccion = createEntity(em);
    }

    @Test
    @Transactional
    public void createDireccion() throws Exception {
        int databaseSizeBeforeCreate = direccionRepository.findAll().size();

        // Create the Direccion
        restDireccionMockMvc.perform(post("/api/direccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isCreated());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeCreate + 1);
        Direccion testDireccion = direccionList.get(direccionList.size() - 1);
        assertThat(testDireccion.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testDireccion.getNumeroint()).isEqualTo(DEFAULT_NUMEROINT);
        assertThat(testDireccion.getNumeroext()).isEqualTo(DEFAULT_NUMEROEXT);
        assertThat(testDireccion.getColonia()).isEqualTo(DEFAULT_COLONIA);
        assertThat(testDireccion.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testDireccion.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testDireccion.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testDireccion.getReferencia()).isEqualTo(DEFAULT_REFERENCIA);
        assertThat(testDireccion.getEntrecalle()).isEqualTo(DEFAULT_ENTRECALLE);

        // Validate the Direccion in Elasticsearch
        verify(mockDireccionSearchRepository, times(1)).save(testDireccion);
    }

    @Test
    @Transactional
    public void createDireccionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = direccionRepository.findAll().size();

        // Create the Direccion with an existing ID
        direccion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDireccionMockMvc.perform(post("/api/direccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isBadRequest());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Direccion in Elasticsearch
        verify(mockDireccionSearchRepository, times(0)).save(direccion);
    }


    @Test
    @Transactional
    public void getAllDireccions() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        // Get all the direccionList
        restDireccionMockMvc.perform(get("/api/direccions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE.toString())))
            .andExpect(jsonPath("$.[*].numeroint").value(hasItem(DEFAULT_NUMEROINT.toString())))
            .andExpect(jsonPath("$.[*].numeroext").value(hasItem(DEFAULT_NUMEROEXT.toString())))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA.toString())))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD.toString())))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].entrecalle").value(hasItem(DEFAULT_ENTRECALLE.toString())));
    }
    
    @Test
    @Transactional
    public void getDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        // Get the direccion
        restDireccionMockMvc.perform(get("/api/direccions/{id}", direccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(direccion.getId().intValue()))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE.toString()))
            .andExpect(jsonPath("$.numeroint").value(DEFAULT_NUMEROINT.toString()))
            .andExpect(jsonPath("$.numeroext").value(DEFAULT_NUMEROEXT.toString()))
            .andExpect(jsonPath("$.colonia").value(DEFAULT_COLONIA.toString()))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD.toString()))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.referencia").value(DEFAULT_REFERENCIA.toString()))
            .andExpect(jsonPath("$.entrecalle").value(DEFAULT_ENTRECALLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDireccion() throws Exception {
        // Get the direccion
        restDireccionMockMvc.perform(get("/api/direccions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();

        // Update the direccion
        Direccion updatedDireccion = direccionRepository.findById(direccion.getId()).get();
        // Disconnect from session so that the updates on updatedDireccion are not directly saved in db
        em.detach(updatedDireccion);
        updatedDireccion
            .calle(UPDATED_CALLE)
            .numeroint(UPDATED_NUMEROINT)
            .numeroext(UPDATED_NUMEROEXT)
            .colonia(UPDATED_COLONIA)
            .ciudad(UPDATED_CIUDAD)
            .municipio(UPDATED_MUNICIPIO)
            .estado(UPDATED_ESTADO)
            .referencia(UPDATED_REFERENCIA)
            .entrecalle(UPDATED_ENTRECALLE);

        restDireccionMockMvc.perform(put("/api/direccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDireccion)))
            .andExpect(status().isOk());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
        Direccion testDireccion = direccionList.get(direccionList.size() - 1);
        assertThat(testDireccion.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testDireccion.getNumeroint()).isEqualTo(UPDATED_NUMEROINT);
        assertThat(testDireccion.getNumeroext()).isEqualTo(UPDATED_NUMEROEXT);
        assertThat(testDireccion.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testDireccion.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testDireccion.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testDireccion.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testDireccion.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
        assertThat(testDireccion.getEntrecalle()).isEqualTo(UPDATED_ENTRECALLE);

        // Validate the Direccion in Elasticsearch
        verify(mockDireccionSearchRepository, times(1)).save(testDireccion);
    }

    @Test
    @Transactional
    public void updateNonExistingDireccion() throws Exception {
        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();

        // Create the Direccion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDireccionMockMvc.perform(put("/api/direccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(direccion)))
            .andExpect(status().isBadRequest());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Direccion in Elasticsearch
        verify(mockDireccionSearchRepository, times(0)).save(direccion);
    }

    @Test
    @Transactional
    public void deleteDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        int databaseSizeBeforeDelete = direccionRepository.findAll().size();

        // Delete the direccion
        restDireccionMockMvc.perform(delete("/api/direccions/{id}", direccion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Direccion in Elasticsearch
        verify(mockDireccionSearchRepository, times(1)).deleteById(direccion.getId());
    }

    @Test
    @Transactional
    public void searchDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);
        when(mockDireccionSearchRepository.search(queryStringQuery("id:" + direccion.getId())))
            .thenReturn(Collections.singletonList(direccion));
        // Search the direccion
        restDireccionMockMvc.perform(get("/api/_search/direccions?query=id:" + direccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].numeroint").value(hasItem(DEFAULT_NUMEROINT)))
            .andExpect(jsonPath("$.[*].numeroext").value(hasItem(DEFAULT_NUMEROEXT)))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA)))
            .andExpect(jsonPath("$.[*].entrecalle").value(hasItem(DEFAULT_ENTRECALLE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Direccion.class);
        Direccion direccion1 = new Direccion();
        direccion1.setId(1L);
        Direccion direccion2 = new Direccion();
        direccion2.setId(direccion1.getId());
        assertThat(direccion1).isEqualTo(direccion2);
        direccion2.setId(2L);
        assertThat(direccion1).isNotEqualTo(direccion2);
        direccion1.setId(null);
        assertThat(direccion1).isNotEqualTo(direccion2);
    }
}
