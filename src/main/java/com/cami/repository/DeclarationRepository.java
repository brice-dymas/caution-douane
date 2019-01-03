package com.cami.repository;

import com.cami.domain.Declaration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Declaration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Long> {

}
