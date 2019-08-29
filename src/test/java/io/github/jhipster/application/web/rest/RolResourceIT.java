package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SiieApp;
import io.github.jhipster.application.domain.Rol;
import io.github.jhipster.application.repository.RolRepository;
import io.github.jhipster.application.repository.search.RolSearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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
 * Integration tests for the {@link RolResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = SiieApp.class)
public class RolResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private RolRepository rolRepository;

    @Mock
    private RolRepository rolRepositoryMock;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.RolSearchRepositoryMockConfiguration
     */
    @Autowired
    private RolSearchRepository mockRolSearchRepository;

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

    private MockMvc restRolMockMvc;

    private Rol rol;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RolResource rolResource = new RolResource(rolRepository, mockRolSearchRepository);
        this.restRolMockMvc = MockMvcBuilders.standaloneSetup(rolResource)
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
    public static Rol createEntity(EntityManager em) {
        Rol rol = new Rol()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .activo(DEFAULT_ACTIVO);
        return rol;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rol createUpdatedEntity(EntityManager em) {
        Rol rol = new Rol()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .activo(UPDATED_ACTIVO);
        return rol;
    }

    @BeforeEach
    public void initTest() {
        rol = createEntity(em);
    }

    @Test
    @Transactional
    public void createRol() throws Exception {
        int databaseSizeBeforeCreate = rolRepository.findAll().size();

        // Create the Rol
        restRolMockMvc.perform(post("/api/rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rol)))
            .andExpect(status().isCreated());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeCreate + 1);
        Rol testRol = rolList.get(rolList.size() - 1);
        assertThat(testRol.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRol.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testRol.isActivo()).isEqualTo(DEFAULT_ACTIVO);

        // Validate the Rol in Elasticsearch
        verify(mockRolSearchRepository, times(1)).save(testRol);
    }

    @Test
    @Transactional
    public void createRolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rolRepository.findAll().size();

        // Create the Rol with an existing ID
        rol.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolMockMvc.perform(post("/api/rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rol)))
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeCreate);

        // Validate the Rol in Elasticsearch
        verify(mockRolSearchRepository, times(0)).save(rol);
    }


    @Test
    @Transactional
    public void getAllRols() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList
        restRolMockMvc.perform(get("/api/rols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rol.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRolsWithEagerRelationshipsIsEnabled() throws Exception {
        RolResource rolResource = new RolResource(rolRepositoryMock, mockRolSearchRepository);
        when(rolRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRolMockMvc = MockMvcBuilders.standaloneSetup(rolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRolMockMvc.perform(get("/api/rols?eagerload=true"))
        .andExpect(status().isOk());

        verify(rolRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRolsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RolResource rolResource = new RolResource(rolRepositoryMock, mockRolSearchRepository);
            when(rolRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRolMockMvc = MockMvcBuilders.standaloneSetup(rolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRolMockMvc.perform(get("/api/rols?eagerload=true"))
        .andExpect(status().isOk());

            verify(rolRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get the rol
        restRolMockMvc.perform(get("/api/rols/{id}", rol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rol.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRol() throws Exception {
        // Get the rol
        restRolMockMvc.perform(get("/api/rols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        int databaseSizeBeforeUpdate = rolRepository.findAll().size();

        // Update the rol
        Rol updatedRol = rolRepository.findById(rol.getId()).get();
        // Disconnect from session so that the updates on updatedRol are not directly saved in db
        em.detach(updatedRol);
        updatedRol
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .activo(UPDATED_ACTIVO);

        restRolMockMvc.perform(put("/api/rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRol)))
            .andExpect(status().isOk());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);
        Rol testRol = rolList.get(rolList.size() - 1);
        assertThat(testRol.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRol.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRol.isActivo()).isEqualTo(UPDATED_ACTIVO);

        // Validate the Rol in Elasticsearch
        verify(mockRolSearchRepository, times(1)).save(testRol);
    }

    @Test
    @Transactional
    public void updateNonExistingRol() throws Exception {
        int databaseSizeBeforeUpdate = rolRepository.findAll().size();

        // Create the Rol

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolMockMvc.perform(put("/api/rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rol)))
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Rol in Elasticsearch
        verify(mockRolSearchRepository, times(0)).save(rol);
    }

    @Test
    @Transactional
    public void deleteRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        int databaseSizeBeforeDelete = rolRepository.findAll().size();

        // Delete the rol
        restRolMockMvc.perform(delete("/api/rols/{id}", rol.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rol> rolList = rolRepository.findAll();
        assertThat(rolList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Rol in Elasticsearch
        verify(mockRolSearchRepository, times(1)).deleteById(rol.getId());
    }

    @Test
    @Transactional
    public void searchRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);
        when(mockRolSearchRepository.search(queryStringQuery("id:" + rol.getId())))
            .thenReturn(Collections.singletonList(rol));
        // Search the rol
        restRolMockMvc.perform(get("/api/_search/rols?query=id:" + rol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rol.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rol.class);
        Rol rol1 = new Rol();
        rol1.setId(1L);
        Rol rol2 = new Rol();
        rol2.setId(rol1.getId());
        assertThat(rol1).isEqualTo(rol2);
        rol2.setId(2L);
        assertThat(rol1).isNotEqualTo(rol2);
        rol1.setId(null);
        assertThat(rol1).isNotEqualTo(rol2);
    }
}
