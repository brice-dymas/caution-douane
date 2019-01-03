package com.cami.service;

import com.cami.domain.DemandeImportation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DemandeImportation.
 */
public interface DemandeImportationService {

    /**
     * Save a demandeImportation.
     *
     * @param demandeImportation the entity to save
     * @return the persisted entity
     */
    DemandeImportation save(DemandeImportation demandeImportation);

    /**
     * Get all the demandeImportations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DemandeImportation> findAll(Pageable pageable);


    /**
     * Get the "id" demandeImportation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DemandeImportation> findOne(Long id);

    /**
     * Delete the "id" demandeImportation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
