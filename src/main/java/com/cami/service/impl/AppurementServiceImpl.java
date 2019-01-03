package com.cami.service.impl;

import com.cami.service.AppurementService;
import com.cami.domain.Appurement;
import com.cami.repository.AppurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Appurement.
 */
@Service
@Transactional
public class AppurementServiceImpl implements AppurementService {

    private final Logger log = LoggerFactory.getLogger(AppurementServiceImpl.class);

    private final AppurementRepository appurementRepository;

    public AppurementServiceImpl(AppurementRepository appurementRepository) {
        this.appurementRepository = appurementRepository;
    }

    /**
     * Save a appurement.
     *
     * @param appurement the entity to save
     * @return the persisted entity
     */
    @Override
    public Appurement save(Appurement appurement) {
        log.debug("Request to save Appurement : {}", appurement);
        return appurementRepository.save(appurement);
    }

    /**
     * Get all the appurements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Appurement> findAll(Pageable pageable) {
        log.debug("Request to get all Appurements");
        return appurementRepository.findAll(pageable);
    }


    /**
     * Get one appurement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Appurement> findOne(Long id) {
        log.debug("Request to get Appurement : {}", id);
        return appurementRepository.findById(id);
    }

    /**
     * Delete the appurement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Appurement : {}", id);
        appurementRepository.deleteById(id);
    }
}
