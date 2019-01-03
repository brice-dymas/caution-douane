package com.cami.service;

import com.cami.domain.Marque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Marque.
 */
public interface MarqueService {

    /**
     * Save a marque.
     *
     * @param marque the entity to save
     * @return the persisted entity
     */
    Marque save(Marque marque);

    /**
     * Get all the marques.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Marque> findAll(Pageable pageable);


    /**
     * Get the "id" marque.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Marque> findOne(Long id);

    /**
     * Delete the "id" marque.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
