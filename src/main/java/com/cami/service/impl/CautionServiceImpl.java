package com.cami.service.impl;

import com.cami.service.CautionService;
import com.cami.domain.Caution;
import com.cami.repository.CautionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Caution.
 */
@Service
@Transactional
public class CautionServiceImpl implements CautionService {

    private final Logger log = LoggerFactory.getLogger(CautionServiceImpl.class);

    private final CautionRepository cautionRepository;

    public CautionServiceImpl(CautionRepository cautionRepository) {
        this.cautionRepository = cautionRepository;
    }

    /**
     * Save a caution.
     *
     * @param caution the entity to save
     * @return the persisted entity
     */
    @Override
    public Caution save(Caution caution) {
        log.debug("Request to save Caution : {}", caution);
        return cautionRepository.save(caution);
    }

    /**
     * Get all the cautions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Caution> findAll(Pageable pageable) {
        log.debug("Request to get all Cautions");
        return cautionRepository.findAll(pageable);
    }


    /**
     * Get one caution by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Caution> findOne(Long id) {
        log.debug("Request to get Caution : {}", id);
        return cautionRepository.findById(id);
    }

    /**
     * Delete the caution by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caution : {}", id);
        cautionRepository.deleteById(id);
    }
}
