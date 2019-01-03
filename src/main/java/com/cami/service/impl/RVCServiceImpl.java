package com.cami.service.impl;

import com.cami.service.RVCService;
import com.cami.domain.RVC;
import com.cami.repository.RVCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RVC.
 */
@Service
@Transactional
public class RVCServiceImpl implements RVCService {

    private final Logger log = LoggerFactory.getLogger(RVCServiceImpl.class);

    private final RVCRepository rVCRepository;

    public RVCServiceImpl(RVCRepository rVCRepository) {
        this.rVCRepository = rVCRepository;
    }

    /**
     * Save a rVC.
     *
     * @param rVC the entity to save
     * @return the persisted entity
     */
    @Override
    public RVC save(RVC rVC) {
        log.debug("Request to save RVC : {}", rVC);
        return rVCRepository.save(rVC);
    }

    /**
     * Get all the rVCS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RVC> findAll(Pageable pageable) {
        log.debug("Request to get all RVCS");
        return rVCRepository.findAll(pageable);
    }


    /**
     * Get one rVC by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RVC> findOne(Long id) {
        log.debug("Request to get RVC : {}", id);
        return rVCRepository.findById(id);
    }

    /**
     * Delete the rVC by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RVC : {}", id);
        rVCRepository.deleteById(id);
    }
}
