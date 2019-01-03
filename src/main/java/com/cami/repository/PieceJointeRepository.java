package com.cami.repository;

import com.cami.domain.PieceJointe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PieceJointe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {

}
