package com.boa.api.repository;

import java.util.List;

import com.boa.api.domain.Tracking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long> {

    public List<Tracking>findByCnpsTransactionId(String transactionId);

}
