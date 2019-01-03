package com.cami.web.rest;

import com.cami.CautiondouaneApp;

import com.cami.domain.Caution;
import com.cami.repository.CautionRepository;
import com.cami.service.CautionService;
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
 * Test class for the CautionResource REST controller.
 *
 * @see CautionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CautiondouaneApp.class)
public class CautionResourceIntTest {

    private static final String DEFAULT_NUMERO_CAUTION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CAUTION = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_DOSSIER = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOSSIER = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Long DEFAULT_MONTANT = 0L;
    private static final Long UPDATED_MONTANT = 1L;

    @Autowired
    private CautionRepository cautionRepository;

    @Autowired
    private CautionService cautionService;

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

    private MockMvc restCautionMockMvc;

    private Caution caution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CautionResource cautionResource = new CautionResource(cautionService);
        this.restCautionMockMvc = MockMvcBuilders.standaloneSetup(cautionResource)
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
    public static Caution createEntity(EntityManager em) {
        Caution caution = new Caution()
            .numeroCaution(DEFAULT_NUMERO_CAUTION)
            .numeroDossier(DEFAULT_NUMERO_DOSSIER)
            .reference(DEFAULT_REFERENCE)
            .montant(DEFAULT_MONTANT);
        return caution;
    }

    @Before
    public void initTest() {
        caution = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaution() throws Exception {
        int databaseSizeBeforeCreate = cautionRepository.findAll().size();

        // Create the Caution
        restCautionMockMvc.perform(post("/api/cautions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caution)))
            .andExpect(status().isCreated());

        // Validate the Caution in the database
        List<Caution> cautionList = cautionRepository.findAll();
        assertThat(cautionList).hasSize(databaseSizeBeforeCreate + 1);
        Caution testCaution = cautionList.get(cautionList.size() - 1);
        assertThat(testCaution.getNumeroCaution()).isEqualTo(DEFAULT_NUMERO_CAUTION);
        assertThat(testCaution.getNumeroDossier()).isEqualTo(DEFAULT_NUMERO_DOSSIER);
        assertThat(testCaution.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCaution.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    public void createCautionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cautionRepository.findAll().size();

        // Create the Caution with an existing ID
        caution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCautionMockMvc.perform(post("/api/cautions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caution)))
            .andExpect(status().isBadRequest());

        // Validate the Caution in the database
        List<Caution> cautionList = cautionRepository.findAll();
        assertThat(cautionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = cautionRepository.findAll().size();
        // set the field null
        caution.setMontant(null);

        // Create the Caution, which fails.

        restCautionMockMvc.perform(post("/api/cautions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caution)))
            .andExpect(status().isBadRequest());

        List<Caution> cautionList = cautionRepository.findAll();
        assertThat(cautionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCautions() throws Exception {
        // Initialize the database
        cautionRepository.saveAndFlush(caution);

        // Get all the cautionList
        restCautionMockMvc.perform(get("/api/cautions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caution.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCaution").value(hasItem(DEFAULT_NUMERO_CAUTION.toString())))
            .andExpect(jsonPath("$.[*].numeroDossier").value(hasItem(DEFAULT_NUMERO_DOSSIER.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.intValue())));
    }
    
    @Test
    @Transactional
    public void getCaution() throws Exception {
        // Initialize the database
        cautionRepository.saveAndFlush(caution);

        // Get the caution
        restCautionMockMvc.perform(get("/api/cautions/{id}", caution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caution.getId().intValue()))
            .andExpect(jsonPath("$.numeroCaution").value(DEFAULT_NUMERO_CAUTION.toString()))
            .andExpect(jsonPath("$.numeroDossier").value(DEFAULT_NUMERO_DOSSIER.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCaution() throws Exception {
        // Get the caution
        restCautionMockMvc.perform(get("/api/cautions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaution() throws Exception {
        // Initialize the database
        cautionService.save(caution);

        int databaseSizeBeforeUpdate = cautionRepository.findAll().size();

        // Update the caution
        Caution updatedCaution = cautionRepository.findById(caution.getId()).get();
        // Disconnect from session so that the updates on updatedCaution are not directly saved in db
        em.detach(updatedCaution);
        updatedCaution
            .numeroCaution(UPDATED_NUMERO_CAUTION)
            .numeroDossier(UPDATED_NUMERO_DOSSIER)
            .reference(UPDATED_REFERENCE)
            .montant(UPDATED_MONTANT);

        restCautionMockMvc.perform(put("/api/cautions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaution)))
            .andExpect(status().isOk());

        // Validate the Caution in the database
        List<Caution> cautionList = cautionRepository.findAll();
        assertThat(cautionList).hasSize(databaseSizeBeforeUpdate);
        Caution testCaution = cautionList.get(cautionList.size() - 1);
        assertThat(testCaution.getNumeroCaution()).isEqualTo(UPDATED_NUMERO_CAUTION);
        assertThat(testCaution.getNumeroDossier()).isEqualTo(UPDATED_NUMERO_DOSSIER);
        assertThat(testCaution.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCaution.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingCaution() throws Exception {
        int databaseSizeBeforeUpdate = cautionRepository.findAll().size();

        // Create the Caution

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCautionMockMvc.perform(put("/api/cautions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caution)))
            .andExpect(status().isBadRequest());

        // Validate the Caution in the database
        List<Caution> cautionList = cautionRepository.findAll();
        assertThat(cautionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaution() throws Exception {
        // Initialize the database
        cautionService.save(caution);

        int databaseSizeBeforeDelete = cautionRepository.findAll().size();

        // Get the caution
        restCautionMockMvc.perform(delete("/api/cautions/{id}", caution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Caution> cautionList = cautionRepository.findAll();
        assertThat(cautionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caution.class);
        Caution caution1 = new Caution();
        caution1.setId(1L);
        Caution caution2 = new Caution();
        caution2.setId(caution1.getId());
        assertThat(caution1).isEqualTo(caution2);
        caution2.setId(2L);
        assertThat(caution1).isNotEqualTo(caution2);
        caution1.setId(null);
        assertThat(caution1).isNotEqualTo(caution2);
    }
}
