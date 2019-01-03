package com.cami.service;

import com.cami.domain.Declaration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Declaration.
 */
public interface DeclarationService {

    /**
     * Save a declaration.
     *
     * @param declaration the entity to save
     * @return the persisted entity
     */
    Declaration save(Declaration declaration);

    /**
     * Get all the declarations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Declaration> findAll(Pageable pageable);


    /**
     * Get the "id" declaration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Declaration> findOne(Long id);

    /**
     * Delete the "id" declaration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
