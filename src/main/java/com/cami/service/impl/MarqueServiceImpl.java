package com.cami.service.impl;

import com.cami.service.MarqueService;
import com.cami.domain.Marque;
import com.cami.repository.MarqueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Marque.
 */
@Service
@Transactional
public class MarqueServiceImpl implements MarqueService {

    private final Logger log = LoggerFactory.getLogger(MarqueServiceImpl.class);

    private final MarqueRepository marqueRepository;

    public MarqueServiceImpl(MarqueRepository marqueRepository) {
        this.marqueRepository = marqueRepository;
    }

    /**
     * Save a marque.
     *
     * @param marque the entity to save
     * @return the persisted entity
     */
    @Override
    public Marque save(Marque marque) {
        log.debug("Request to save Marque : {}", marque);
        return marqueRepository.save(marque);
    }

    /**
     * Get all the marques.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Marque> findAll(Pageable pageable) {
        log.debug("Request to get all Marques");
        return marqueRepository.findAll(pageable);
    }


    /**
     * Get one marque by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Marque> findOne(Long id) {
        log.debug("Request to get Marque : {}", id);
        return marqueRepository.findById(id);
    }

    /**
     * Delete the marque by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Marque : {}", id);
        marqueRepository.deleteById(id);
    }
}
