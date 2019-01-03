package com.cami.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cami.domain.TypeVehicule;
import com.cami.service.TypeVehiculeService;
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
 * REST controller for managing TypeVehicule.
 */
@RestController
@RequestMapping("/api")
public class TypeVehiculeResource {

    private final Logger log = LoggerFactory.getLogger(TypeVehiculeResource.class);

    private static final String ENTITY_NAME = "typeVehicule";

    private final TypeVehiculeService typeVehiculeService;

    public TypeVehiculeResource(TypeVehiculeService typeVehiculeService) {
        this.typeVehiculeService = typeVehiculeService;
    }

    /**
     * POST  /type-vehicules : Create a new typeVehicule.
     *
     * @param typeVehicule the typeVehicule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeVehicule, or with status 400 (Bad Request) if the typeVehicule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-vehicules")
    @Timed
    public ResponseEntity<TypeVehicule> createTypeVehicule(@Valid @RequestBody TypeVehicule typeVehicule) throws URISyntaxException {
        log.debug("REST request to save TypeVehicule : {}", typeVehicule);
        if (typeVehicule.getId() != null) {
            throw new BadRequestAlertException("A new typeVehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeVehicule result = typeVehiculeService.save(typeVehicule);
        return ResponseEntity.created(new URI("/api/type-vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-vehicules : Updates an existing typeVehicule.
     *
     * @param typeVehicule the typeVehicule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeVehicule,
     * or with status 400 (Bad Request) if the typeVehicule is not valid,
     * or with status 500 (Internal Server Error) if the typeVehicule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-vehicules")
    @Timed
    public ResponseEntity<TypeVehicule> updateTypeVehicule(@Valid @RequestBody TypeVehicule typeVehicule) throws URISyntaxException {
        log.debug("REST request to update TypeVehicule : {}", typeVehicule);
        if (typeVehicule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeVehicule result = typeVehiculeService.save(typeVehicule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeVehicule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-vehicules : get all the typeVehicules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeVehicules in body
     */
    @GetMapping("/type-vehicules")
    @Timed
    public ResponseEntity<List<TypeVehicule>> getAllTypeVehicules(Pageable pageable) {
        log.debug("REST request to get a page of TypeVehicules");
        Page<TypeVehicule> page = typeVehiculeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-vehicules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-vehicules/:id : get the "id" typeVehicule.
     *
     * @param id the id of the typeVehicule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeVehicule, or with status 404 (Not Found)
     */
    @GetMapping("/type-vehicules/{id}")
    @Timed
    public ResponseEntity<TypeVehicule> getTypeVehicule(@PathVariable Long id) {
        log.debug("REST request to get TypeVehicule : {}", id);
        Optional<TypeVehicule> typeVehicule = typeVehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeVehicule);
    }

    /**
     * DELETE  /type-vehicules/:id : delete the "id" typeVehicule.
     *
     * @param id the id of the typeVehicule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-vehicules/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeVehicule(@PathVariable Long id) {
        log.debug("REST request to delete TypeVehicule : {}", id);
        typeVehiculeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
