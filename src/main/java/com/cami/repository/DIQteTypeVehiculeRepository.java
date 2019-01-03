package com.cami.repository;

import com.cami.domain.DIQteTypeVehicule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DIQteTypeVehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DIQteTypeVehiculeRepository extends JpaRepository<DIQteTypeVehicule, Long> {

}
