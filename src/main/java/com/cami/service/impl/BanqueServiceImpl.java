package com.cami.service.impl;

import com.cami.service.BanqueService;
import com.cami.domain.Banque;
import com.cami.repository.BanqueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Banque.
 */
@Service
@Transactional
public class BanqueServiceImpl implements BanqueService {

    private final Logger log = LoggerFactory.getLogger(BanqueServiceImpl.class);

    private final BanqueRepository banqueRepository;

    public BanqueServiceImpl(BanqueRepository banqueRepository) {
        this.banqueRepository = banqueRepository;
    }

    /**
     * Save a banque.
     *
     * @param banque the entity to save
     * @return the persisted entity
     */
    @Override
    public Banque save(Banque banque) {
        log.debug("Request to save Banque : {}", banque);
        return banqueRepository.save(banque);
    }

    /**
     * Get all the banques.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Banque> findAll(Pageable pageable) {
        log.debug("Request to get all Banques");
        return banqueRepository.findAll(pageable);
    }


    /**
     * Get one banque by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Banque> findOne(Long id) {
        log.debug("Request to get Banque : {}", id);
        return banqueRepository.findById(id);
    }

    /**
     * Delete the banque by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Banque : {}", id);
        banqueRepository.deleteById(id);
    }
}
