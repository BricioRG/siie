package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Ciclo;
import io.github.jhipster.application.repository.CicloRepository;
import io.github.jhipster.application.repository.search.CicloSearchRepository;
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
 * Integration tests for the {@link CicloResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class CicloResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECINI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECINI = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECINI = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECFIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECFIN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECFIN = LocalDate.ofEpochDay(-1L);

    @Autowired
    private CicloRepository cicloRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.CicloSearchRepositoryMockConfiguration
     */
    @Autowired
    private CicloSearchRepository mockCicloSearchRepository;

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

    private MockMvc restCicloMockMvc;

    private Ciclo ciclo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CicloResource cicloResource = new CicloResource(cicloRepository, mockCicloSearchRepository);
        this.restCicloMockMvc = MockMvcBuilders.standaloneSetup(cicloResource)
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
    public static Ciclo createEntity(EntityManager em) {
        Ciclo ciclo = new Ciclo()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fecini(DEFAULT_FECINI)
            .fecfin(DEFAULT_FECFIN);
        return ciclo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ciclo createUpdatedEntity(EntityManager em) {
        Ciclo ciclo = new Ciclo()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .fecini(UPDATED_FECINI)
            .fecfin(UPDATED_FECFIN);
        return ciclo;
    }

    @BeforeEach
    public void initTest() {
        ciclo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiclo() throws Exception {
        int databaseSizeBeforeCreate = cicloRepository.findAll().size();

        // Create the Ciclo
        restCicloMockMvc.perform(post("/api/ciclos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciclo)))
            .andExpect(status().isCreated());

        // Validate the Ciclo in the database
        List<Ciclo> cicloList = cicloRepository.findAll();
        assertThat(cicloList).hasSize(databaseSizeBeforeCreate + 1);
        Ciclo testCiclo = cicloList.get(cicloList.size() - 1);
        assertThat(testCiclo.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testCiclo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCiclo.getFecini()).isEqualTo(DEFAULT_FECINI);
        assertThat(testCiclo.getFecfin()).isEqualTo(DEFAULT_FECFIN);

        // Validate the Ciclo in Elasticsearch
        verify(mockCicloSearchRepository, times(1)).save(testCiclo);
    }

    @Test
    @Transactional
    public void createCicloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cicloRepository.findAll().size();

        // Create the Ciclo with an existing ID
        ciclo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCicloMockMvc.perform(post("/api/ciclos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciclo)))
            .andExpect(status().isBadRequest());

        // Validate the Ciclo in the database
        List<Ciclo> cicloList = cicloRepository.findAll();
        assertThat(cicloList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ciclo in Elasticsearch
        verify(mockCicloSearchRepository, times(0)).save(ciclo);
    }


    @Test
    @Transactional
    public void getAllCiclos() throws Exception {
        // Initialize the database
        cicloRepository.saveAndFlush(ciclo);

        // Get all the cicloList
        restCicloMockMvc.perform(get("/api/ciclos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciclo.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fecini").value(hasItem(DEFAULT_FECINI.toString())))
            .andExpect(jsonPath("$.[*].fecfin").value(hasItem(DEFAULT_FECFIN.toString())));
    }
    
    @Test
    @Transactional
    public void getCiclo() throws Exception {
        // Initialize the database
        cicloRepository.saveAndFlush(ciclo);

        // Get the ciclo
        restCicloMockMvc.perform(get("/api/ciclos/{id}", ciclo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ciclo.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fecini").value(DEFAULT_FECINI.toString()))
            .andExpect(jsonPath("$.fecfin").value(DEFAULT_FECFIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCiclo() throws Exception {
        // Get the ciclo
        restCicloMockMvc.perform(get("/api/ciclos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiclo() throws Exception {
        // Initialize the database
        cicloRepository.saveAndFlush(ciclo);

        int databaseSizeBeforeUpdate = cicloRepository.findAll().size();

        // Update the ciclo
        Ciclo updatedCiclo = cicloRepository.findById(ciclo.getId()).get();
        // Disconnect from session so that the updates on updatedCiclo are not directly saved in db
        em.detach(updatedCiclo);
        updatedCiclo
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .fecini(UPDATED_FECINI)
            .fecfin(UPDATED_FECFIN);

        restCicloMockMvc.perform(put("/api/ciclos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCiclo)))
            .andExpect(status().isOk());

        // Validate the Ciclo in the database
        List<Ciclo> cicloList = cicloRepository.findAll();
        assertThat(cicloList).hasSize(databaseSizeBeforeUpdate);
        Ciclo testCiclo = cicloList.get(cicloList.size() - 1);
        assertThat(testCiclo.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testCiclo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCiclo.getFecini()).isEqualTo(UPDATED_FECINI);
        assertThat(testCiclo.getFecfin()).isEqualTo(UPDATED_FECFIN);

        // Validate the Ciclo in Elasticsearch
        verify(mockCicloSearchRepository, times(1)).save(testCiclo);
    }

    @Test
    @Transactional
    public void updateNonExistingCiclo() throws Exception {
        int databaseSizeBeforeUpdate = cicloRepository.findAll().size();

        // Create the Ciclo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCicloMockMvc.perform(put("/api/ciclos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciclo)))
            .andExpect(status().isBadRequest());

        // Validate the Ciclo in the database
        List<Ciclo> cicloList = cicloRepository.findAll();
        assertThat(cicloList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ciclo in Elasticsearch
        verify(mockCicloSearchRepository, times(0)).save(ciclo);
    }

    @Test
    @Transactional
    public void deleteCiclo() throws Exception {
        // Initialize the database
        cicloRepository.saveAndFlush(ciclo);

        int databaseSizeBeforeDelete = cicloRepository.findAll().size();

        // Delete the ciclo
        restCicloMockMvc.perform(delete("/api/ciclos/{id}", ciclo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ciclo> cicloList = cicloRepository.findAll();
        assertThat(cicloList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ciclo in Elasticsearch
        verify(mockCicloSearchRepository, times(1)).deleteById(ciclo.getId());
    }

    @Test
    @Transactional
    public void searchCiclo() throws Exception {
        // Initialize the database
        cicloRepository.saveAndFlush(ciclo);
        when(mockCicloSearchRepository.search(queryStringQuery("id:" + ciclo.getId())))
            .thenReturn(Collections.singletonList(ciclo));
        // Search the ciclo
        restCicloMockMvc.perform(get("/api/_search/ciclos?query=id:" + ciclo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciclo.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fecini").value(hasItem(DEFAULT_FECINI.toString())))
            .andExpect(jsonPath("$.[*].fecfin").value(hasItem(DEFAULT_FECFIN.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ciclo.class);
        Ciclo ciclo1 = new Ciclo();
        ciclo1.setId(1L);
        Ciclo ciclo2 = new Ciclo();
        ciclo2.setId(ciclo1.getId());
        assertThat(ciclo1).isEqualTo(ciclo2);
        ciclo2.setId(2L);
        assertThat(ciclo1).isNotEqualTo(ciclo2);
        ciclo1.setId(null);
        assertThat(ciclo1).isNotEqualTo(ciclo2);
    }
}
