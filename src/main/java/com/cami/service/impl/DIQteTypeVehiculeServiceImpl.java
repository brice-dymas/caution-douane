package com.cami.service.impl;

import com.cami.service.DIQteTypeVehiculeService;
import com.cami.domain.DIQteTypeVehicule;
import com.cami.repository.DIQteTypeVehiculeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DIQteTypeVehicule.
 */
@Service
@Transactional
public class DIQteTypeVehiculeServiceImpl implements DIQteTypeVehiculeService {

    private final Logger log = LoggerFactory.getLogger(DIQteTypeVehiculeServiceImpl.class);

    private final DIQteTypeVehiculeRepository dIQteTypeVehiculeRepository;

    public DIQteTypeVehiculeServiceImpl(DIQteTypeVehiculeRepository dIQteTypeVehiculeRepository) {
        this.dIQteTypeVehiculeRepository = dIQteTypeVehiculeRepository;
    }

    /**
     * Save a dIQteTypeVehicule.
     *
     * @param dIQteTypeVehicule the entity to save
     * @return the persisted entity
     */
    @Override
    public DIQteTypeVehicule save(DIQteTypeVehicule dIQteTypeVehicule) {
        log.debug("Request to save DIQteTypeVehicule : {}", dIQteTypeVehicule);
        return dIQteTypeVehiculeRepository.save(dIQteTypeVehicule);
    }

    /**
     * Get all the dIQteTypeVehicules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DIQteTypeVehicule> findAll(Pageable pageable) {
        log.debug("Request to get all DIQteTypeVehicules");
        return dIQteTypeVehiculeRepository.findAll(pageable);
    }


    /**
     * Get one dIQteTypeVehicule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DIQteTypeVehicule> findOne(Long id) {
        log.debug("Request to get DIQteTypeVehicule : {}", id);
        return dIQteTypeVehiculeRepository.findById(id);
    }

    /**
     * Delete the dIQteTypeVehicule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DIQteTypeVehicule : {}", id);
        dIQteTypeVehiculeRepository.deleteById(id);
    }
}
