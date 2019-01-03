package com.cami.service.impl;

import com.cami.service.DemandeImportationService;
import com.cami.domain.DemandeImportation;
import com.cami.repository.DemandeImportationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DemandeImportation.
 */
@Service
@Transactional
public class DemandeImportationServiceImpl implements DemandeImportationService {

    private final Logger log = LoggerFactory.getLogger(DemandeImportationServiceImpl.class);

    private final DemandeImportationRepository demandeImportationRepository;

    public DemandeImportationServiceImpl(DemandeImportationRepository demandeImportationRepository) {
        this.demandeImportationRepository = demandeImportationRepository;
    }

    /**
     * Save a demandeImportation.
     *
     * @param demandeImportation the entity to save
     * @return the persisted entity
     */
    @Override
    public DemandeImportation save(DemandeImportation demandeImportation) {
        log.debug("Request to save DemandeImportation : {}", demandeImportation);
        return demandeImportationRepository.save(demandeImportation);
    }

    /**
     * Get all the demandeImportations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DemandeImportation> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeImportations");
        return demandeImportationRepository.findAll(pageable);
    }


    /**
     * Get one demandeImportation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeImportation> findOne(Long id) {
        log.debug("Request to get DemandeImportation : {}", id);
        return demandeImportationRepository.findById(id);
    }

    /**
     * Delete the demandeImportation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandeImportation : {}", id);
        demandeImportationRepository.deleteById(id);
    }
}
