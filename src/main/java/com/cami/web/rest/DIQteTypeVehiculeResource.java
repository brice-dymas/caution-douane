package com.cami.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cami.domain.DIQteTypeVehicule;
import com.cami.service.DIQteTypeVehiculeService;
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
 * REST controller for managing DIQteTypeVehicule.
 */
@RestController
@RequestMapping("/api")
public class DIQteTypeVehiculeResource {

    private final Logger log = LoggerFactory.getLogger(DIQteTypeVehiculeResource.class);

    private static final String ENTITY_NAME = "dIQteTypeVehicule";

    private final DIQteTypeVehiculeService dIQteTypeVehiculeService;

    public DIQteTypeVehiculeResource(DIQteTypeVehiculeService dIQteTypeVehiculeService) {
        this.dIQteTypeVehiculeService = dIQteTypeVehiculeService;
    }

    /**
     * POST  /di-qte-type-vehicules : Create a new dIQteTypeVehicule.
     *
     * @param dIQteTypeVehicule the dIQteTypeVehicule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dIQteTypeVehicule, or with status 400 (Bad Request) if the dIQteTypeVehicule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/di-qte-type-vehicules")
    @Timed
    public ResponseEntity<DIQteTypeVehicule> createDIQteTypeVehicule(@Valid @RequestBody DIQteTypeVehicule dIQteTypeVehicule) throws URISyntaxException {
        log.debug("REST request to save DIQteTypeVehicule : {}", dIQteTypeVehicule);
        if (dIQteTypeVehicule.getId() != null) {
            throw new BadRequestAlertException("A new dIQteTypeVehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DIQteTypeVehicule result = dIQteTypeVehiculeService.save(dIQteTypeVehicule);
        return ResponseEntity.created(new URI("/api/di-qte-type-vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /di-qte-type-vehicules : Updates an existing dIQteTypeVehicule.
     *
     * @param dIQteTypeVehicule the dIQteTypeVehicule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dIQteTypeVehicule,
     * or with status 400 (Bad Request) if the dIQteTypeVehicule is not valid,
     * or with status 500 (Internal Server Error) if the dIQteTypeVehicule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/di-qte-type-vehicules")
    @Timed
    public ResponseEntity<DIQteTypeVehicule> updateDIQteTypeVehicule(@Valid @RequestBody DIQteTypeVehicule dIQteTypeVehicule) throws URISyntaxException {
        log.debug("REST request to update DIQteTypeVehicule : {}", dIQteTypeVehicule);
        if (dIQteTypeVehicule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DIQteTypeVehicule result = dIQteTypeVehiculeService.save(dIQteTypeVehicule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dIQteTypeVehicule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /di-qte-type-vehicules : get all the dIQteTypeVehicules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dIQteTypeVehicules in body
     */
    @GetMapping("/di-qte-type-vehicules")
    @Timed
    public ResponseEntity<List<DIQteTypeVehicule>> getAllDIQteTypeVehicules(Pageable pageable) {
        log.debug("REST request to get a page of DIQteTypeVehicules");
        Page<DIQteTypeVehicule> page = dIQteTypeVehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/di-qte-type-vehicules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /di-qte-type-vehicules/:id : get the "id" dIQteTypeVehicule.
     *
     * @param id the id of the dIQteTypeVehicule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dIQteTypeVehicule, or with status 404 (Not Found)
     */
    @GetMapping("/di-qte-type-vehicules/{id}")
    @Timed
    public ResponseEntity<DIQteTypeVehicule> getDIQteTypeVehicule(@PathVariable Long id) {
        log.debug("REST request to get DIQteTypeVehicule : {}", id);
        Optional<DIQteTypeVehicule> dIQteTypeVehicule = dIQteTypeVehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dIQteTypeVehicule);
    }

    /**
     * DELETE  /di-qte-type-vehicules/:id : delete the "id" dIQteTypeVehicule.
     *
     * @param id the id of the dIQteTypeVehicule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/di-qte-type-vehicules/{id}")
    @Timed
    public ResponseEntity<Void> deleteDIQteTypeVehicule(@PathVariable Long id) {
        log.debug("REST request to delete DIQteTypeVehicule : {}", id);
        dIQteTypeVehiculeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
