package com.boa.api.repository;

import com.boa.api.domain.BillerT;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BillerT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillerTRepository extends JpaRepository<BillerT, Long> {

    @Query("Select id from BillerT where billerCode=?1")
    public Long findIdByBillerCode(String billerCode);
}
