package com.cami.service.impl;

import com.cami.service.PieceJointeService;
import com.cami.domain.PieceJointe;
import com.cami.repository.PieceJointeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PieceJointe.
 */
@Service
@Transactional
public class PieceJointeServiceImpl implements PieceJointeService {

    private final Logger log = LoggerFactory.getLogger(PieceJointeServiceImpl.class);

    private final PieceJointeRepository pieceJointeRepository;

    public PieceJointeServiceImpl(PieceJointeRepository pieceJointeRepository) {
        this.pieceJointeRepository = pieceJointeRepository;
    }

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointe the entity to save
     * @return the persisted entity
     */
    @Override
    public PieceJointe save(PieceJointe pieceJointe) {
        log.debug("Request to save PieceJointe : {}", pieceJointe);
        return pieceJointeRepository.save(pieceJointe);
    }

    /**
     * Get all the pieceJointes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PieceJointe> findAll(Pageable pageable) {
        log.debug("Request to get all PieceJointes");
        return pieceJointeRepository.findAll(pageable);
    }


    /**
     * Get one pieceJointe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PieceJointe> findOne(Long id) {
        log.debug("Request to get PieceJointe : {}", id);
        return pieceJointeRepository.findById(id);
    }

    /**
     * Delete the pieceJointe by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PieceJointe : {}", id);
        pieceJointeRepository.deleteById(id);
    }
}
