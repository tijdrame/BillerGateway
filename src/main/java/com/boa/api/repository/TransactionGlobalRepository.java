package com.boa.api.repository;

import com.boa.api.domain.TransactionGlobal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionGlobal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionGlobalRepository extends JpaRepository<TransactionGlobal, Long> {

}
