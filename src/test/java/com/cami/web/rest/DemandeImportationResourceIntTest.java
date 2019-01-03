package com.cami.web.rest;

import com.cami.CautiondouaneApp;

import com.cami.domain.DemandeImportation;
import com.cami.repository.DemandeImportationRepository;
import com.cami.service.DemandeImportationService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.cami.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DemandeImportationResource REST controller.
 *
 * @see DemandeImportationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CautiondouaneApp.class)
public class DemandeImportationResourceIntTest {

    private static final String DEFAULT_NUMERO_DORDRE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DORDRE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_FACTURE_PROFORMA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_FACTURE_PROFORMA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RECEPTION_FACTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEPTION_FACTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEPOT_FACTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEPOT_FACTURE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_PR = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RECEPTION_PR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEPTION_PR = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_DI = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DI = "BBBBBBBBBB";

    private static final Long DEFAULT_MONTANT_FOB = 0L;
    private static final Long UPDATED_MONTANT_FOB = 1L;

    private static final Long DEFAULT_MONTANT_FRET = 0L;
    private static final Long UPDATED_MONTANT_FRET = 1L;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    @Autowired
    private DemandeImportationRepository demandeImportationRepository;

    @Autowired
    private DemandeImportationService demandeImportationService;

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

    private MockMvc restDemandeImportationMockMvc;

    private DemandeImportation demandeImportation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DemandeImportationResource demandeImportationResource = new DemandeImportationResource(demandeImportationService);
        this.restDemandeImportationMockMvc = MockMvcBuilders.standaloneSetup(demandeImportationResource)
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
    public static DemandeImportation createEntity(EntityManager em) {
        DemandeImportation demandeImportation = new DemandeImportation()
            .numeroDordre(DEFAULT_NUMERO_DORDRE)
            .numeroFactureProforma(DEFAULT_NUMERO_FACTURE_PROFORMA)
            .dateReceptionFacture(DEFAULT_DATE_RECEPTION_FACTURE)
            .dateDepotFacture(DEFAULT_DATE_DEPOT_FACTURE)
            .numeroPR(DEFAULT_NUMERO_PR)
            .dateReceptionPR(DEFAULT_DATE_RECEPTION_PR)
            .numeroDI(DEFAULT_NUMERO_DI)
            .montantFOB(DEFAULT_MONTANT_FOB)
            .montantFRET(DEFAULT_MONTANT_FRET)
            .observation(DEFAULT_OBSERVATION);
        return demandeImportation;
    }

    @Before
    public void initTest() {
        demandeImportation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDemandeImportation() throws Exception {
        int databaseSizeBeforeCreate = demandeImportationRepository.findAll().size();

        // Create the DemandeImportation
        restDemandeImportationMockMvc.perform(post("/api/demande-importations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeImportation)))
            .andExpect(status().isCreated());

        // Validate the DemandeImportation in the database
        List<DemandeImportation> demandeImportationList = demandeImportationRepository.findAll();
        assertThat(demandeImportationList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeImportation testDemandeImportation = demandeImportationList.get(demandeImportationList.size() - 1);
        assertThat(testDemandeImportation.getNumeroDordre()).isEqualTo(DEFAULT_NUMERO_DORDRE);
        assertThat(testDemandeImportation.getNumeroFactureProforma()).isEqualTo(DEFAULT_NUMERO_FACTURE_PROFORMA);
        assertThat(testDemandeImportation.getDateReceptionFacture()).isEqualTo(DEFAULT_DATE_RECEPTION_FACTURE);
        assertThat(testDemandeImportation.getDateDepotFacture()).isEqualTo(DEFAULT_DATE_DEPOT_FACTURE);
        assertThat(testDemandeImportation.getNumeroPR()).isEqualTo(DEFAULT_NUMERO_PR);
        assertThat(testDemandeImportation.getDateReceptionPR()).isEqualTo(DEFAULT_DATE_RECEPTION_PR);
        assertThat(testDemandeImportation.getNumeroDI()).isEqualTo(DEFAULT_NUMERO_DI);
        assertThat(testDemandeImportation.getMontantFOB()).isEqualTo(DEFAULT_MONTANT_FOB);
        assertThat(testDemandeImportation.getMontantFRET()).isEqualTo(DEFAULT_MONTANT_FRET);
        assertThat(testDemandeImportation.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
    }

    @Test
    @Transactional
    public void createDemandeImportationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demandeImportationRepository.findAll().size();

        // Create the DemandeImportation with an existing ID
        demandeImportation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeImportationMockMvc.perform(post("/api/demande-importations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeImportation)))
            .andExpect(status().isBadRequest());

        // Validate the DemandeImportation in the database
        List<DemandeImportation> demandeImportationList = demandeImportationRepository.findAll();
        assertThat(demandeImportationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroDordreIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeImportationRepository.findAll().size();
        // set the field null
        demandeImportation.setNumeroDordre(null);

        // Create the DemandeImportation, which fails.

        restDemandeImportationMockMvc.perform(post("/api/demande-importations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeImportation)))
            .andExpect(status().isBadRequest());

        List<DemandeImportation> demandeImportationList = demandeImportationRepository.findAll();
        assertThat(demandeImportationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroFactureProformaIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeImportationRepository.findAll().size();
        // set the field null
        demandeImportation.setNumeroFactureProforma(null);

        // Create the DemandeImportation, which fails.

        restDemandeImportationMockMvc.perform(post("/api/demande-importations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeImportation)))
            .andExpect(status().isBadRequest());

        List<DemandeImportation> demandeImportationList = demandeImportationRepository.findAll();
        assertThat(demandeImportationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroPRIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeImportationRepository.findAll().size();
        // set the field null
        demandeImportation.setNumeroPR(null);

        // Create the DemandeImportation, which fails.

        restDemandeImportationMockMvc.perform(post("/api/demande-importations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeImportation)))
            .andExpect(status().isBadRequest());

        List<DemandeImportation> demandeImportationList = demandeImportationRepository.findAll();
        assertThat(demandeImportationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDemandeImportations() throws Exception {
        // Initialize the database
        demandeImportationRepository.saveAndFlush(demandeImportation);

        // Get all the demandeImportationList
        restDemandeImportationMockMvc.perform(get("/api/demande-importations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeImportation.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDordre").value(hasItem(DEFAULT_NUMERO_DORDRE.toString())))
            .andExpect(jsonPath("$.[*].numeroFactureProforma").value(hasItem(DEFAULT_NUMERO_FACTURE_PROFORMA.toString())))
            .andExpect(jsonPath("$.[*].dateReceptionFacture").value(hasItem(DEFAULT_DATE_RECEPTION_FACTURE.toString())))
            .andExpect(jsonPath("$.[*].dateDepotFacture").value(hasItem(DEFAULT_DATE_DEPOT_FACTURE.toString())))
            .andExpect(jsonPath("$.[*].numeroPR").value(hasItem(DEFAULT_NUMERO_PR.toString())))
            .andExpect(jsonPath("$.[*].dateReceptionPR").value(hasItem(DEFAULT_DATE_RECEPTION_PR.toString())))
            .andExpect(jsonPath("$.[*].numeroDI").value(hasItem(DEFAULT_NUMERO_DI.toString())))
            .andExpect(jsonPath("$.[*].montantFOB").value(hasItem(DEFAULT_MONTANT_FOB.intValue())))
            .andExpect(jsonPath("$.[*].montantFRET").value(hasItem(DEFAULT_MONTANT_FRET.intValue())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())));
    }
    
    @Test
    @Transactional
    public void getDemandeImportation() throws Exception {
        // Initialize the database
        demandeImportationRepository.saveAndFlush(demandeImportation);

        // Get the demandeImportation
        restDemandeImportationMockMvc.perform(get("/api/demande-importations/{id}", demandeImportation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(demandeImportation.getId().intValue()))
            .andExpect(jsonPath("$.numeroDordre").value(DEFAULT_NUMERO_DORDRE.toString()))
            .andExpect(jsonPath("$.numeroFactureProforma").value(DEFAULT_NUMERO_FACTURE_PROFORMA.toString()))
            .andExpect(jsonPath("$.dateReceptionFacture").value(DEFAULT_DATE_RECEPTION_FACTURE.toString()))
            .andExpect(jsonPath("$.dateDepotFacture").value(DEFAULT_DATE_DEPOT_FACTURE.toString()))
            .andExpect(jsonPath("$.numeroPR").value(DEFAULT_NUMERO_PR.toString()))
            .andExpect(jsonPath("$.dateReceptionPR").value(DEFAULT_DATE_RECEPTION_PR.toString()))
            .andExpect(jsonPath("$.numeroDI").value(DEFAULT_NUMERO_DI.toString()))
            .andExpect(jsonPath("$.montantFOB").value(DEFAULT_MONTANT_FOB.intValue()))
            .andExpect(jsonPath("$.montantFRET").value(DEFAULT_MONTANT_FRET.intValue()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDemandeImportation() throws Exception {
        // Get the demandeImportation
        restDemandeImportationMockMvc.perform(get("/api/demande-importations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDemandeImportation() throws Exception {
        // Initialize the database
        demandeImportationService.save(demandeImportation);

        int databaseSizeBeforeUpdate = demandeImportationRepository.findAll().size();

        // Update the demandeImportation
        DemandeImportation updatedDemandeImportation = demandeImportationRepository.findById(demandeImportation.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeImportation are not directly saved in db
        em.detach(updatedDemandeImportation);
        updatedDemandeImportation
            .numeroDordre(UPDATED_NUMERO_DORDRE)
            .numeroFactureProforma(UPDATED_NUMERO_FACTURE_PROFORMA)
            .dateReceptionFacture(UPDATED_DATE_RECEPTION_FACTURE)
            .dateDepotFacture(UPDATED_DATE_DEPOT_FACTURE)
            .numeroPR(UPDATED_NUMERO_PR)
            .dateReceptionPR(UPDATED_DATE_RECEPTION_PR)
            .numeroDI(UPDATED_NUMERO_DI)
            .montantFOB(UPDATED_MONTANT_FOB)
            .montantFRET(UPDATED_MONTANT_FRET)
            .observation(UPDATED_OBSERVATION);

        restDemandeImportationMockMvc.perform(put("/api/demande-importations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDemandeImportation)))
            .andExpect(status().isOk());

        // Validate the DemandeImportation in the database
        List<DemandeImportation> demandeImportationList = demandeImportationRepository.findAll();
        assertThat(demandeImportationList).hasSize(databaseSizeBeforeUpdate);
        DemandeImportation testDemandeImportation = demandeImportationList.get(demandeImportationList.size() - 1);
        assertThat(testDemandeImportation.getNumeroDordre()).isEqualTo(UPDATED_NUMERO_DORDRE);
        assertThat(testDemandeImportation.getNumeroFactureProforma()).isEqualTo(UPDATED_NUMERO_FACTURE_PROFORMA);
        assertThat(testDemandeImportation.getDateReceptionFacture()).isEqualTo(UPDATED_DATE_RECEPTION_FACTURE);
        assertThat(testDemandeImportation.getDateDepotFacture()).isEqualTo(UPDATED_DATE_DEPOT_FACTURE);
        assertThat(testDemandeImportation.getNumeroPR()).isEqualTo(UPDATED_NUMERO_PR);
        assertThat(testDemandeImportation.getDateReceptionPR()).isEqualTo(UPDATED_DATE_RECEPTION_PR);
        assertThat(testDemandeImportation.getNumeroDI()).isEqualTo(UPDATED_NUMERO_DI);
        assertThat(testDemandeImportation.getMontantFOB()).isEqualTo(UPDATED_MONTANT_FOB);
        assertThat(testDemandeImportation.getMontantFRET()).isEqualTo(UPDATED_MONTANT_FRET);
        assertThat(testDemandeImportation.getObservation()).isEqualTo(UPDATED_OBSERVATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDemandeImportation() throws Exception {
        int databaseSizeBeforeUpdate = demandeImportationRepository.findAll().size();

        // Create the DemandeImportation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeImportationMockMvc.perform(put("/api/demande-importations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeImportation)))
            .andExpect(status().isBadRequest());

        // Validate the DemandeImportation in the database
        List<DemandeImportation> demandeImportationList = demandeImportationRepository.findAll();
        assertThat(demandeImportationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDemandeImportation() throws Exception {
        // Initialize the database
        demandeImportationService.save(demandeImportation);

        int databaseSizeBeforeDelete = demandeImportationRepository.findAll().size();

        // Get the demandeImportation
        restDemandeImportationMockMvc.perform(delete("/api/demande-importations/{id}", demandeImportation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DemandeImportation> demandeImportationList = demandeImportationRepository.findAll();
        assertThat(demandeImportationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeImportation.class);
        DemandeImportation demandeImportation1 = new DemandeImportation();
        demandeImportation1.setId(1L);
        DemandeImportation demandeImportation2 = new DemandeImportation();
        demandeImportation2.setId(demandeImportation1.getId());
        assertThat(demandeImportation1).isEqualTo(demandeImportation2);
        demandeImportation2.setId(2L);
        assertThat(demandeImportation1).isNotEqualTo(demandeImportation2);
        demandeImportation1.setId(null);
        assertThat(demandeImportation1).isNotEqualTo(demandeImportation2);
    }
}
