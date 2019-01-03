package com.cami.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cami.domain.Caution;
import com.cami.service.CautionService;
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
 * REST controller for managing Caution.
 */
@RestController
@RequestMapping("/api")
public class CautionResource {

    private final Logger log = LoggerFactory.getLogger(CautionResource.class);

    private static final String ENTITY_NAME = "caution";

    private final CautionService cautionService;

    public CautionResource(CautionService cautionService) {
        this.cautionService = cautionService;
    }

    /**
     * POST  /cautions : Create a new caution.
     *
     * @param caution the caution to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caution, or with status 400 (Bad Request) if the caution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cautions")
    @Timed
    public ResponseEntity<Caution> createCaution(@Valid @RequestBody Caution caution) throws URISyntaxException {
        log.debug("REST request to save Caution : {}", caution);
        if (caution.getId() != null) {
            throw new BadRequestAlertException("A new caution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Caution result = cautionService.save(caution);
        return ResponseEntity.created(new URI("/api/cautions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cautions : Updates an existing caution.
     *
     * @param caution the caution to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caution,
     * or with status 400 (Bad Request) if the caution is not valid,
     * or with status 500 (Internal Server Error) if the caution couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cautions")
    @Timed
    public ResponseEntity<Caution> updateCaution(@Valid @RequestBody Caution caution) throws URISyntaxException {
        log.debug("REST request to update Caution : {}", caution);
        if (caution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Caution result = cautionService.save(caution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cautions : get all the cautions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cautions in body
     */
    @GetMapping("/cautions")
    @Timed
    public ResponseEntity<List<Caution>> getAllCautions(Pageable pageable) {
        log.debug("REST request to get a page of Cautions");
        Page<Caution> page = cautionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cautions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cautions/:id : get the "id" caution.
     *
     * @param id the id of the caution to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caution, or with status 404 (Not Found)
     */
    @GetMapping("/cautions/{id}")
    @Timed
    public ResponseEntity<Caution> getCaution(@PathVariable Long id) {
        log.debug("REST request to get Caution : {}", id);
        Optional<Caution> caution = cautionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caution);
    }

    /**
     * DELETE  /cautions/:id : delete the "id" caution.
     *
     * @param id the id of the caution to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cautions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaution(@PathVariable Long id) {
        log.debug("REST request to delete Caution : {}", id);
        cautionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
