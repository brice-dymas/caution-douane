package com.cami.service;

import com.cami.domain.Banque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Banque.
 */
public interface BanqueService {

    /**
     * Save a banque.
     *
     * @param banque the entity to save
     * @return the persisted entity
     */
    Banque save(Banque banque);

    /**
     * Get all the banques.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Banque> findAll(Pageable pageable);


    /**
     * Get the "id" banque.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Banque> findOne(Long id);

    /**
     * Delete the "id" banque.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
