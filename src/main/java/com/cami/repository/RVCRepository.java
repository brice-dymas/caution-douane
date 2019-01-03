package com.cami.repository;

import com.cami.domain.RVC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RVC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RVCRepository extends JpaRepository<RVC, Long> {

}
