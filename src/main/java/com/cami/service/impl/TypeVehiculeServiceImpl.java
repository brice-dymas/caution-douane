package com.cami.service.impl;

import com.cami.service.TypeVehiculeService;
import com.cami.domain.TypeVehicule;
import com.cami.repository.TypeVehiculeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeVehicule.
 */
@Service
@Transactional
public class TypeVehiculeServiceImpl implements TypeVehiculeService {

    private final Logger log = LoggerFactory.getLogger(TypeVehiculeServiceImpl.class);

    private final TypeVehiculeRepository typeVehiculeRepository;

    public TypeVehiculeServiceImpl(TypeVehiculeRepository typeVehiculeRepository) {
        this.typeVehiculeRepository = typeVehiculeRepository;
    }

    /**
     * Save a typeVehicule.
     *
     * @param typeVehicule the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeVehicule save(TypeVehicule typeVehicule) {
        log.debug("Request to save TypeVehicule : {}", typeVehicule);
        return typeVehiculeRepository.save(typeVehicule);
    }

    /**
     * Get all the typeVehicules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeVehicule> findAll(Pageable pageable) {
        log.debug("Request to get all TypeVehicules");
        return typeVehiculeRepository.findAll(pageable);
    }


    /**
     * Get one typeVehicule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeVehicule> findOne(Long id) {
        log.debug("Request to get TypeVehicule : {}", id);
        return typeVehiculeRepository.findById(id);
    }

    /**
     * Delete the typeVehicule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeVehicule : {}", id);
        typeVehiculeRepository.deleteById(id);
    }
}
