package com.boa.api.service;

import com.boa.api.domain.ParamFiliale;
import com.boa.api.repository.ParamFilialeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ParamFiliale}.
 */
@Service
@Transactional
public class ParamFilialeService {

    private final Logger log = LoggerFactory.getLogger(ParamFilialeService.class);

    private final ParamFilialeRepository paramFilialeRepository;

    public ParamFilialeService(ParamFilialeRepository paramFilialeRepository) {
        this.paramFilialeRepository = paramFilialeRepository;
    }

    /**
     * Save a paramFiliale.
     *
     * @param paramFiliale the entity to save.
     * @return the persisted entity.
     */
    public ParamFiliale save(ParamFiliale paramFiliale) {
        log.debug("Request to save ParamFiliale : {}", paramFiliale);
        return paramFilialeRepository.save(paramFiliale);
    }

    /**
     * Get all the paramFiliales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ParamFiliale> findAll(Pageable pageable) {
        log.debug("Request to get all ParamFiliales");
        return paramFilialeRepository.findAll(pageable);
    }


    /**
     * Get one paramFiliale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParamFiliale> findOne(Long id) {
        log.debug("Request to get ParamFiliale : {}", id);
        return paramFilialeRepository.findById(id);
    }

    /**
     * Delete the paramFiliale by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParamFiliale : {}", id);
        paramFilialeRepository.deleteById(id);
    }
}
