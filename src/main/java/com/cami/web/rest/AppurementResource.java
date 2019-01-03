package com.cami.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cami.domain.Appurement;
import com.cami.service.AppurementService;
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
 * REST controller for managing Appurement.
 */
@RestController
@RequestMapping("/api")
public class AppurementResource {

    private final Logger log = LoggerFactory.getLogger(AppurementResource.class);

    private static final String ENTITY_NAME = "appurement";

    private final AppurementService appurementService;

    public AppurementResource(AppurementService appurementService) {
        this.appurementService = appurementService;
    }

    /**
     * POST  /appurements : Create a new appurement.
     *
     * @param appurement the appurement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appurement, or with status 400 (Bad Request) if the appurement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/appurements")
    @Timed
    public ResponseEntity<Appurement> createAppurement(@Valid @RequestBody Appurement appurement) throws URISyntaxException {
        log.debug("REST request to save Appurement : {}", appurement);
        if (appurement.getId() != null) {
            throw new BadRequestAlertException("A new appurement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appurement result = appurementService.save(appurement);
        return ResponseEntity.created(new URI("/api/appurements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appurements : Updates an existing appurement.
     *
     * @param appurement the appurement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appurement,
     * or with status 400 (Bad Request) if the appurement is not valid,
     * or with status 500 (Internal Server Error) if the appurement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/appurements")
    @Timed
    public ResponseEntity<Appurement> updateAppurement(@Valid @RequestBody Appurement appurement) throws URISyntaxException {
        log.debug("REST request to update Appurement : {}", appurement);
        if (appurement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Appurement result = appurementService.save(appurement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appurement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appurements : get all the appurements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of appurements in body
     */
    @GetMapping("/appurements")
    @Timed
    public ResponseEntity<List<Appurement>> getAllAppurements(Pageable pageable) {
        log.debug("REST request to get a page of Appurements");
        Page<Appurement> page = appurementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/appurements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /appurements/:id : get the "id" appurement.
     *
     * @param id the id of the appurement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appurement, or with status 404 (Not Found)
     */
    @GetMapping("/appurements/{id}")
    @Timed
    public ResponseEntity<Appurement> getAppurement(@PathVariable Long id) {
        log.debug("REST request to get Appurement : {}", id);
        Optional<Appurement> appurement = appurementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appurement);
    }

    /**
     * DELETE  /appurements/:id : delete the "id" appurement.
     *
     * @param id the id of the appurement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/appurements/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppurement(@PathVariable Long id) {
        log.debug("REST request to delete Appurement : {}", id);
        appurementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
