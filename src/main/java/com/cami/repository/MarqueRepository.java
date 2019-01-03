package com.cami.repository;

import com.cami.domain.Marque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Marque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {

}
