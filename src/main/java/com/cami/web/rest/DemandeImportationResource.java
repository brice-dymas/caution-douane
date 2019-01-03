package com.cami.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cami.domain.DemandeImportation;
import com.cami.service.DemandeImportationService;
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
 * REST controller for managing DemandeImportation.
 */
@RestController
@RequestMapping("/api")
public class DemandeImportationResource {

    private final Logger log = LoggerFactory.getLogger(DemandeImportationResource.class);

    private static final String ENTITY_NAME = "demandeImportation";

    private final DemandeImportationService demandeImportationService;

    public DemandeImportationResource(DemandeImportationService demandeImportationService) {
        this.demandeImportationService = demandeImportationService;
    }

    /**
     * POST  /demande-importations : Create a new demandeImportation.
     *
     * @param demandeImportation the demandeImportation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new demandeImportation, or with status 400 (Bad Request) if the demandeImportation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/demande-importations")
    @Timed
    public ResponseEntity<DemandeImportation> createDemandeImportation(@Valid @RequestBody DemandeImportation demandeImportation) throws URISyntaxException {
        log.debug("REST request to save DemandeImportation : {}", demandeImportation);
        if (demandeImportation.getId() != null) {
            throw new BadRequestAlertException("A new demandeImportation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeImportation result = demandeImportationService.save(demandeImportation);
        return ResponseEntity.created(new URI("/api/demande-importations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /demande-importations : Updates an existing demandeImportation.
     *
     * @param demandeImportation the demandeImportation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated demandeImportation,
     * or with status 400 (Bad Request) if the demandeImportation is not valid,
     * or with status 500 (Internal Server Error) if the demandeImportation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/demande-importations")
    @Timed
    public ResponseEntity<DemandeImportation> updateDemandeImportation(@Valid @RequestBody DemandeImportation demandeImportation) throws URISyntaxException {
        log.debug("REST request to update DemandeImportation : {}", demandeImportation);
        if (demandeImportation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DemandeImportation result = demandeImportationService.save(demandeImportation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, demandeImportation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /demande-importations : get all the demandeImportations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of demandeImportations in body
     */
    @GetMapping("/demande-importations")
    @Timed
    public ResponseEntity<List<DemandeImportation>> getAllDemandeImportations(Pageable pageable) {
        log.debug("REST request to get a page of DemandeImportations");
        Page<DemandeImportation> page = demandeImportationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/demande-importations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /demande-importations/:id : get the "id" demandeImportation.
     *
     * @param id the id of the demandeImportation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the demandeImportation, or with status 404 (Not Found)
     */
    @GetMapping("/demande-importations/{id}")
    @Timed
    public ResponseEntity<DemandeImportation> getDemandeImportation(@PathVariable Long id) {
        log.debug("REST request to get DemandeImportation : {}", id);
        Optional<DemandeImportation> demandeImportation = demandeImportationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeImportation);
    }

    /**
     * DELETE  /demande-importations/:id : delete the "id" demandeImportation.
     *
     * @param id the id of the demandeImportation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/demande-importations/{id}")
    @Timed
    public ResponseEntity<Void> deleteDemandeImportation(@PathVariable Long id) {
        log.debug("REST request to delete DemandeImportation : {}", id);
        demandeImportationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
