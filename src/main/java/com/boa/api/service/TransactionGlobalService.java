package com.boa.api.service;

import com.boa.api.domain.TransactionGlobal;
import com.boa.api.repository.TransactionGlobalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionGlobal}.
 */
@Service
@Transactional
public class TransactionGlobalService {

    private final Logger log = LoggerFactory.getLogger(TransactionGlobalService.class);

    private final TransactionGlobalRepository transactionGlobalRepository;

    public TransactionGlobalService(TransactionGlobalRepository transactionGlobalRepository) {
        this.transactionGlobalRepository = transactionGlobalRepository;
    }

    /**
     * Save a transactionGlobal.
     *
     * @param transactionGlobal the entity to save.
     * @return the persisted entity.
     */
    public TransactionGlobal save(TransactionGlobal transactionGlobal) {
        log.debug("Request to save TransactionGlobal : {}", transactionGlobal);
        return transactionGlobalRepository.save(transactionGlobal);
    }

    /**
     * Get all the transactionGlobals.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionGlobal> findAll() {
        log.debug("Request to get all TransactionGlobals");
        return transactionGlobalRepository.findAll();
    }


    /**
     * Get one transactionGlobal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionGlobal> findOne(Long id) {
        log.debug("Request to get TransactionGlobal : {}", id);
        return transactionGlobalRepository.findById(id);
    }

    /**
     * Delete the transactionGlobal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransactionGlobal : {}", id);
        transactionGlobalRepository.deleteById(id);
    }
}
