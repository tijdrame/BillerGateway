package com.boa.api.web.rest;

import com.boa.api.domain.ParamFiliale;
import com.boa.api.service.ParamFilialeService;
import com.boa.api.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import springfox.documentation.annotations.ApiIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.boa.api.domain.ParamFiliale}.
 */
@RestController
@RequestMapping("/api")
@ApiIgnore
public class ParamFilialeResource {

    private final Logger log = LoggerFactory.getLogger(ParamFilialeResource.class);

    private static final String ENTITY_NAME = "paramFiliale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamFilialeService paramFilialeService;

    public ParamFilialeResource(ParamFilialeService paramFilialeService) {
        this.paramFilialeService = paramFilialeService;
    }

    /**
     * {@code POST  /param-filiales} : Create a new paramFiliale.
     *
     * @param paramFiliale the paramFiliale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramFiliale, or with status {@code 400 (Bad Request)} if the paramFiliale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/param-filiales")
    public ResponseEntity<ParamFiliale> createParamFiliale(@RequestBody ParamFiliale paramFiliale) throws URISyntaxException {
        log.debug("REST request to save ParamFiliale : {}", paramFiliale);
        if (paramFiliale.getId() != null) {
            throw new BadRequestAlertException("A new paramFiliale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParamFiliale result = paramFilialeService.save(paramFiliale);
        return ResponseEntity.created(new URI("/api/param-filiales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /param-filiales} : Updates an existing paramFiliale.
     *
     * @param paramFiliale the paramFiliale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramFiliale,
     * or with status {@code 400 (Bad Request)} if the paramFiliale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paramFiliale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/param-filiales")
    public ResponseEntity<ParamFiliale> updateParamFiliale(@RequestBody ParamFiliale paramFiliale) throws URISyntaxException {
        log.debug("REST request to update ParamFiliale : {}", paramFiliale);
        if (paramFiliale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParamFiliale result = paramFilialeService.save(paramFiliale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paramFiliale.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /param-filiales} : get all the paramFiliales.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paramFiliales in body.
     */
    @GetMapping("/param-filiales")
    public ResponseEntity<List<ParamFiliale>> getAllParamFiliales(Pageable pageable) {
        log.debug("REST request to get a page of ParamFiliales");
        Page<ParamFiliale> page = paramFilialeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /param-filiales/:id} : get the "id" paramFiliale.
     *
     * @param id the id of the paramFiliale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramFiliale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/param-filiales/{id}")
    public ResponseEntity<ParamFiliale> getParamFiliale(@PathVariable Long id) {
        log.debug("REST request to get ParamFiliale : {}", id);
        Optional<ParamFiliale> paramFiliale = paramFilialeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramFiliale);
    }

    /**
     * {@code DELETE  /param-filiales/:id} : delete the "id" paramFiliale.
     *
     * @param id the id of the paramFiliale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/param-filiales/{id}")
    public ResponseEntity<Void> deleteParamFiliale(@PathVariable Long id) {
        log.debug("REST request to delete ParamFiliale : {}", id);
        paramFilialeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
