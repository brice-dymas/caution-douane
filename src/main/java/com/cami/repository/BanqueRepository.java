package com.cami.repository;

import com.cami.domain.Banque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Banque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanqueRepository extends JpaRepository<Banque, Long> {

}
