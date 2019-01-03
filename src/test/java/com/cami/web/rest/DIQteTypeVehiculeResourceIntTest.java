package com.cami.web.rest;

import com.cami.CautiondouaneApp;

import com.cami.domain.DIQteTypeVehicule;
import com.cami.repository.DIQteTypeVehiculeRepository;
import com.cami.service.DIQteTypeVehiculeService;
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
 * Test class for the DIQteTypeVehiculeResource REST controller.
 *
 * @see DIQteTypeVehiculeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CautiondouaneApp.class)
public class DIQteTypeVehiculeResourceIntTest {

    private static final Long DEFAULT_QUANTITE = 0L;
    private static final Long UPDATED_QUANTITE = 1L;

    @Autowired
    private DIQteTypeVehiculeRepository dIQteTypeVehiculeRepository;

    @Autowired
    private DIQteTypeVehiculeService dIQteTypeVehiculeService;

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

    private MockMvc restDIQteTypeVehiculeMockMvc;

    private DIQteTypeVehicule dIQteTypeVehicule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DIQteTypeVehiculeResource dIQteTypeVehiculeResource = new DIQteTypeVehiculeResource(dIQteTypeVehiculeService);
        this.restDIQteTypeVehiculeMockMvc = MockMvcBuilders.standaloneSetup(dIQteTypeVehiculeResource)
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
    public static DIQteTypeVehicule createEntity(EntityManager em) {
        DIQteTypeVehicule dIQteTypeVehicule = new DIQteTypeVehicule()
            .quantite(DEFAULT_QUANTITE);
        return dIQteTypeVehicule;
    }

    @Before
    public void initTest() {
        dIQteTypeVehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createDIQteTypeVehicule() throws Exception {
        int databaseSizeBeforeCreate = dIQteTypeVehiculeRepository.findAll().size();

        // Create the DIQteTypeVehicule
        restDIQteTypeVehiculeMockMvc.perform(post("/api/di-qte-type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dIQteTypeVehicule)))
            .andExpect(status().isCreated());

        // Validate the DIQteTypeVehicule in the database
        List<DIQteTypeVehicule> dIQteTypeVehiculeList = dIQteTypeVehiculeRepository.findAll();
        assertThat(dIQteTypeVehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        DIQteTypeVehicule testDIQteTypeVehicule = dIQteTypeVehiculeList.get(dIQteTypeVehiculeList.size() - 1);
        assertThat(testDIQteTypeVehicule.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    public void createDIQteTypeVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dIQteTypeVehiculeRepository.findAll().size();

        // Create the DIQteTypeVehicule with an existing ID
        dIQteTypeVehicule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDIQteTypeVehiculeMockMvc.perform(post("/api/di-qte-type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dIQteTypeVehicule)))
            .andExpect(status().isBadRequest());

        // Validate the DIQteTypeVehicule in the database
        List<DIQteTypeVehicule> dIQteTypeVehiculeList = dIQteTypeVehiculeRepository.findAll();
        assertThat(dIQteTypeVehiculeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = dIQteTypeVehiculeRepository.findAll().size();
        // set the field null
        dIQteTypeVehicule.setQuantite(null);

        // Create the DIQteTypeVehicule, which fails.

        restDIQteTypeVehiculeMockMvc.perform(post("/api/di-qte-type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dIQteTypeVehicule)))
            .andExpect(status().isBadRequest());

        List<DIQteTypeVehicule> dIQteTypeVehiculeList = dIQteTypeVehiculeRepository.findAll();
        assertThat(dIQteTypeVehiculeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDIQteTypeVehicules() throws Exception {
        // Initialize the database
        dIQteTypeVehiculeRepository.saveAndFlush(dIQteTypeVehicule);

        // Get all the dIQteTypeVehiculeList
        restDIQteTypeVehiculeMockMvc.perform(get("/api/di-qte-type-vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dIQteTypeVehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())));
    }
    
    @Test
    @Transactional
    public void getDIQteTypeVehicule() throws Exception {
        // Initialize the database
        dIQteTypeVehiculeRepository.saveAndFlush(dIQteTypeVehicule);

        // Get the dIQteTypeVehicule
        restDIQteTypeVehiculeMockMvc.perform(get("/api/di-qte-type-vehicules/{id}", dIQteTypeVehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dIQteTypeVehicule.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDIQteTypeVehicule() throws Exception {
        // Get the dIQteTypeVehicule
        restDIQteTypeVehiculeMockMvc.perform(get("/api/di-qte-type-vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDIQteTypeVehicule() throws Exception {
        // Initialize the database
        dIQteTypeVehiculeService.save(dIQteTypeVehicule);

        int databaseSizeBeforeUpdate = dIQteTypeVehiculeRepository.findAll().size();

        // Update the dIQteTypeVehicule
        DIQteTypeVehicule updatedDIQteTypeVehicule = dIQteTypeVehiculeRepository.findById(dIQteTypeVehicule.getId()).get();
        // Disconnect from session so that the updates on updatedDIQteTypeVehicule are not directly saved in db
        em.detach(updatedDIQteTypeVehicule);
        updatedDIQteTypeVehicule
            .quantite(UPDATED_QUANTITE);

        restDIQteTypeVehiculeMockMvc.perform(put("/api/di-qte-type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDIQteTypeVehicule)))
            .andExpect(status().isOk());

        // Validate the DIQteTypeVehicule in the database
        List<DIQteTypeVehicule> dIQteTypeVehiculeList = dIQteTypeVehiculeRepository.findAll();
        assertThat(dIQteTypeVehiculeList).hasSize(databaseSizeBeforeUpdate);
        DIQteTypeVehicule testDIQteTypeVehicule = dIQteTypeVehiculeList.get(dIQteTypeVehiculeList.size() - 1);
        assertThat(testDIQteTypeVehicule.getQuantite()).isEqualTo(UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void updateNonExistingDIQteTypeVehicule() throws Exception {
        int databaseSizeBeforeUpdate = dIQteTypeVehiculeRepository.findAll().size();

        // Create the DIQteTypeVehicule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDIQteTypeVehiculeMockMvc.perform(put("/api/di-qte-type-vehicules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dIQteTypeVehicule)))
            .andExpect(status().isBadRequest());

        // Validate the DIQteTypeVehicule in the database
        List<DIQteTypeVehicule> dIQteTypeVehiculeList = dIQteTypeVehiculeRepository.findAll();
        assertThat(dIQteTypeVehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDIQteTypeVehicule() throws Exception {
        // Initialize the database
        dIQteTypeVehiculeService.save(dIQteTypeVehicule);

        int databaseSizeBeforeDelete = dIQteTypeVehiculeRepository.findAll().size();

        // Get the dIQteTypeVehicule
        restDIQteTypeVehiculeMockMvc.perform(delete("/api/di-qte-type-vehicules/{id}", dIQteTypeVehicule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DIQteTypeVehicule> dIQteTypeVehiculeList = dIQteTypeVehiculeRepository.findAll();
        assertThat(dIQteTypeVehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DIQteTypeVehicule.class);
        DIQteTypeVehicule dIQteTypeVehicule1 = new DIQteTypeVehicule();
        dIQteTypeVehicule1.setId(1L);
        DIQteTypeVehicule dIQteTypeVehicule2 = new DIQteTypeVehicule();
        dIQteTypeVehicule2.setId(dIQteTypeVehicule1.getId());
        assertThat(dIQteTypeVehicule1).isEqualTo(dIQteTypeVehicule2);
        dIQteTypeVehicule2.setId(2L);
        assertThat(dIQteTypeVehicule1).isNotEqualTo(dIQteTypeVehicule2);
        dIQteTypeVehicule1.setId(null);
        assertThat(dIQteTypeVehicule1).isNotEqualTo(dIQteTypeVehicule2);
    }
}
