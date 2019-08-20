package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Subciclo;
import io.github.jhipster.application.repository.SubcicloRepository;
import io.github.jhipster.application.repository.search.SubcicloSearchRepository;
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
 * Integration tests for the {@link SubcicloResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class SubcicloResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECINI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECINI = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECINI = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHAFIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHAFIN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHAFIN = LocalDate.ofEpochDay(-1L);

    @Autowired
    private SubcicloRepository subcicloRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.SubcicloSearchRepositoryMockConfiguration
     */
    @Autowired
    private SubcicloSearchRepository mockSubcicloSearchRepository;

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

    private MockMvc restSubcicloMockMvc;

    private Subciclo subciclo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubcicloResource subcicloResource = new SubcicloResource(subcicloRepository, mockSubcicloSearchRepository);
        this.restSubcicloMockMvc = MockMvcBuilders.standaloneSetup(subcicloResource)
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
    public static Subciclo createEntity(EntityManager em) {
        Subciclo subciclo = new Subciclo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fecini(DEFAULT_FECINI)
            .fechafin(DEFAULT_FECHAFIN);
        return subciclo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subciclo createUpdatedEntity(EntityManager em) {
        Subciclo subciclo = new Subciclo()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fecini(UPDATED_FECINI)
            .fechafin(UPDATED_FECHAFIN);
        return subciclo;
    }

    @BeforeEach
    public void initTest() {
        subciclo = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubciclo() throws Exception {
        int databaseSizeBeforeCreate = subcicloRepository.findAll().size();

        // Create the Subciclo
        restSubcicloMockMvc.perform(post("/api/subciclos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subciclo)))
            .andExpect(status().isCreated());

        // Validate the Subciclo in the database
        List<Subciclo> subcicloList = subcicloRepository.findAll();
        assertThat(subcicloList).hasSize(databaseSizeBeforeCreate + 1);
        Subciclo testSubciclo = subcicloList.get(subcicloList.size() - 1);
        assertThat(testSubciclo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSubciclo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSubciclo.getFecini()).isEqualTo(DEFAULT_FECINI);
        assertThat(testSubciclo.getFechafin()).isEqualTo(DEFAULT_FECHAFIN);

        // Validate the Subciclo in Elasticsearch
        verify(mockSubcicloSearchRepository, times(1)).save(testSubciclo);
    }

    @Test
    @Transactional
    public void createSubcicloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subcicloRepository.findAll().size();

        // Create the Subciclo with an existing ID
        subciclo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubcicloMockMvc.perform(post("/api/subciclos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subciclo)))
            .andExpect(status().isBadRequest());

        // Validate the Subciclo in the database
        List<Subciclo> subcicloList = subcicloRepository.findAll();
        assertThat(subcicloList).hasSize(databaseSizeBeforeCreate);

        // Validate the Subciclo in Elasticsearch
        verify(mockSubcicloSearchRepository, times(0)).save(subciclo);
    }


    @Test
    @Transactional
    public void getAllSubciclos() throws Exception {
        // Initialize the database
        subcicloRepository.saveAndFlush(subciclo);

        // Get all the subcicloList
        restSubcicloMockMvc.perform(get("/api/subciclos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subciclo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fecini").value(hasItem(DEFAULT_FECINI.toString())))
            .andExpect(jsonPath("$.[*].fechafin").value(hasItem(DEFAULT_FECHAFIN.toString())));
    }
    
    @Test
    @Transactional
    public void getSubciclo() throws Exception {
        // Initialize the database
        subcicloRepository.saveAndFlush(subciclo);

        // Get the subciclo
        restSubcicloMockMvc.perform(get("/api/subciclos/{id}", subciclo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subciclo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fecini").value(DEFAULT_FECINI.toString()))
            .andExpect(jsonPath("$.fechafin").value(DEFAULT_FECHAFIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubciclo() throws Exception {
        // Get the subciclo
        restSubcicloMockMvc.perform(get("/api/subciclos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubciclo() throws Exception {
        // Initialize the database
        subcicloRepository.saveAndFlush(subciclo);

        int databaseSizeBeforeUpdate = subcicloRepository.findAll().size();

        // Update the subciclo
        Subciclo updatedSubciclo = subcicloRepository.findById(subciclo.getId()).get();
        // Disconnect from session so that the updates on updatedSubciclo are not directly saved in db
        em.detach(updatedSubciclo);
        updatedSubciclo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fecini(UPDATED_FECINI)
            .fechafin(UPDATED_FECHAFIN);

        restSubcicloMockMvc.perform(put("/api/subciclos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubciclo)))
            .andExpect(status().isOk());

        // Validate the Subciclo in the database
        List<Subciclo> subcicloList = subcicloRepository.findAll();
        assertThat(subcicloList).hasSize(databaseSizeBeforeUpdate);
        Subciclo testSubciclo = subcicloList.get(subcicloList.size() - 1);
        assertThat(testSubciclo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSubciclo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSubciclo.getFecini()).isEqualTo(UPDATED_FECINI);
        assertThat(testSubciclo.getFechafin()).isEqualTo(UPDATED_FECHAFIN);

        // Validate the Subciclo in Elasticsearch
        verify(mockSubcicloSearchRepository, times(1)).save(testSubciclo);
    }

    @Test
    @Transactional
    public void updateNonExistingSubciclo() throws Exception {
        int databaseSizeBeforeUpdate = subcicloRepository.findAll().size();

        // Create the Subciclo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubcicloMockMvc.perform(put("/api/subciclos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subciclo)))
            .andExpect(status().isBadRequest());

        // Validate the Subciclo in the database
        List<Subciclo> subcicloList = subcicloRepository.findAll();
        assertThat(subcicloList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Subciclo in Elasticsearch
        verify(mockSubcicloSearchRepository, times(0)).save(subciclo);
    }

    @Test
    @Transactional
    public void deleteSubciclo() throws Exception {
        // Initialize the database
        subcicloRepository.saveAndFlush(subciclo);

        int databaseSizeBeforeDelete = subcicloRepository.findAll().size();

        // Delete the subciclo
        restSubcicloMockMvc.perform(delete("/api/subciclos/{id}", subciclo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subciclo> subcicloList = subcicloRepository.findAll();
        assertThat(subcicloList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Subciclo in Elasticsearch
        verify(mockSubcicloSearchRepository, times(1)).deleteById(subciclo.getId());
    }

    @Test
    @Transactional
    public void searchSubciclo() throws Exception {
        // Initialize the database
        subcicloRepository.saveAndFlush(subciclo);
        when(mockSubcicloSearchRepository.search(queryStringQuery("id:" + subciclo.getId())))
            .thenReturn(Collections.singletonList(subciclo));
        // Search the subciclo
        restSubcicloMockMvc.perform(get("/api/_search/subciclos?query=id:" + subciclo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subciclo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fecini").value(hasItem(DEFAULT_FECINI.toString())))
            .andExpect(jsonPath("$.[*].fechafin").value(hasItem(DEFAULT_FECHAFIN.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subciclo.class);
        Subciclo subciclo1 = new Subciclo();
        subciclo1.setId(1L);
        Subciclo subciclo2 = new Subciclo();
        subciclo2.setId(subciclo1.getId());
        assertThat(subciclo1).isEqualTo(subciclo2);
        subciclo2.setId(2L);
        assertThat(subciclo1).isNotEqualTo(subciclo2);
        subciclo1.setId(null);
        assertThat(subciclo1).isNotEqualTo(subciclo2);
    }
}
