package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Tipomov;
import io.github.jhipster.application.repository.TipomovRepository;
import io.github.jhipster.application.repository.search.TipomovSearchRepository;
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
 * Integration tests for the {@link TipomovResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class TipomovResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipomovRepository tipomovRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TipomovSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipomovSearchRepository mockTipomovSearchRepository;

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

    private MockMvc restTipomovMockMvc;

    private Tipomov tipomov;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipomovResource tipomovResource = new TipomovResource(tipomovRepository, mockTipomovSearchRepository);
        this.restTipomovMockMvc = MockMvcBuilders.standaloneSetup(tipomovResource)
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
    public static Tipomov createEntity(EntityManager em) {
        Tipomov tipomov = new Tipomov()
            .descripcion(DEFAULT_DESCRIPCION);
        return tipomov;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tipomov createUpdatedEntity(EntityManager em) {
        Tipomov tipomov = new Tipomov()
            .descripcion(UPDATED_DESCRIPCION);
        return tipomov;
    }

    @BeforeEach
    public void initTest() {
        tipomov = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipomov() throws Exception {
        int databaseSizeBeforeCreate = tipomovRepository.findAll().size();

        // Create the Tipomov
        restTipomovMockMvc.perform(post("/api/tipomovs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipomov)))
            .andExpect(status().isCreated());

        // Validate the Tipomov in the database
        List<Tipomov> tipomovList = tipomovRepository.findAll();
        assertThat(tipomovList).hasSize(databaseSizeBeforeCreate + 1);
        Tipomov testTipomov = tipomovList.get(tipomovList.size() - 1);
        assertThat(testTipomov.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);

        // Validate the Tipomov in Elasticsearch
        verify(mockTipomovSearchRepository, times(1)).save(testTipomov);
    }

    @Test
    @Transactional
    public void createTipomovWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipomovRepository.findAll().size();

        // Create the Tipomov with an existing ID
        tipomov.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipomovMockMvc.perform(post("/api/tipomovs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipomov)))
            .andExpect(status().isBadRequest());

        // Validate the Tipomov in the database
        List<Tipomov> tipomovList = tipomovRepository.findAll();
        assertThat(tipomovList).hasSize(databaseSizeBeforeCreate);

        // Validate the Tipomov in Elasticsearch
        verify(mockTipomovSearchRepository, times(0)).save(tipomov);
    }


    @Test
    @Transactional
    public void getAllTipomovs() throws Exception {
        // Initialize the database
        tipomovRepository.saveAndFlush(tipomov);

        // Get all the tipomovList
        restTipomovMockMvc.perform(get("/api/tipomovs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipomov.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getTipomov() throws Exception {
        // Initialize the database
        tipomovRepository.saveAndFlush(tipomov);

        // Get the tipomov
        restTipomovMockMvc.perform(get("/api/tipomovs/{id}", tipomov.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipomov.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipomov() throws Exception {
        // Get the tipomov
        restTipomovMockMvc.perform(get("/api/tipomovs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipomov() throws Exception {
        // Initialize the database
        tipomovRepository.saveAndFlush(tipomov);

        int databaseSizeBeforeUpdate = tipomovRepository.findAll().size();

        // Update the tipomov
        Tipomov updatedTipomov = tipomovRepository.findById(tipomov.getId()).get();
        // Disconnect from session so that the updates on updatedTipomov are not directly saved in db
        em.detach(updatedTipomov);
        updatedTipomov
            .descripcion(UPDATED_DESCRIPCION);

        restTipomovMockMvc.perform(put("/api/tipomovs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipomov)))
            .andExpect(status().isOk());

        // Validate the Tipomov in the database
        List<Tipomov> tipomovList = tipomovRepository.findAll();
        assertThat(tipomovList).hasSize(databaseSizeBeforeUpdate);
        Tipomov testTipomov = tipomovList.get(tipomovList.size() - 1);
        assertThat(testTipomov.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);

        // Validate the Tipomov in Elasticsearch
        verify(mockTipomovSearchRepository, times(1)).save(testTipomov);
    }

    @Test
    @Transactional
    public void updateNonExistingTipomov() throws Exception {
        int databaseSizeBeforeUpdate = tipomovRepository.findAll().size();

        // Create the Tipomov

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipomovMockMvc.perform(put("/api/tipomovs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipomov)))
            .andExpect(status().isBadRequest());

        // Validate the Tipomov in the database
        List<Tipomov> tipomovList = tipomovRepository.findAll();
        assertThat(tipomovList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Tipomov in Elasticsearch
        verify(mockTipomovSearchRepository, times(0)).save(tipomov);
    }

    @Test
    @Transactional
    public void deleteTipomov() throws Exception {
        // Initialize the database
        tipomovRepository.saveAndFlush(tipomov);

        int databaseSizeBeforeDelete = tipomovRepository.findAll().size();

        // Delete the tipomov
        restTipomovMockMvc.perform(delete("/api/tipomovs/{id}", tipomov.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tipomov> tipomovList = tipomovRepository.findAll();
        assertThat(tipomovList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Tipomov in Elasticsearch
        verify(mockTipomovSearchRepository, times(1)).deleteById(tipomov.getId());
    }

    @Test
    @Transactional
    public void searchTipomov() throws Exception {
        // Initialize the database
        tipomovRepository.saveAndFlush(tipomov);
        when(mockTipomovSearchRepository.search(queryStringQuery("id:" + tipomov.getId())))
            .thenReturn(Collections.singletonList(tipomov));
        // Search the tipomov
        restTipomovMockMvc.perform(get("/api/_search/tipomovs?query=id:" + tipomov.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipomov.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tipomov.class);
        Tipomov tipomov1 = new Tipomov();
        tipomov1.setId(1L);
        Tipomov tipomov2 = new Tipomov();
        tipomov2.setId(tipomov1.getId());
        assertThat(tipomov1).isEqualTo(tipomov2);
        tipomov2.setId(2L);
        assertThat(tipomov1).isNotEqualTo(tipomov2);
        tipomov1.setId(null);
        assertThat(tipomov1).isNotEqualTo(tipomov2);
    }
}
