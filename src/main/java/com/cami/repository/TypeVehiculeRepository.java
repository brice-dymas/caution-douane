package com.cami.repository;

import com.cami.domain.TypeVehicule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeVehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeVehiculeRepository extends JpaRepository<TypeVehicule, Long> {

}
