package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Partida;
import io.github.jhipster.application.repository.PartidaRepository;
import io.github.jhipster.application.repository.search.PartidaSearchRepository;
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
 * Integration tests for the {@link PartidaResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class PartidaResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private PartidaRepository partidaRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.PartidaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PartidaSearchRepository mockPartidaSearchRepository;

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

    private MockMvc restPartidaMockMvc;

    private Partida partida;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartidaResource partidaResource = new PartidaResource(partidaRepository, mockPartidaSearchRepository);
        this.restPartidaMockMvc = MockMvcBuilders.standaloneSetup(partidaResource)
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
    public static Partida createEntity(EntityManager em) {
        Partida partida = new Partida()
            .clave(DEFAULT_CLAVE)
            .nombre(DEFAULT_NOMBRE);
        return partida;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partida createUpdatedEntity(EntityManager em) {
        Partida partida = new Partida()
            .clave(UPDATED_CLAVE)
            .nombre(UPDATED_NOMBRE);
        return partida;
    }

    @BeforeEach
    public void initTest() {
        partida = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartida() throws Exception {
        int databaseSizeBeforeCreate = partidaRepository.findAll().size();

        // Create the Partida
        restPartidaMockMvc.perform(post("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isCreated());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeCreate + 1);
        Partida testPartida = partidaList.get(partidaList.size() - 1);
        assertThat(testPartida.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testPartida.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the Partida in Elasticsearch
        verify(mockPartidaSearchRepository, times(1)).save(testPartida);
    }

    @Test
    @Transactional
    public void createPartidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partidaRepository.findAll().size();

        // Create the Partida with an existing ID
        partida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartidaMockMvc.perform(post("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isBadRequest());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Partida in Elasticsearch
        verify(mockPartidaSearchRepository, times(0)).save(partida);
    }


    @Test
    @Transactional
    public void getAllPartidas() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList
        restPartidaMockMvc.perform(get("/api/partidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partida.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getPartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get the partida
        restPartidaMockMvc.perform(get("/api/partidas/{id}", partida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partida.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartida() throws Exception {
        // Get the partida
        restPartidaMockMvc.perform(get("/api/partidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();

        // Update the partida
        Partida updatedPartida = partidaRepository.findById(partida.getId()).get();
        // Disconnect from session so that the updates on updatedPartida are not directly saved in db
        em.detach(updatedPartida);
        updatedPartida
            .clave(UPDATED_CLAVE)
            .nombre(UPDATED_NOMBRE);

        restPartidaMockMvc.perform(put("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartida)))
            .andExpect(status().isOk());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
        Partida testPartida = partidaList.get(partidaList.size() - 1);
        assertThat(testPartida.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testPartida.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the Partida in Elasticsearch
        verify(mockPartidaSearchRepository, times(1)).save(testPartida);
    }

    @Test
    @Transactional
    public void updateNonExistingPartida() throws Exception {
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();

        // Create the Partida

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartidaMockMvc.perform(put("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isBadRequest());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Partida in Elasticsearch
        verify(mockPartidaSearchRepository, times(0)).save(partida);
    }

    @Test
    @Transactional
    public void deletePartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        int databaseSizeBeforeDelete = partidaRepository.findAll().size();

        // Delete the partida
        restPartidaMockMvc.perform(delete("/api/partidas/{id}", partida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Partida in Elasticsearch
        verify(mockPartidaSearchRepository, times(1)).deleteById(partida.getId());
    }

    @Test
    @Transactional
    public void searchPartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);
        when(mockPartidaSearchRepository.search(queryStringQuery("id:" + partida.getId())))
            .thenReturn(Collections.singletonList(partida));
        // Search the partida
        restPartidaMockMvc.perform(get("/api/_search/partidas?query=id:" + partida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partida.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partida.class);
        Partida partida1 = new Partida();
        partida1.setId(1L);
        Partida partida2 = new Partida();
        partida2.setId(partida1.getId());
        assertThat(partida1).isEqualTo(partida2);
        partida2.setId(2L);
        assertThat(partida1).isNotEqualTo(partida2);
        partida1.setId(null);
        assertThat(partida1).isNotEqualTo(partida2);
    }
}
