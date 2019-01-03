package com.cami.service;

import com.cami.domain.Caution;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Caution.
 */
public interface CautionService {

    /**
     * Save a caution.
     *
     * @param caution the entity to save
     * @return the persisted entity
     */
    Caution save(Caution caution);

    /**
     * Get all the cautions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Caution> findAll(Pageable pageable);


    /**
     * Get the "id" caution.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Caution> findOne(Long id);

    /**
     * Delete the "id" caution.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
