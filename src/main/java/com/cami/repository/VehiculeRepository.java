package com.cami.repository;

import com.cami.domain.Vehicule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

}
