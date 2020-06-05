package com.boa.api.service;

import com.boa.api.domain.BillerT;
import com.boa.api.repository.BillerTRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BillerT}.
 */
@Service
@Transactional
public class BillerTService {

    private final Logger log = LoggerFactory.getLogger(BillerTService.class);

    private final BillerTRepository billerTRepository;

    public BillerTService(BillerTRepository billerTRepository) {
        this.billerTRepository = billerTRepository;
    }

    /**
     * Save a billerT.
     *
     * @param billerT the entity to save.
     * @return the persisted entity.
     */
    public BillerT save(BillerT billerT) {
        log.debug("Request to save BillerT : {}", billerT);
        return billerTRepository.save(billerT);
    }

    /**
     * Get all the billerTS.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BillerT> findAll() {
        log.debug("Request to get all BillerTS");
        return billerTRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Long findByBillerCode(String billerCode) {
        log.debug("Request to get findByBillerCode");
        return billerTRepository.findIdByBillerCode(billerCode);
    }


    /**
     * Get one billerT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BillerT> findOne(Long id) {
        log.debug("Request to get BillerT : {}", id);
        return billerTRepository.findById(id);
    }

    /**
     * Delete the billerT by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BillerT : {}", id);
        billerTRepository.deleteById(id);
    }
}
