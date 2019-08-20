package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Horariolab;
import io.github.jhipster.application.repository.HorariolabRepository;
import io.github.jhipster.application.repository.search.HorariolabSearchRepository;
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
 * Integration tests for the {@link HorariolabResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class HorariolabResourceIT {

    private static final String DEFAULT_DIA = "AAAAAAAAAA";
    private static final String UPDATED_DIA = "BBBBBBBBBB";

    private static final String DEFAULT_HINICIO = "AAAAAAAAAA";
    private static final String UPDATED_HINICIO = "BBBBBBBBBB";

    private static final String DEFAULT_HFIN = "AAAAAAAAAA";
    private static final String UPDATED_HFIN = "BBBBBBBBBB";

    @Autowired
    private HorariolabRepository horariolabRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.HorariolabSearchRepositoryMockConfiguration
     */
    @Autowired
    private HorariolabSearchRepository mockHorariolabSearchRepository;

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

    private MockMvc restHorariolabMockMvc;

    private Horariolab horariolab;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HorariolabResource horariolabResource = new HorariolabResource(horariolabRepository, mockHorariolabSearchRepository);
        this.restHorariolabMockMvc = MockMvcBuilders.standaloneSetup(horariolabResource)
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
    public static Horariolab createEntity(EntityManager em) {
        Horariolab horariolab = new Horariolab()
            .dia(DEFAULT_DIA)
            .hinicio(DEFAULT_HINICIO)
            .hfin(DEFAULT_HFIN);
        return horariolab;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horariolab createUpdatedEntity(EntityManager em) {
        Horariolab horariolab = new Horariolab()
            .dia(UPDATED_DIA)
            .hinicio(UPDATED_HINICIO)
            .hfin(UPDATED_HFIN);
        return horariolab;
    }

    @BeforeEach
    public void initTest() {
        horariolab = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorariolab() throws Exception {
        int databaseSizeBeforeCreate = horariolabRepository.findAll().size();

        // Create the Horariolab
        restHorariolabMockMvc.perform(post("/api/horariolabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horariolab)))
            .andExpect(status().isCreated());

        // Validate the Horariolab in the database
        List<Horariolab> horariolabList = horariolabRepository.findAll();
        assertThat(horariolabList).hasSize(databaseSizeBeforeCreate + 1);
        Horariolab testHorariolab = horariolabList.get(horariolabList.size() - 1);
        assertThat(testHorariolab.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testHorariolab.getHinicio()).isEqualTo(DEFAULT_HINICIO);
        assertThat(testHorariolab.getHfin()).isEqualTo(DEFAULT_HFIN);

        // Validate the Horariolab in Elasticsearch
        verify(mockHorariolabSearchRepository, times(1)).save(testHorariolab);
    }

    @Test
    @Transactional
    public void createHorariolabWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horariolabRepository.findAll().size();

        // Create the Horariolab with an existing ID
        horariolab.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorariolabMockMvc.perform(post("/api/horariolabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horariolab)))
            .andExpect(status().isBadRequest());

        // Validate the Horariolab in the database
        List<Horariolab> horariolabList = horariolabRepository.findAll();
        assertThat(horariolabList).hasSize(databaseSizeBeforeCreate);

        // Validate the Horariolab in Elasticsearch
        verify(mockHorariolabSearchRepository, times(0)).save(horariolab);
    }


    @Test
    @Transactional
    public void getAllHorariolabs() throws Exception {
        // Initialize the database
        horariolabRepository.saveAndFlush(horariolab);

        // Get all the horariolabList
        restHorariolabMockMvc.perform(get("/api/horariolabs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horariolab.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.[*].hinicio").value(hasItem(DEFAULT_HINICIO.toString())))
            .andExpect(jsonPath("$.[*].hfin").value(hasItem(DEFAULT_HFIN.toString())));
    }
    
    @Test
    @Transactional
    public void getHorariolab() throws Exception {
        // Initialize the database
        horariolabRepository.saveAndFlush(horariolab);

        // Get the horariolab
        restHorariolabMockMvc.perform(get("/api/horariolabs/{id}", horariolab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(horariolab.getId().intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
            .andExpect(jsonPath("$.hinicio").value(DEFAULT_HINICIO.toString()))
            .andExpect(jsonPath("$.hfin").value(DEFAULT_HFIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHorariolab() throws Exception {
        // Get the horariolab
        restHorariolabMockMvc.perform(get("/api/horariolabs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorariolab() throws Exception {
        // Initialize the database
        horariolabRepository.saveAndFlush(horariolab);

        int databaseSizeBeforeUpdate = horariolabRepository.findAll().size();

        // Update the horariolab
        Horariolab updatedHorariolab = horariolabRepository.findById(horariolab.getId()).get();
        // Disconnect from session so that the updates on updatedHorariolab are not directly saved in db
        em.detach(updatedHorariolab);
        updatedHorariolab
            .dia(UPDATED_DIA)
            .hinicio(UPDATED_HINICIO)
            .hfin(UPDATED_HFIN);

        restHorariolabMockMvc.perform(put("/api/horariolabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHorariolab)))
            .andExpect(status().isOk());

        // Validate the Horariolab in the database
        List<Horariolab> horariolabList = horariolabRepository.findAll();
        assertThat(horariolabList).hasSize(databaseSizeBeforeUpdate);
        Horariolab testHorariolab = horariolabList.get(horariolabList.size() - 1);
        assertThat(testHorariolab.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testHorariolab.getHinicio()).isEqualTo(UPDATED_HINICIO);
        assertThat(testHorariolab.getHfin()).isEqualTo(UPDATED_HFIN);

        // Validate the Horariolab in Elasticsearch
        verify(mockHorariolabSearchRepository, times(1)).save(testHorariolab);
    }

    @Test
    @Transactional
    public void updateNonExistingHorariolab() throws Exception {
        int databaseSizeBeforeUpdate = horariolabRepository.findAll().size();

        // Create the Horariolab

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorariolabMockMvc.perform(put("/api/horariolabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horariolab)))
            .andExpect(status().isBadRequest());

        // Validate the Horariolab in the database
        List<Horariolab> horariolabList = horariolabRepository.findAll();
        assertThat(horariolabList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Horariolab in Elasticsearch
        verify(mockHorariolabSearchRepository, times(0)).save(horariolab);
    }

    @Test
    @Transactional
    public void deleteHorariolab() throws Exception {
        // Initialize the database
        horariolabRepository.saveAndFlush(horariolab);

        int databaseSizeBeforeDelete = horariolabRepository.findAll().size();

        // Delete the horariolab
        restHorariolabMockMvc.perform(delete("/api/horariolabs/{id}", horariolab.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Horariolab> horariolabList = horariolabRepository.findAll();
        assertThat(horariolabList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Horariolab in Elasticsearch
        verify(mockHorariolabSearchRepository, times(1)).deleteById(horariolab.getId());
    }

    @Test
    @Transactional
    public void searchHorariolab() throws Exception {
        // Initialize the database
        horariolabRepository.saveAndFlush(horariolab);
        when(mockHorariolabSearchRepository.search(queryStringQuery("id:" + horariolab.getId())))
            .thenReturn(Collections.singletonList(horariolab));
        // Search the horariolab
        restHorariolabMockMvc.perform(get("/api/_search/horariolabs?query=id:" + horariolab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horariolab.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA)))
            .andExpect(jsonPath("$.[*].hinicio").value(hasItem(DEFAULT_HINICIO)))
            .andExpect(jsonPath("$.[*].hfin").value(hasItem(DEFAULT_HFIN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Horariolab.class);
        Horariolab horariolab1 = new Horariolab();
        horariolab1.setId(1L);
        Horariolab horariolab2 = new Horariolab();
        horariolab2.setId(horariolab1.getId());
        assertThat(horariolab1).isEqualTo(horariolab2);
        horariolab2.setId(2L);
        assertThat(horariolab1).isNotEqualTo(horariolab2);
        horariolab1.setId(null);
        assertThat(horariolab1).isNotEqualTo(horariolab2);
    }
}
