package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Persona;
import io.github.jhipster.application.repository.PersonaRepository;
import io.github.jhipster.application.repository.search.PersonaSearchRepository;
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
 * Integration tests for the {@link PersonaResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class PersonaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMERAPE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMERAPE = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDOAPE = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDOAPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHANAC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHANAC = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHANAC = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ENTIDADNAC = "AAAAAAAAAA";
    private static final String UPDATED_ENTIDADNAC = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final String DEFAULT_CURP = "AAAAAAAAAA";
    private static final String UPDATED_CURP = "BBBBBBBBBB";

    private static final String DEFAULT_EDOCIVIL = "AAAAAAAAAA";
    private static final String UPDATED_EDOCIVIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMPLEADO = false;
    private static final Boolean UPDATED_EMPLEADO = true;

    private static final String DEFAULT_NUEMPLEADO = "AAAAAAAAAA";
    private static final String UPDATED_NUEMPLEADO = "BBBBBBBBBB";

    @Autowired
    private PersonaRepository personaRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.PersonaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PersonaSearchRepository mockPersonaSearchRepository;

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

    private MockMvc restPersonaMockMvc;

    private Persona persona;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonaResource personaResource = new PersonaResource(personaRepository, mockPersonaSearchRepository);
        this.restPersonaMockMvc = MockMvcBuilders.standaloneSetup(personaResource)
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
    public static Persona createEntity(EntityManager em) {
        Persona persona = new Persona()
            .nombre(DEFAULT_NOMBRE)
            .primerape(DEFAULT_PRIMERAPE)
            .segundoape(DEFAULT_SEGUNDOAPE)
            .fechanac(DEFAULT_FECHANAC)
            .entidadnac(DEFAULT_ENTIDADNAC)
            .genero(DEFAULT_GENERO)
            .rfc(DEFAULT_RFC)
            .curp(DEFAULT_CURP)
            .edocivil(DEFAULT_EDOCIVIL)
            .empleado(DEFAULT_EMPLEADO)
            .nuempleado(DEFAULT_NUEMPLEADO);
        return persona;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persona createUpdatedEntity(EntityManager em) {
        Persona persona = new Persona()
            .nombre(UPDATED_NOMBRE)
            .primerape(UPDATED_PRIMERAPE)
            .segundoape(UPDATED_SEGUNDOAPE)
            .fechanac(UPDATED_FECHANAC)
            .entidadnac(UPDATED_ENTIDADNAC)
            .genero(UPDATED_GENERO)
            .rfc(UPDATED_RFC)
            .curp(UPDATED_CURP)
            .edocivil(UPDATED_EDOCIVIL)
            .empleado(UPDATED_EMPLEADO)
            .nuempleado(UPDATED_NUEMPLEADO);
        return persona;
    }

    @BeforeEach
    public void initTest() {
        persona = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersona() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate + 1);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersona.getPrimerape()).isEqualTo(DEFAULT_PRIMERAPE);
        assertThat(testPersona.getSegundoape()).isEqualTo(DEFAULT_SEGUNDOAPE);
        assertThat(testPersona.getFechanac()).isEqualTo(DEFAULT_FECHANAC);
        assertThat(testPersona.getEntidadnac()).isEqualTo(DEFAULT_ENTIDADNAC);
        assertThat(testPersona.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testPersona.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testPersona.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testPersona.getEdocivil()).isEqualTo(DEFAULT_EDOCIVIL);
        assertThat(testPersona.isEmpleado()).isEqualTo(DEFAULT_EMPLEADO);
        assertThat(testPersona.getNuempleado()).isEqualTo(DEFAULT_NUEMPLEADO);

        // Validate the Persona in Elasticsearch
        verify(mockPersonaSearchRepository, times(1)).save(testPersona);
    }

    @Test
    @Transactional
    public void createPersonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona with an existing ID
        persona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Persona in Elasticsearch
        verify(mockPersonaSearchRepository, times(0)).save(persona);
    }


    @Test
    @Transactional
    public void getAllPersonas() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList
        restPersonaMockMvc.perform(get("/api/personas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].primerape").value(hasItem(DEFAULT_PRIMERAPE.toString())))
            .andExpect(jsonPath("$.[*].segundoape").value(hasItem(DEFAULT_SEGUNDOAPE.toString())))
            .andExpect(jsonPath("$.[*].fechanac").value(hasItem(DEFAULT_FECHANAC.toString())))
            .andExpect(jsonPath("$.[*].entidadnac").value(hasItem(DEFAULT_ENTIDADNAC.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
            .andExpect(jsonPath("$.[*].edocivil").value(hasItem(DEFAULT_EDOCIVIL.toString())))
            .andExpect(jsonPath("$.[*].empleado").value(hasItem(DEFAULT_EMPLEADO.booleanValue())))
            .andExpect(jsonPath("$.[*].nuempleado").value(hasItem(DEFAULT_NUEMPLEADO.toString())));
    }
    
    @Test
    @Transactional
    public void getPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", persona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(persona.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.primerape").value(DEFAULT_PRIMERAPE.toString()))
            .andExpect(jsonPath("$.segundoape").value(DEFAULT_SEGUNDOAPE.toString()))
            .andExpect(jsonPath("$.fechanac").value(DEFAULT_FECHANAC.toString()))
            .andExpect(jsonPath("$.entidadnac").value(DEFAULT_ENTIDADNAC.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.edocivil").value(DEFAULT_EDOCIVIL.toString()))
            .andExpect(jsonPath("$.empleado").value(DEFAULT_EMPLEADO.booleanValue()))
            .andExpect(jsonPath("$.nuempleado").value(DEFAULT_NUEMPLEADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersona() throws Exception {
        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona
        Persona updatedPersona = personaRepository.findById(persona.getId()).get();
        // Disconnect from session so that the updates on updatedPersona are not directly saved in db
        em.detach(updatedPersona);
        updatedPersona
            .nombre(UPDATED_NOMBRE)
            .primerape(UPDATED_PRIMERAPE)
            .segundoape(UPDATED_SEGUNDOAPE)
            .fechanac(UPDATED_FECHANAC)
            .entidadnac(UPDATED_ENTIDADNAC)
            .genero(UPDATED_GENERO)
            .rfc(UPDATED_RFC)
            .curp(UPDATED_CURP)
            .edocivil(UPDATED_EDOCIVIL)
            .empleado(UPDATED_EMPLEADO)
            .nuempleado(UPDATED_NUEMPLEADO);

        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersona)))
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersona.getPrimerape()).isEqualTo(UPDATED_PRIMERAPE);
        assertThat(testPersona.getSegundoape()).isEqualTo(UPDATED_SEGUNDOAPE);
        assertThat(testPersona.getFechanac()).isEqualTo(UPDATED_FECHANAC);
        assertThat(testPersona.getEntidadnac()).isEqualTo(UPDATED_ENTIDADNAC);
        assertThat(testPersona.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testPersona.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testPersona.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testPersona.getEdocivil()).isEqualTo(UPDATED_EDOCIVIL);
        assertThat(testPersona.isEmpleado()).isEqualTo(UPDATED_EMPLEADO);
        assertThat(testPersona.getNuempleado()).isEqualTo(UPDATED_NUEMPLEADO);

        // Validate the Persona in Elasticsearch
        verify(mockPersonaSearchRepository, times(1)).save(testPersona);
    }

    @Test
    @Transactional
    public void updateNonExistingPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Create the Persona

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Persona in Elasticsearch
        verify(mockPersonaSearchRepository, times(0)).save(persona);
    }

    @Test
    @Transactional
    public void deletePersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        int databaseSizeBeforeDelete = personaRepository.findAll().size();

        // Delete the persona
        restPersonaMockMvc.perform(delete("/api/personas/{id}", persona.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Persona in Elasticsearch
        verify(mockPersonaSearchRepository, times(1)).deleteById(persona.getId());
    }

    @Test
    @Transactional
    public void searchPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);
        when(mockPersonaSearchRepository.search(queryStringQuery("id:" + persona.getId())))
            .thenReturn(Collections.singletonList(persona));
        // Search the persona
        restPersonaMockMvc.perform(get("/api/_search/personas?query=id:" + persona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerape").value(hasItem(DEFAULT_PRIMERAPE)))
            .andExpect(jsonPath("$.[*].segundoape").value(hasItem(DEFAULT_SEGUNDOAPE)))
            .andExpect(jsonPath("$.[*].fechanac").value(hasItem(DEFAULT_FECHANAC.toString())))
            .andExpect(jsonPath("$.[*].entidadnac").value(hasItem(DEFAULT_ENTIDADNAC)))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO)))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP)))
            .andExpect(jsonPath("$.[*].edocivil").value(hasItem(DEFAULT_EDOCIVIL)))
            .andExpect(jsonPath("$.[*].empleado").value(hasItem(DEFAULT_EMPLEADO.booleanValue())))
            .andExpect(jsonPath("$.[*].nuempleado").value(hasItem(DEFAULT_NUEMPLEADO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Persona.class);
        Persona persona1 = new Persona();
        persona1.setId(1L);
        Persona persona2 = new Persona();
        persona2.setId(persona1.getId());
        assertThat(persona1).isEqualTo(persona2);
        persona2.setId(2L);
        assertThat(persona1).isNotEqualTo(persona2);
        persona1.setId(null);
        assertThat(persona1).isNotEqualTo(persona2);
    }
}
