package com.cami.service;

import com.cami.domain.Appurement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Appurement.
 */
public interface AppurementService {

    /**
     * Save a appurement.
     *
     * @param appurement the entity to save
     * @return the persisted entity
     */
    Appurement save(Appurement appurement);

    /**
     * Get all the appurements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Appurement> findAll(Pageable pageable);


    /**
     * Get the "id" appurement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Appurement> findOne(Long id);

    /**
     * Delete the "id" appurement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
