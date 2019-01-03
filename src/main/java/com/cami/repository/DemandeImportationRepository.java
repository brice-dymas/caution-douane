package com.cami.repository;

import com.cami.domain.DemandeImportation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DemandeImportation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeImportationRepository extends JpaRepository<DemandeImportation, Long> {

}
