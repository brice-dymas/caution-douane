package com.cami.service.impl;

import com.cami.service.DeclarationService;
import com.cami.domain.Declaration;
import com.cami.repository.DeclarationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Declaration.
 */
@Service
@Transactional
public class DeclarationServiceImpl implements DeclarationService {

    private final Logger log = LoggerFactory.getLogger(DeclarationServiceImpl.class);

    private final DeclarationRepository declarationRepository;

    public DeclarationServiceImpl(DeclarationRepository declarationRepository) {
        this.declarationRepository = declarationRepository;
    }

    /**
     * Save a declaration.
     *
     * @param declaration the entity to save
     * @return the persisted entity
     */
    @Override
    public Declaration save(Declaration declaration) {
        log.debug("Request to save Declaration : {}", declaration);
        return declarationRepository.save(declaration);
    }

    /**
     * Get all the declarations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Declaration> findAll(Pageable pageable) {
        log.debug("Request to get all Declarations");
        return declarationRepository.findAll(pageable);
    }


    /**
     * Get one declaration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Declaration> findOne(Long id) {
        log.debug("Request to get Declaration : {}", id);
        return declarationRepository.findById(id);
    }

    /**
     * Delete the declaration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Declaration : {}", id);
        declarationRepository.deleteById(id);
    }
}
