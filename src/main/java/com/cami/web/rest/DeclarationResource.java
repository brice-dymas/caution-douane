package com.cami.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cami.domain.Declaration;
import com.cami.service.DeclarationService;
import com.cami.web.rest.errors.BadRequestAlertException;
import com.cami.web.rest.util.HeaderUtil;
import com.cami.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Declaration.
 */
@RestController
@RequestMapping("/api")
public class DeclarationResource {

    private final Logger log = LoggerFactory.getLogger(DeclarationResource.class);

    private static final String ENTITY_NAME = "declaration";

    private final DeclarationService declarationService;

    public DeclarationResource(DeclarationService declarationService) {
        this.declarationService = declarationService;
    }

    /**
     * POST  /declarations : Create a new declaration.
     *
     * @param declaration the declaration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new declaration, or with status 400 (Bad Request) if the declaration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/declarations")
    @Timed
    public ResponseEntity<Declaration> createDeclaration(@Valid @RequestBody Declaration declaration) throws URISyntaxException {
        log.debug("REST request to save Declaration : {}", declaration);
        if (declaration.getId() != null) {
            throw new BadRequestAlertException("A new declaration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Declaration result = declarationService.save(declaration);
        return ResponseEntity.created(new URI("/api/declarations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /declarations : Updates an existing declaration.
     *
     * @param declaration the declaration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated declaration,
     * or with status 400 (Bad Request) if the declaration is not valid,
     * or with status 500 (Internal Server Error) if the declaration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/declarations")
    @Timed
    public ResponseEntity<Declaration> updateDeclaration(@Valid @RequestBody Declaration declaration) throws URISyntaxException {
        log.debug("REST request to update Declaration : {}", declaration);
        if (declaration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Declaration result = declarationService.save(declaration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, declaration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /declarations : get all the declarations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of declarations in body
     */
    @GetMapping("/declarations")
    @Timed
    public ResponseEntity<List<Declaration>> getAllDeclarations(Pageable pageable) {
        log.debug("REST request to get a page of Declarations");
        Page<Declaration> page = declarationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/declarations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /declarations/:id : get the "id" declaration.
     *
     * @param id the id of the declaration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the declaration, or with status 404 (Not Found)
     */
    @GetMapping("/declarations/{id}")
    @Timed
    public ResponseEntity<Declaration> getDeclaration(@PathVariable Long id) {
        log.debug("REST request to get Declaration : {}", id);
        Optional<Declaration> declaration = declarationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(declaration);
    }

    /**
     * DELETE  /declarations/:id : delete the "id" declaration.
     *
     * @param id the id of the declaration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/declarations/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeclaration(@PathVariable Long id) {
        log.debug("REST request to delete Declaration : {}", id);
        declarationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
