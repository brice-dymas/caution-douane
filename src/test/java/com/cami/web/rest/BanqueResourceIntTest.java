package com.cami.web.rest;

import com.cami.CautiondouaneApp;

import com.cami.domain.Banque;
import com.cami.repository.BanqueRepository;
import com.cami.service.BanqueService;
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
 * Test class for the BanqueResource REST controller.
 *
 * @see BanqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CautiondouaneApp.class)
public class BanqueResourceIntTest {

    private static final String DEFAULT_NUMERO_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COMPTE = "BBBBBBBBBB";

    @Autowired
    private BanqueRepository banqueRepository;

    @Autowired
    private BanqueService banqueService;

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

    private MockMvc restBanqueMockMvc;

    private Banque banque;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BanqueResource banqueResource = new BanqueResource(banqueService);
        this.restBanqueMockMvc = MockMvcBuilders.standaloneSetup(banqueResource)
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
    public static Banque createEntity(EntityManager em) {
        Banque banque = new Banque()
            .numeroBanque(DEFAULT_NUMERO_BANQUE)
            .nomBanque(DEFAULT_NOM_BANQUE)
            .numeroCompte(DEFAULT_NUMERO_COMPTE);
        return banque;
    }

    @Before
    public void initTest() {
        banque = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanque() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isCreated());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate + 1);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getNumeroBanque()).isEqualTo(DEFAULT_NUMERO_BANQUE);
        assertThat(testBanque.getNomBanque()).isEqualTo(DEFAULT_NOM_BANQUE);
        assertThat(testBanque.getNumeroCompte()).isEqualTo(DEFAULT_NUMERO_COMPTE);
    }

    @Test
    @Transactional
    public void createBanqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque with an existing ID
        banque.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomBanqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = banqueRepository.findAll().size();
        // set the field null
        banque.setNomBanque(null);

        // Create the Banque, which fails.

        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroCompteIsRequired() throws Exception {
        int databaseSizeBeforeTest = banqueRepository.findAll().size();
        // set the field null
        banque.setNumeroCompte(null);

        // Create the Banque, which fails.

        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBanques() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList
        restBanqueMockMvc.perform(get("/api/banques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banque.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroBanque").value(hasItem(DEFAULT_NUMERO_BANQUE.toString())))
            .andExpect(jsonPath("$.[*].nomBanque").value(hasItem(DEFAULT_NOM_BANQUE.toString())))
            .andExpect(jsonPath("$.[*].numeroCompte").value(hasItem(DEFAULT_NUMERO_COMPTE.toString())));
    }
    
    @Test
    @Transactional
    public void getBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", banque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banque.getId().intValue()))
            .andExpect(jsonPath("$.numeroBanque").value(DEFAULT_NUMERO_BANQUE.toString()))
            .andExpect(jsonPath("$.nomBanque").value(DEFAULT_NOM_BANQUE.toString()))
            .andExpect(jsonPath("$.numeroCompte").value(DEFAULT_NUMERO_COMPTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBanque() throws Exception {
        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanque() throws Exception {
        // Initialize the database
        banqueService.save(banque);

        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Update the banque
        Banque updatedBanque = banqueRepository.findById(banque.getId()).get();
        // Disconnect from session so that the updates on updatedBanque are not directly saved in db
        em.detach(updatedBanque);
        updatedBanque
            .numeroBanque(UPDATED_NUMERO_BANQUE)
            .nomBanque(UPDATED_NOM_BANQUE)
            .numeroCompte(UPDATED_NUMERO_COMPTE);

        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanque)))
            .andExpect(status().isOk());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getNumeroBanque()).isEqualTo(UPDATED_NUMERO_BANQUE);
        assertThat(testBanque.getNomBanque()).isEqualTo(UPDATED_NOM_BANQUE);
        assertThat(testBanque.getNumeroCompte()).isEqualTo(UPDATED_NUMERO_COMPTE);
    }

    @Test
    @Transactional
    public void updateNonExistingBanque() throws Exception {
        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Create the Banque

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBanque() throws Exception {
        // Initialize the database
        banqueService.save(banque);

        int databaseSizeBeforeDelete = banqueRepository.findAll().size();

        // Get the banque
        restBanqueMockMvc.perform(delete("/api/banques/{id}", banque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banque.class);
        Banque banque1 = new Banque();
        banque1.setId(1L);
        Banque banque2 = new Banque();
        banque2.setId(banque1.getId());
        assertThat(banque1).isEqualTo(banque2);
        banque2.setId(2L);
        assertThat(banque1).isNotEqualTo(banque2);
        banque1.setId(null);
        assertThat(banque1).isNotEqualTo(banque2);
    }
}
