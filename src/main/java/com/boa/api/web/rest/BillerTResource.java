package com.boa.api.web.rest;

import com.boa.api.domain.BillerT;
import com.boa.api.service.BillerTService;
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
 * REST controller for managing {@link com.boa.api.domain.BillerT}.
 */
@RestController
@RequestMapping("/api")
public class BillerTResource {

    private final Logger log = LoggerFactory.getLogger(BillerTResource.class);

    private static final String ENTITY_NAME = "billerT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillerTService billerTService;

    public BillerTResource(BillerTService billerTService) {
        this.billerTService = billerTService;
    }

    /**
     * {@code POST  /biller-ts} : Create a new billerT.
     *
     * @param billerT the billerT to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billerT, or with status {@code 400 (Bad Request)} if the billerT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/biller-ts")
    public ResponseEntity<BillerT> createBillerT(@RequestBody BillerT billerT) throws URISyntaxException {
        log.debug("REST request to save BillerT : {}", billerT);
        if (billerT.getId() != null) {
            throw new BadRequestAlertException("A new billerT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillerT result = billerTService.save(billerT);
        return ResponseEntity.created(new URI("/api/biller-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /biller-ts} : Updates an existing billerT.
     *
     * @param billerT the billerT to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billerT,
     * or with status {@code 400 (Bad Request)} if the billerT is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billerT couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/biller-ts")
    public ResponseEntity<BillerT> updateBillerT(@RequestBody BillerT billerT) throws URISyntaxException {
        log.debug("REST request to update BillerT : {}", billerT);
        if (billerT.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillerT result = billerTService.save(billerT);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, billerT.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /biller-ts} : get all the billerTS.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billerTS in body.
     */
    @GetMapping("/biller-ts")
    public List<BillerT> getAllBillerTS() {
        log.debug("REST request to get all BillerTS");
        return billerTService.findAll();
    }

    /**
     * {@code GET  /biller-ts/:id} : get the "id" billerT.
     *
     * @param id the id of the billerT to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billerT, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/biller-ts/{id}")
    public ResponseEntity<BillerT> getBillerT(@PathVariable Long id) {
        log.debug("REST request to get BillerT : {}", id);
        Optional<BillerT> billerT = billerTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billerT);
    }

    /**
     * {@code DELETE  /biller-ts/:id} : delete the "id" billerT.
     *
     * @param id the id of the billerT to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    /*@DeleteMapping("/biller-ts/{id}")
    public ResponseEntity<Void> deleteBillerT(@PathVariable Long id) {
        log.debug("REST request to delete BillerT : {}", id);
        billerTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }*/
}
