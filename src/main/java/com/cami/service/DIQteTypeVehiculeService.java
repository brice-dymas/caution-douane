package com.cami.service;

import com.cami.domain.DIQteTypeVehicule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DIQteTypeVehicule.
 */
public interface DIQteTypeVehiculeService {

    /**
     * Save a dIQteTypeVehicule.
     *
     * @param dIQteTypeVehicule the entity to save
     * @return the persisted entity
     */
    DIQteTypeVehicule save(DIQteTypeVehicule dIQteTypeVehicule);

    /**
     * Get all the dIQteTypeVehicules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DIQteTypeVehicule> findAll(Pageable pageable);


    /**
     * Get the "id" dIQteTypeVehicule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DIQteTypeVehicule> findOne(Long id);

    /**
     * Delete the "id" dIQteTypeVehicule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
