package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.Clinic;

import com.softgroup.algorithm.clinic.repository.ClinicRepository;
import com.softgroup.algorithm.clinic.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Clinic.
 */
@RestController
@RequestMapping("/api")
public class ClinicResource {

    private final Logger log = LoggerFactory.getLogger(ClinicResource.class);

    private static final String ENTITY_NAME = "clinic";
        
    private final ClinicRepository clinicRepository;

    public ClinicResource(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    /**
     * POST  /clinics : Create a new clinic.
     *
     * @param clinic the clinic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clinic, or with status 400 (Bad Request) if the clinic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clinics")
    @Timed
    public ResponseEntity<Clinic> createClinic(@RequestBody Clinic clinic) throws URISyntaxException {
        log.debug("REST request to save Clinic : {}", clinic);
        if (clinic.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clinic cannot already have an ID")).body(null);
        }
        Clinic result = clinicRepository.save(clinic);
        return ResponseEntity.created(new URI("/api/clinics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clinics : Updates an existing clinic.
     *
     * @param clinic the clinic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clinic,
     * or with status 400 (Bad Request) if the clinic is not valid,
     * or with status 500 (Internal Server Error) if the clinic couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clinics")
    @Timed
    public ResponseEntity<Clinic> updateClinic(@RequestBody Clinic clinic) throws URISyntaxException {
        log.debug("REST request to update Clinic : {}", clinic);
        if (clinic.getId() == null) {
            return createClinic(clinic);
        }
        Clinic result = clinicRepository.save(clinic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clinic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clinics : get all the clinics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clinics in body
     */
    @GetMapping("/clinics")
    @Timed
    public List<Clinic> getAllClinics() {
        log.debug("REST request to get all Clinics");
        List<Clinic> clinics = clinicRepository.findAll();
        return clinics;
    }

    /**
     * GET  /clinics/:id : get the "id" clinic.
     *
     * @param id the id of the clinic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clinic, or with status 404 (Not Found)
     */
    @GetMapping("/clinics/{id}")
    @Timed
    public ResponseEntity<Clinic> getClinic(@PathVariable Long id) {
        log.debug("REST request to get Clinic : {}", id);
        Clinic clinic = clinicRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clinic));
    }

    /**
     * DELETE  /clinics/:id : delete the "id" clinic.
     *
     * @param id the id of the clinic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clinics/{id}")
    @Timed
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        log.debug("REST request to delete Clinic : {}", id);
        clinicRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
