package com.boa.api.web.rest;

import com.boa.api.domain.TransactionGlobal;
import com.boa.api.service.TransactionGlobalService;
import com.boa.api.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.boa.api.domain.TransactionGlobal}.
 */
@RestController
@RequestMapping("/api")
public class TransactionGlobalResource {

    private final Logger log = LoggerFactory.getLogger(TransactionGlobalResource.class);

    private static final String ENTITY_NAME = "transactionGlobal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionGlobalService transactionGlobalService;

    public TransactionGlobalResource(TransactionGlobalService transactionGlobalService) {
        this.transactionGlobalService = transactionGlobalService;
    }

    /**
     * {@code POST  /transaction-globals} : Create a new transactionGlobal.
     *
     * @param transactionGlobal the transactionGlobal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionGlobal, or with status {@code 400 (Bad Request)} if the transactionGlobal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-globals")
    public ResponseEntity<TransactionGlobal> createTransactionGlobal(@RequestBody TransactionGlobal transactionGlobal) throws URISyntaxException {
        log.debug("REST request to save TransactionGlobal : {}", transactionGlobal);
        if (transactionGlobal.getId() != null) {
            throw new BadRequestAlertException("A new transactionGlobal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionGlobal result = transactionGlobalService.save(transactionGlobal);
        return ResponseEntity.created(new URI("/api/transaction-globals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-globals} : Updates an existing transactionGlobal.
     *
     * @param transactionGlobal the transactionGlobal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionGlobal,
     * or with status {@code 400 (Bad Request)} if the transactionGlobal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionGlobal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-globals")
    public ResponseEntity<TransactionGlobal> updateTransactionGlobal(@RequestBody TransactionGlobal transactionGlobal) throws URISyntaxException {
        log.debug("REST request to update TransactionGlobal : {}", transactionGlobal);
        if (transactionGlobal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionGlobal result = transactionGlobalService.save(transactionGlobal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionGlobal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-globals} : get all the transactionGlobals.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionGlobals in body.
     */
    @GetMapping("/transaction-globals")
    public List<TransactionGlobal> getAllTransactionGlobals() {
        log.debug("REST request to get all TransactionGlobals");
        return transactionGlobalService.findAll();
    }

    /**
     * {@code GET  /transaction-globals/:id} : get the "id" transactionGlobal.
     *
     * @param id the id of the transactionGlobal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionGlobal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-globals/{id}")
    public ResponseEntity<TransactionGlobal> getTransactionGlobal(@PathVariable Long id) {
        log.debug("REST request to get TransactionGlobal : {}", id);
        Optional<TransactionGlobal> transactionGlobal = transactionGlobalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionGlobal);
    }

    /**
     * {@code DELETE  /transaction-globals/:id} : delete the "id" transactionGlobal.
     *
     * @param id the id of the transactionGlobal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    /*@DeleteMapping("/transaction-globals/{id}")
    public ResponseEntity<Void> deleteTransactionGlobal(@PathVariable Long id) {
        log.debug("REST request to delete TransactionGlobal : {}", id);
        transactionGlobalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }*/
}
