package com.cami.service;

import com.cami.domain.RVC;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RVC.
 */
public interface RVCService {

    /**
     * Save a rVC.
     *
     * @param rVC the entity to save
     * @return the persisted entity
     */
    RVC save(RVC rVC);

    /**
     * Get all the rVCS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RVC> findAll(Pageable pageable);


    /**
     * Get the "id" rVC.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RVC> findOne(Long id);

    /**
     * Delete the "id" rVC.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
