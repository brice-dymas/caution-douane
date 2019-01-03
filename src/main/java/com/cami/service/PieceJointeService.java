package com.cami.service;

import com.cami.domain.PieceJointe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PieceJointe.
 */
public interface PieceJointeService {

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointe the entity to save
     * @return the persisted entity
     */
    PieceJointe save(PieceJointe pieceJointe);

    /**
     * Get all the pieceJointes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PieceJointe> findAll(Pageable pageable);


    /**
     * Get the "id" pieceJointe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PieceJointe> findOne(Long id);

    /**
     * Delete the "id" pieceJointe.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
