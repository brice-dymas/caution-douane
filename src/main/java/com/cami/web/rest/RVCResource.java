package com.cami.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cami.domain.RVC;
import com.cami.service.RVCService;
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
 * REST controller for managing RVC.
 */
@RestController
@RequestMapping("/api")
public class RVCResource {

    private final Logger log = LoggerFactory.getLogger(RVCResource.class);

    private static final String ENTITY_NAME = "rVC";

    private final RVCService rVCService;

    public RVCResource(RVCService rVCService) {
        this.rVCService = rVCService;
    }

    /**
     * POST  /rvcs : Create a new rVC.
     *
     * @param rVC the rVC to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rVC, or with status 400 (Bad Request) if the rVC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rvcs")
    @Timed
    public ResponseEntity<RVC> createRVC(@Valid @RequestBody RVC rVC) throws URISyntaxException {
        log.debug("REST request to save RVC : {}", rVC);
        if (rVC.getId() != null) {
            throw new BadRequestAlertException("A new rVC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RVC result = rVCService.save(rVC);
        return ResponseEntity.created(new URI("/api/rvcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rvcs : Updates an existing rVC.
     *
     * @param rVC the rVC to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rVC,
     * or with status 400 (Bad Request) if the rVC is not valid,
     * or with status 500 (Internal Server Error) if the rVC couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rvcs")
    @Timed
    public ResponseEntity<RVC> updateRVC(@Valid @RequestBody RVC rVC) throws URISyntaxException {
        log.debug("REST request to update RVC : {}", rVC);
        if (rVC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RVC result = rVCService.save(rVC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rVC.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rvcs : get all the rVCS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rVCS in body
     */
    @GetMapping("/rvcs")
    @Timed
    public ResponseEntity<List<RVC>> getAllRVCS(Pageable pageable) {
        log.debug("REST request to get a page of RVCS");
        Page<RVC> page = rVCService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rvcs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rvcs/:id : get the "id" rVC.
     *
     * @param id the id of the rVC to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rVC, or with status 404 (Not Found)
     */
    @GetMapping("/rvcs/{id}")
    @Timed
    public ResponseEntity<RVC> getRVC(@PathVariable Long id) {
        log.debug("REST request to get RVC : {}", id);
        Optional<RVC> rVC = rVCService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rVC);
    }

    /**
     * DELETE  /rvcs/:id : delete the "id" rVC.
     *
     * @param id the id of the rVC to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rvcs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRVC(@PathVariable Long id) {
        log.debug("REST request to delete RVC : {}", id);
        rVCService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
