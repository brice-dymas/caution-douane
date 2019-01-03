package com.cami.web.rest;

import com.cami.CautiondouaneApp;

import com.cami.domain.PieceJointe;
import com.cami.repository.PieceJointeRepository;
import com.cami.service.PieceJointeService;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.cami.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PieceJointeResource REST controller.
 *
 * @see PieceJointeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CautiondouaneApp.class)
public class PieceJointeResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHIER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PieceJointeRepository pieceJointeRepository;

    @Autowired
    private PieceJointeService pieceJointeService;

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

    private MockMvc restPieceJointeMockMvc;

    private PieceJointe pieceJointe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PieceJointeResource pieceJointeResource = new PieceJointeResource(pieceJointeService);
        this.restPieceJointeMockMvc = MockMvcBuilders.standaloneSetup(pieceJointeResource)
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
    public static PieceJointe createEntity(EntityManager em) {
        PieceJointe pieceJointe = new PieceJointe()
            .nom(DEFAULT_NOM)
            .fichier(DEFAULT_FICHIER)
            .fichierContentType(DEFAULT_FICHIER_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return pieceJointe;
    }

    @Before
    public void initTest() {
        pieceJointe = createEntity(em);
    }

    @Test
    @Transactional
    public void createPieceJointe() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe
        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointe)))
            .andExpect(status().isCreated());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate + 1);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPieceJointe.getFichier()).isEqualTo(DEFAULT_FICHIER);
        assertThat(testPieceJointe.getFichierContentType()).isEqualTo(DEFAULT_FICHIER_CONTENT_TYPE);
        assertThat(testPieceJointe.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPieceJointeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe with an existing ID
        pieceJointe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointe)))
            .andExpect(status().isBadRequest());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceJointeRepository.findAll().size();
        // set the field null
        pieceJointe.setNom(null);

        // Create the PieceJointe, which fails.

        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointe)))
            .andExpect(status().isBadRequest());

        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPieceJointes() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);

        // Get all the pieceJointeList
        restPieceJointeMockMvc.perform(get("/api/piece-jointes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJointe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].fichierContentType").value(hasItem(DEFAULT_FICHIER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getPieceJointe() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);

        // Get the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/piece-jointes/{id}", pieceJointe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pieceJointe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.fichierContentType").value(DEFAULT_FICHIER_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichier").value(Base64Utils.encodeToString(DEFAULT_FICHIER)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPieceJointe() throws Exception {
        // Get the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/piece-jointes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePieceJointe() throws Exception {
        // Initialize the database
        pieceJointeService.save(pieceJointe);

        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Update the pieceJointe
        PieceJointe updatedPieceJointe = pieceJointeRepository.findById(pieceJointe.getId()).get();
        // Disconnect from session so that the updates on updatedPieceJointe are not directly saved in db
        em.detach(updatedPieceJointe);
        updatedPieceJointe
            .nom(UPDATED_NOM)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);

        restPieceJointeMockMvc.perform(put("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPieceJointe)))
            .andExpect(status().isOk());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPieceJointe.getFichier()).isEqualTo(UPDATED_FICHIER);
        assertThat(testPieceJointe.getFichierContentType()).isEqualTo(UPDATED_FICHIER_CONTENT_TYPE);
        assertThat(testPieceJointe.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPieceJointe() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPieceJointeMockMvc.perform(put("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointe)))
            .andExpect(status().isBadRequest());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePieceJointe() throws Exception {
        // Initialize the database
        pieceJointeService.save(pieceJointe);

        int databaseSizeBeforeDelete = pieceJointeRepository.findAll().size();

        // Get the pieceJointe
        restPieceJointeMockMvc.perform(delete("/api/piece-jointes/{id}", pieceJointe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointe.class);
        PieceJointe pieceJointe1 = new PieceJointe();
        pieceJointe1.setId(1L);
        PieceJointe pieceJointe2 = new PieceJointe();
        pieceJointe2.setId(pieceJointe1.getId());
        assertThat(pieceJointe1).isEqualTo(pieceJointe2);
        pieceJointe2.setId(2L);
        assertThat(pieceJointe1).isNotEqualTo(pieceJointe2);
        pieceJointe1.setId(null);
        assertThat(pieceJointe1).isNotEqualTo(pieceJointe2);
    }
}
