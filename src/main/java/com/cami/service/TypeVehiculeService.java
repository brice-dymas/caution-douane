package com.cami.service;

import com.cami.domain.TypeVehicule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TypeVehicule.
 */
public interface TypeVehiculeService {

    /**
     * Save a typeVehicule.
     *
     * @param typeVehicule the entity to save
     * @return the persisted entity
     */
    TypeVehicule save(TypeVehicule typeVehicule);

    /**
     * Get all the typeVehicules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TypeVehicule> findAll(Pageable pageable);


    /**
     * Get the "id" typeVehicule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TypeVehicule> findOne(Long id);

    /**
     * Delete the "id" typeVehicule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
