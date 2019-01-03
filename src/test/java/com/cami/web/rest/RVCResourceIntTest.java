package com.cami.web.rest;

import com.cami.CautiondouaneApp;

import com.cami.domain.RVC;
import com.cami.repository.RVCRepository;
import com.cami.service.RVCService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.cami.web.rest.TestUtil.sameInstant;
import static com.cami.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RVCResource REST controller.
 *
 * @see RVCResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CautiondouaneApp.class)
public class RVCResourceIntTest {

    private static final String DEFAULT_NUMERO_RVC = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_RVC = "BBBBBBBBBB";

    private static final Long DEFAULT_MONTANT_RVC = 0L;
    private static final Long UPDATED_MONTANT_RVC = 1L;

    private static final String DEFAULT_NUMERO_NAVIRE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_NAVIRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_NAVIRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_NAVIRE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS_PROVENANCE = "AAAAAAAAAA";
    private static final String UPDATED_PAYS_PROVENANCE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_HEURE_DEPART = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HEURE_DEPART = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_HEURE_ARRIVEE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HEURE_ARRIVEE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RVCRepository rVCRepository;

    @Autowired
    private RVCService rVCService;

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

    private MockMvc restRVCMockMvc;

    private RVC rVC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RVCResource rVCResource = new RVCResource(rVCService);
        this.restRVCMockMvc = MockMvcBuilders.standaloneSetup(rVCResource)
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
    public static RVC createEntity(EntityManager em) {
        RVC rVC = new RVC()
            .numeroRVC(DEFAULT_NUMERO_RVC)
            .montantRVC(DEFAULT_MONTANT_RVC)
            .numeroNavire(DEFAULT_NUMERO_NAVIRE)
            .nomNavire(DEFAULT_NOM_NAVIRE)
            .paysProvenance(DEFAULT_PAYS_PROVENANCE)
            .heureDepart(DEFAULT_HEURE_DEPART)
            .heureArrivee(DEFAULT_HEURE_ARRIVEE);
        return rVC;
    }

    @Before
    public void initTest() {
        rVC = createEntity(em);
    }

    @Test
    @Transactional
    public void createRVC() throws Exception {
        int databaseSizeBeforeCreate = rVCRepository.findAll().size();

        // Create the RVC
        restRVCMockMvc.perform(post("/api/rvcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rVC)))
            .andExpect(status().isCreated());

        // Validate the RVC in the database
        List<RVC> rVCList = rVCRepository.findAll();
        assertThat(rVCList).hasSize(databaseSizeBeforeCreate + 1);
        RVC testRVC = rVCList.get(rVCList.size() - 1);
        assertThat(testRVC.getNumeroRVC()).isEqualTo(DEFAULT_NUMERO_RVC);
        assertThat(testRVC.getMontantRVC()).isEqualTo(DEFAULT_MONTANT_RVC);
        assertThat(testRVC.getNumeroNavire()).isEqualTo(DEFAULT_NUMERO_NAVIRE);
        assertThat(testRVC.getNomNavire()).isEqualTo(DEFAULT_NOM_NAVIRE);
        assertThat(testRVC.getPaysProvenance()).isEqualTo(DEFAULT_PAYS_PROVENANCE);
        assertThat(testRVC.getHeureDepart()).isEqualTo(DEFAULT_HEURE_DEPART);
        assertThat(testRVC.getHeureArrivee()).isEqualTo(DEFAULT_HEURE_ARRIVEE);
    }

    @Test
    @Transactional
    public void createRVCWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rVCRepository.findAll().size();

        // Create the RVC with an existing ID
        rVC.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRVCMockMvc.perform(post("/api/rvcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rVC)))
            .andExpect(status().isBadRequest());

        // Validate the RVC in the database
        List<RVC> rVCList = rVCRepository.findAll();
        assertThat(rVCList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantRVCIsRequired() throws Exception {
        int databaseSizeBeforeTest = rVCRepository.findAll().size();
        // set the field null
        rVC.setMontantRVC(null);

        // Create the RVC, which fails.

        restRVCMockMvc.perform(post("/api/rvcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rVC)))
            .andExpect(status().isBadRequest());

        List<RVC> rVCList = rVCRepository.findAll();
        assertThat(rVCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomNavireIsRequired() throws Exception {
        int databaseSizeBeforeTest = rVCRepository.findAll().size();
        // set the field null
        rVC.setNomNavire(null);

        // Create the RVC, which fails.

        restRVCMockMvc.perform(post("/api/rvcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rVC)))
            .andExpect(status().isBadRequest());

        List<RVC> rVCList = rVCRepository.findAll();
        assertThat(rVCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaysProvenanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = rVCRepository.findAll().size();
        // set the field null
        rVC.setPaysProvenance(null);

        // Create the RVC, which fails.

        restRVCMockMvc.perform(post("/api/rvcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rVC)))
            .andExpect(status().isBadRequest());

        List<RVC> rVCList = rVCRepository.findAll();
        assertThat(rVCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRVCS() throws Exception {
        // Initialize the database
        rVCRepository.saveAndFlush(rVC);

        // Get all the rVCList
        restRVCMockMvc.perform(get("/api/rvcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rVC.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroRVC").value(hasItem(DEFAULT_NUMERO_RVC.toString())))
            .andExpect(jsonPath("$.[*].montantRVC").value(hasItem(DEFAULT_MONTANT_RVC.intValue())))
            .andExpect(jsonPath("$.[*].numeroNavire").value(hasItem(DEFAULT_NUMERO_NAVIRE.toString())))
            .andExpect(jsonPath("$.[*].nomNavire").value(hasItem(DEFAULT_NOM_NAVIRE.toString())))
            .andExpect(jsonPath("$.[*].paysProvenance").value(hasItem(DEFAULT_PAYS_PROVENANCE.toString())))
            .andExpect(jsonPath("$.[*].heureDepart").value(hasItem(sameInstant(DEFAULT_HEURE_DEPART))))
            .andExpect(jsonPath("$.[*].heureArrivee").value(hasItem(sameInstant(DEFAULT_HEURE_ARRIVEE))));
    }
    
    @Test
    @Transactional
    public void getRVC() throws Exception {
        // Initialize the database
        rVCRepository.saveAndFlush(rVC);

        // Get the rVC
        restRVCMockMvc.perform(get("/api/rvcs/{id}", rVC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rVC.getId().intValue()))
            .andExpect(jsonPath("$.numeroRVC").value(DEFAULT_NUMERO_RVC.toString()))
            .andExpect(jsonPath("$.montantRVC").value(DEFAULT_MONTANT_RVC.intValue()))
            .andExpect(jsonPath("$.numeroNavire").value(DEFAULT_NUMERO_NAVIRE.toString()))
            .andExpect(jsonPath("$.nomNavire").value(DEFAULT_NOM_NAVIRE.toString()))
            .andExpect(jsonPath("$.paysProvenance").value(DEFAULT_PAYS_PROVENANCE.toString()))
            .andExpect(jsonPath("$.heureDepart").value(sameInstant(DEFAULT_HEURE_DEPART)))
            .andExpect(jsonPath("$.heureArrivee").value(sameInstant(DEFAULT_HEURE_ARRIVEE)));
    }

    @Test
    @Transactional
    public void getNonExistingRVC() throws Exception {
        // Get the rVC
        restRVCMockMvc.perform(get("/api/rvcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRVC() throws Exception {
        // Initialize the database
        rVCService.save(rVC);

        int databaseSizeBeforeUpdate = rVCRepository.findAll().size();

        // Update the rVC
        RVC updatedRVC = rVCRepository.findById(rVC.getId()).get();
        // Disconnect from session so that the updates on updatedRVC are not directly saved in db
        em.detach(updatedRVC);
        updatedRVC
            .numeroRVC(UPDATED_NUMERO_RVC)
            .montantRVC(UPDATED_MONTANT_RVC)
            .numeroNavire(UPDATED_NUMERO_NAVIRE)
            .nomNavire(UPDATED_NOM_NAVIRE)
            .paysProvenance(UPDATED_PAYS_PROVENANCE)
            .heureDepart(UPDATED_HEURE_DEPART)
            .heureArrivee(UPDATED_HEURE_ARRIVEE);

        restRVCMockMvc.perform(put("/api/rvcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRVC)))
            .andExpect(status().isOk());

        // Validate the RVC in the database
        List<RVC> rVCList = rVCRepository.findAll();
        assertThat(rVCList).hasSize(databaseSizeBeforeUpdate);
        RVC testRVC = rVCList.get(rVCList.size() - 1);
        assertThat(testRVC.getNumeroRVC()).isEqualTo(UPDATED_NUMERO_RVC);
        assertThat(testRVC.getMontantRVC()).isEqualTo(UPDATED_MONTANT_RVC);
        assertThat(testRVC.getNumeroNavire()).isEqualTo(UPDATED_NUMERO_NAVIRE);
        assertThat(testRVC.getNomNavire()).isEqualTo(UPDATED_NOM_NAVIRE);
        assertThat(testRVC.getPaysProvenance()).isEqualTo(UPDATED_PAYS_PROVENANCE);
        assertThat(testRVC.getHeureDepart()).isEqualTo(UPDATED_HEURE_DEPART);
        assertThat(testRVC.getHeureArrivee()).isEqualTo(UPDATED_HEURE_ARRIVEE);
    }

    @Test
    @Transactional
    public void updateNonExistingRVC() throws Exception {
        int databaseSizeBeforeUpdate = rVCRepository.findAll().size();

        // Create the RVC

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRVCMockMvc.perform(put("/api/rvcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rVC)))
            .andExpect(status().isBadRequest());

        // Validate the RVC in the database
        List<RVC> rVCList = rVCRepository.findAll();
        assertThat(rVCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRVC() throws Exception {
        // Initialize the database
        rVCService.save(rVC);

        int databaseSizeBeforeDelete = rVCRepository.findAll().size();

        // Get the rVC
        restRVCMockMvc.perform(delete("/api/rvcs/{id}", rVC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RVC> rVCList = rVCRepository.findAll();
        assertThat(rVCList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RVC.class);
        RVC rVC1 = new RVC();
        rVC1.setId(1L);
        RVC rVC2 = new RVC();
        rVC2.setId(rVC1.getId());
        assertThat(rVC1).isEqualTo(rVC2);
        rVC2.setId(2L);
        assertThat(rVC1).isNotEqualTo(rVC2);
        rVC1.setId(null);
        assertThat(rVC1).isNotEqualTo(rVC2);
    }
}
