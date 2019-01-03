package com.cami.web.rest;

import com.cami.CautiondouaneApp;

import com.cami.domain.TypeVehicule;
import com.cami.repository.TypeVehiculeRepository;
import com.cami.service.TypeVehiculeService;
import com.cami.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.cami.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TypeVehiculeResource REST controller.
 *
 * @see TypeVehiculeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CautiondouaneApp.class)
public class TypeVehiculeResourceIntTest {

    private static final String DEFAULT_NUMERO_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TYPE = "BBBBBBBBBB";

    @Autowired
    private TypeVehiculeRepository typeVehiculeRepository;

    @Autowired
    private TypeVehiculeService typeVehiculeService;

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

    private MockMvc restTypeVehiculeMockMvc;

    private TypeVehicule typeVehicule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeVehiculeResource typeVehiculeResource = new TypeVehiculeResource(typeVehiculeService);
        this.restTypeVehiculeMockMvc = MockMvcBuilders.standaloneSetup(typeVehiculeResource)
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
    public static TypeVehicule createEntity(EntityManager em) {
        TypeVehicule typeVehicule = new TypeVehicule()
            .numeroType(DEFAULT_NUMERO_TYPE)
            .nomType(DEFAULT_NOM_TYPE);
        return typeVehicule;
    }

    @Before
    public void initTest() {
        typeVehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeVehicule() throws Exception {
        int databaseSizeBeforeCreate = typeVehiculeRepository.findAll().size();

        // Create the TypeVehicule
        restTypeVehiculeMockMvc.perform(post("/api/type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVehicule)))
            .andExpect(status().isCreated());

        // Validate the TypeVehicule in the database
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        TypeVehicule testTypeVehicule = typeVehiculeList.get(typeVehiculeList.size() - 1);
        assertThat(testTypeVehicule.getNumeroType()).isEqualTo(DEFAULT_NUMERO_TYPE);
        assertThat(testTypeVehicule.getNomType()).isEqualTo(DEFAULT_NOM_TYPE);
    }

    @Test
    @Transactional
    public void createTypeVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeVehiculeRepository.findAll().size();

        // Create the TypeVehicule with an existing ID
        typeVehicule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeVehiculeMockMvc.perform(post("/api/type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVehicule)))
            .andExpect(status().isBadRequest());

        // Validate the TypeVehicule in the database
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeVehiculeRepository.findAll().size();
        // set the field null
        typeVehicule.setNomType(null);

        // Create the TypeVehicule, which fails.

        restTypeVehiculeMockMvc.perform(post("/api/type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVehicule)))
            .andExpect(status().isBadRequest());

        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeVehicules() throws Exception {
        // Initialize the database
        typeVehiculeRepository.saveAndFlush(typeVehicule);

        // Get all the typeVehiculeList
        restTypeVehiculeMockMvc.perform(get("/api/type-vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeVehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroType").value(hasItem(DEFAULT_NUMERO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nomType").value(hasItem(DEFAULT_NOM_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeVehicule() throws Exception {
        // Initialize the database
        typeVehiculeRepository.saveAndFlush(typeVehicule);

        // Get the typeVehicule
        restTypeVehiculeMockMvc.perform(get("/api/type-vehicules/{id}", typeVehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeVehicule.getId().intValue()))
            .andExpect(jsonPath("$.numeroType").value(DEFAULT_NUMERO_TYPE.toString()))
            .andExpect(jsonPath("$.nomType").value(DEFAULT_NOM_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeVehicule() throws Exception {
        // Get the typeVehicule
        restTypeVehiculeMockMvc.perform(get("/api/type-vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeVehicule() throws Exception {
        // Initialize the database
        typeVehiculeService.save(typeVehicule);

        int databaseSizeBeforeUpdate = typeVehiculeRepository.findAll().size();

        // Update the typeVehicule
        TypeVehicule updatedTypeVehicule = typeVehiculeRepository.findById(typeVehicule.getId()).get();
        // Disconnect from session so that the updates on updatedTypeVehicule are not directly saved in db
        em.detach(updatedTypeVehicule);
        updatedTypeVehicule
            .numeroType(UPDATED_NUMERO_TYPE)
            .nomType(UPDATED_NOM_TYPE);

        restTypeVehiculeMockMvc.perform(put("/api/type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeVehicule)))
            .andExpect(status().isOk());

        // Validate the TypeVehicule in the database
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeUpdate);
        TypeVehicule testTypeVehicule = typeVehiculeList.get(typeVehiculeList.size() - 1);
        assertThat(testTypeVehicule.getNumeroType()).isEqualTo(UPDATED_NUMERO_TYPE);
        assertThat(testTypeVehicule.getNomType()).isEqualTo(UPDATED_NOM_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeVehicule() throws Exception {
        int databaseSizeBeforeUpdate = typeVehiculeRepository.findAll().size();

        // Create the TypeVehicule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeVehiculeMockMvc.perform(put("/api/type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVehicule)))
            .andExpect(status().isBadRequest());

        // Validate the TypeVehicule in the database
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeVehicule() throws Exception {
        // Initialize the database
        typeVehiculeService.save(typeVehicule);

        int databaseSizeBeforeDelete = typeVehiculeRepository.findAll().size();

        // Get the typeVehicule
        restTypeVehiculeMockMvc.perform(delete("/api/type-vehicules/{id}", typeVehicule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeVehicule> typeVehiculeList = typeVehiculeRepository.findAll();
        assertThat(typeVehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeVehicule.class);
        TypeVehicule typeVehicule1 = new TypeVehicule();
        typeVehicule1.setId(1L);
        TypeVehicule typeVehicule2 = new TypeVehicule();
        typeVehicule2.setId(typeVehicule1.getId());
        assertThat(typeVehicule1).isEqualTo(typeVehicule2);
        typeVehicule2.setId(2L);
        assertThat(typeVehicule1).isNotEqualTo(typeVehicule2);
        typeVehicule1.setId(null);
        assertThat(typeVehicule1).isNotEqualTo(typeVehicule2);
    }
}
