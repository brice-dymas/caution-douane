package com.cami.web.rest;

import com.cami.CautiondouaneApp;

import com.cami.domain.Appurement;
import com.cami.repository.AppurementRepository;
import com.cami.service.AppurementService;
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
 * Test class for the AppurementResource REST controller.
 *
 * @see AppurementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CautiondouaneApp.class)
public class AppurementResourceIntTest {

    private static final String DEFAULT_NUMERO_APPUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_APPUREMENT = "BBBBBBBBBB";

    private static final Long DEFAULT_MONTANT_APPURE = 0L;
    private static final Long UPDATED_MONTANT_APPURE = 1L;

    private static final Long DEFAULT_NOMBRE_VEHICULE = 0L;
    private static final Long UPDATED_NOMBRE_VEHICULE = 1L;

    @Autowired
    private AppurementRepository appurementRepository;

    @Autowired
    private AppurementService appurementService;

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

    private MockMvc restAppurementMockMvc;

    private Appurement appurement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppurementResource appurementResource = new AppurementResource(appurementService);
        this.restAppurementMockMvc = MockMvcBuilders.standaloneSetup(appurementResource)
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
    public static Appurement createEntity(EntityManager em) {
        Appurement appurement = new Appurement()
            .numeroAppurement(DEFAULT_NUMERO_APPUREMENT)
            .montantAppure(DEFAULT_MONTANT_APPURE)
            .nombreVehicule(DEFAULT_NOMBRE_VEHICULE);
        return appurement;
    }

    @Before
    public void initTest() {
        appurement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppurement() throws Exception {
        int databaseSizeBeforeCreate = appurementRepository.findAll().size();

        // Create the Appurement
        restAppurementMockMvc.perform(post("/api/appurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appurement)))
            .andExpect(status().isCreated());

        // Validate the Appurement in the database
        List<Appurement> appurementList = appurementRepository.findAll();
        assertThat(appurementList).hasSize(databaseSizeBeforeCreate + 1);
        Appurement testAppurement = appurementList.get(appurementList.size() - 1);
        assertThat(testAppurement.getNumeroAppurement()).isEqualTo(DEFAULT_NUMERO_APPUREMENT);
        assertThat(testAppurement.getMontantAppure()).isEqualTo(DEFAULT_MONTANT_APPURE);
        assertThat(testAppurement.getNombreVehicule()).isEqualTo(DEFAULT_NOMBRE_VEHICULE);
    }

    @Test
    @Transactional
    public void createAppurementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appurementRepository.findAll().size();

        // Create the Appurement with an existing ID
        appurement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppurementMockMvc.perform(post("/api/appurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appurement)))
            .andExpect(status().isBadRequest());

        // Validate the Appurement in the database
        List<Appurement> appurementList = appurementRepository.findAll();
        assertThat(appurementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAppurements() throws Exception {
        // Initialize the database
        appurementRepository.saveAndFlush(appurement);

        // Get all the appurementList
        restAppurementMockMvc.perform(get("/api/appurements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appurement.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroAppurement").value(hasItem(DEFAULT_NUMERO_APPUREMENT.toString())))
            .andExpect(jsonPath("$.[*].montantAppure").value(hasItem(DEFAULT_MONTANT_APPURE.intValue())))
            .andExpect(jsonPath("$.[*].nombreVehicule").value(hasItem(DEFAULT_NOMBRE_VEHICULE.intValue())));
    }
    
    @Test
    @Transactional
    public void getAppurement() throws Exception {
        // Initialize the database
        appurementRepository.saveAndFlush(appurement);

        // Get the appurement
        restAppurementMockMvc.perform(get("/api/appurements/{id}", appurement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appurement.getId().intValue()))
            .andExpect(jsonPath("$.numeroAppurement").value(DEFAULT_NUMERO_APPUREMENT.toString()))
            .andExpect(jsonPath("$.montantAppure").value(DEFAULT_MONTANT_APPURE.intValue()))
            .andExpect(jsonPath("$.nombreVehicule").value(DEFAULT_NOMBRE_VEHICULE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAppurement() throws Exception {
        // Get the appurement
        restAppurementMockMvc.perform(get("/api/appurements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppurement() throws Exception {
        // Initialize the database
        appurementService.save(appurement);

        int databaseSizeBeforeUpdate = appurementRepository.findAll().size();

        // Update the appurement
        Appurement updatedAppurement = appurementRepository.findById(appurement.getId()).get();
        // Disconnect from session so that the updates on updatedAppurement are not directly saved in db
        em.detach(updatedAppurement);
        updatedAppurement
            .numeroAppurement(UPDATED_NUMERO_APPUREMENT)
            .montantAppure(UPDATED_MONTANT_APPURE)
            .nombreVehicule(UPDATED_NOMBRE_VEHICULE);

        restAppurementMockMvc.perform(put("/api/appurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppurement)))
            .andExpect(status().isOk());

        // Validate the Appurement in the database
        List<Appurement> appurementList = appurementRepository.findAll();
        assertThat(appurementList).hasSize(databaseSizeBeforeUpdate);
        Appurement testAppurement = appurementList.get(appurementList.size() - 1);
        assertThat(testAppurement.getNumeroAppurement()).isEqualTo(UPDATED_NUMERO_APPUREMENT);
        assertThat(testAppurement.getMontantAppure()).isEqualTo(UPDATED_MONTANT_APPURE);
        assertThat(testAppurement.getNombreVehicule()).isEqualTo(UPDATED_NOMBRE_VEHICULE);
    }

    @Test
    @Transactional
    public void updateNonExistingAppurement() throws Exception {
        int databaseSizeBeforeUpdate = appurementRepository.findAll().size();

        // Create the Appurement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppurementMockMvc.perform(put("/api/appurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appurement)))
            .andExpect(status().isBadRequest());

        // Validate the Appurement in the database
        List<Appurement> appurementList = appurementRepository.findAll();
        assertThat(appurementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppurement() throws Exception {
        // Initialize the database
        appurementService.save(appurement);

        int databaseSizeBeforeDelete = appurementRepository.findAll().size();

        // Get the appurement
        restAppurementMockMvc.perform(delete("/api/appurements/{id}", appurement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Appurement> appurementList = appurementRepository.findAll();
        assertThat(appurementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appurement.class);
        Appurement appurement1 = new Appurement();
        appurement1.setId(1L);
        Appurement appurement2 = new Appurement();
        appurement2.setId(appurement1.getId());
        assertThat(appurement1).isEqualTo(appurement2);
        appurement2.setId(2L);
        assertThat(appurement1).isNotEqualTo(appurement2);
        appurement1.setId(null);
        assertThat(appurement1).isNotEqualTo(appurement2);
    }
}
