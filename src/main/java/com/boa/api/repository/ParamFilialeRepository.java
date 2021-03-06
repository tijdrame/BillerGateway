package com.boa.api.repository;

import com.boa.api.domain.ParamFiliale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParamFiliale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamFilialeRepository extends JpaRepository<ParamFiliale, Long> {
    ParamFiliale findByCodeFiliale(String code);
}
