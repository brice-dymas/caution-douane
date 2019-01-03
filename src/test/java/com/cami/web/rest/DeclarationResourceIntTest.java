package com.cami.web.rest;

import com.cami.CautiondouaneApp;

import com.cami.domain.Declaration;
import com.cami.repository.DeclarationRepository;
import com.cami.service.DeclarationService;
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
 * Test class for the DeclarationResource REST controller.
 *
 * @see DeclarationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CautiondouaneApp.class)
public class DeclarationResourceIntTest {

    private static final String DEFAULT_NUMERO_DECLARATION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DECLARATION = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_IM_7 = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IM_7 = "BBBBBBBBBB";

    @Autowired
    private DeclarationRepository declarationRepository;

    @Autowired
    private DeclarationService declarationService;

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

    private MockMvc restDeclarationMockMvc;

    private Declaration declaration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeclarationResource declarationResource = new DeclarationResource(declarationService);
        this.restDeclarationMockMvc = MockMvcBuilders.standaloneSetup(declarationResource)
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
    public static Declaration createEntity(EntityManager em) {
        Declaration declaration = new Declaration()
            .numeroDeclaration(DEFAULT_NUMERO_DECLARATION)
            .reference(DEFAULT_REFERENCE)
            .numeroIM7(DEFAULT_NUMERO_IM_7);
        return declaration;
    }

    @Before
    public void initTest() {
        declaration = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeclaration() throws Exception {
        int databaseSizeBeforeCreate = declarationRepository.findAll().size();

        // Create the Declaration
        restDeclarationMockMvc.perform(post("/api/declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(declaration)))
            .andExpect(status().isCreated());

        // Validate the Declaration in the database
        List<Declaration> declarationList = declarationRepository.findAll();
        assertThat(declarationList).hasSize(databaseSizeBeforeCreate + 1);
        Declaration testDeclaration = declarationList.get(declarationList.size() - 1);
        assertThat(testDeclaration.getNumeroDeclaration()).isEqualTo(DEFAULT_NUMERO_DECLARATION);
        assertThat(testDeclaration.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testDeclaration.getNumeroIM7()).isEqualTo(DEFAULT_NUMERO_IM_7);
    }

    @Test
    @Transactional
    public void createDeclarationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = declarationRepository.findAll().size();

        // Create the Declaration with an existing ID
        declaration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeclarationMockMvc.perform(post("/api/declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(declaration)))
            .andExpect(status().isBadRequest());

        // Validate the Declaration in the database
        List<Declaration> declarationList = declarationRepository.findAll();
        assertThat(declarationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroDeclarationIsRequired() throws Exception {
        int databaseSizeBeforeTest = declarationRepository.findAll().size();
        // set the field null
        declaration.setNumeroDeclaration(null);

        // Create the Declaration, which fails.

        restDeclarationMockMvc.perform(post("/api/declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(declaration)))
            .andExpect(status().isBadRequest());

        List<Declaration> declarationList = declarationRepository.findAll();
        assertThat(declarationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = declarationRepository.findAll().size();
        // set the field null
        declaration.setReference(null);

        // Create the Declaration, which fails.

        restDeclarationMockMvc.perform(post("/api/declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(declaration)))
            .andExpect(status().isBadRequest());

        List<Declaration> declarationList = declarationRepository.findAll();
        assertThat(declarationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIM7IsRequired() throws Exception {
        int databaseSizeBeforeTest = declarationRepository.findAll().size();
        // set the field null
        declaration.setNumeroIM7(null);

        // Create the Declaration, which fails.

        restDeclarationMockMvc.perform(post("/api/declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(declaration)))
            .andExpect(status().isBadRequest());

        List<Declaration> declarationList = declarationRepository.findAll();
        assertThat(declarationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeclarations() throws Exception {
        // Initialize the database
        declarationRepository.saveAndFlush(declaration);

        // Get all the declarationList
        restDeclarationMockMvc.perform(get("/api/declarations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(declaration.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDeclaration").value(hasItem(DEFAULT_NUMERO_DECLARATION.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].numeroIM7").value(hasItem(DEFAULT_NUMERO_IM_7.toString())));
    }
    
    @Test
    @Transactional
    public void getDeclaration() throws Exception {
        // Initialize the database
        declarationRepository.saveAndFlush(declaration);

        // Get the declaration
        restDeclarationMockMvc.perform(get("/api/declarations/{id}", declaration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(declaration.getId().intValue()))
            .andExpect(jsonPath("$.numeroDeclaration").value(DEFAULT_NUMERO_DECLARATION.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.numeroIM7").value(DEFAULT_NUMERO_IM_7.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeclaration() throws Exception {
        // Get the declaration
        restDeclarationMockMvc.perform(get("/api/declarations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeclaration() throws Exception {
        // Initialize the database
        declarationService.save(declaration);

        int databaseSizeBeforeUpdate = declarationRepository.findAll().size();

        // Update the declaration
        Declaration updatedDeclaration = declarationRepository.findById(declaration.getId()).get();
        // Disconnect from session so that the updates on updatedDeclaration are not directly saved in db
        em.detach(updatedDeclaration);
        updatedDeclaration
            .numeroDeclaration(UPDATED_NUMERO_DECLARATION)
            .reference(UPDATED_REFERENCE)
            .numeroIM7(UPDATED_NUMERO_IM_7);

        restDeclarationMockMvc.perform(put("/api/declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeclaration)))
            .andExpect(status().isOk());

        // Validate the Declaration in the database
        List<Declaration> declarationList = declarationRepository.findAll();
        assertThat(declarationList).hasSize(databaseSizeBeforeUpdate);
        Declaration testDeclaration = declarationList.get(declarationList.size() - 1);
        assertThat(testDeclaration.getNumeroDeclaration()).isEqualTo(UPDATED_NUMERO_DECLARATION);
        assertThat(testDeclaration.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testDeclaration.getNumeroIM7()).isEqualTo(UPDATED_NUMERO_IM_7);
    }

    @Test
    @Transactional
    public void updateNonExistingDeclaration() throws Exception {
        int databaseSizeBeforeUpdate = declarationRepository.findAll().size();

        // Create the Declaration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclarationMockMvc.perform(put("/api/declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(declaration)))
            .andExpect(status().isBadRequest());

        // Validate the Declaration in the database
        List<Declaration> declarationList = declarationRepository.findAll();
        assertThat(declarationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeclaration() throws Exception {
        // Initialize the database
        declarationService.save(declaration);

        int databaseSizeBeforeDelete = declarationRepository.findAll().size();

        // Get the declaration
        restDeclarationMockMvc.perform(delete("/api/declarations/{id}", declaration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Declaration> declarationList = declarationRepository.findAll();
        assertThat(declarationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Declaration.class);
        Declaration declaration1 = new Declaration();
        declaration1.setId(1L);
        Declaration declaration2 = new Declaration();
        declaration2.setId(declaration1.getId());
        assertThat(declaration1).isEqualTo(declaration2);
        declaration2.setId(2L);
        assertThat(declaration1).isNotEqualTo(declaration2);
        declaration1.setId(null);
        assertThat(declaration1).isNotEqualTo(declaration2);
    }
}
