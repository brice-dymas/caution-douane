package com.cami.repository;

import com.cami.domain.Appurement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Appurement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppurementRepository extends JpaRepository<Appurement, Long> {

}
