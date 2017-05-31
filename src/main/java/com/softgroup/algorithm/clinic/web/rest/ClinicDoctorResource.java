package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.ClinicDoctor;

import com.softgroup.algorithm.clinic.repository.ClinicDoctorRepository;
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
 * REST controller for managing ClinicDoctor.
 */
@RestController
@RequestMapping("/api")
public class ClinicDoctorResource {

    private final Logger log = LoggerFactory.getLogger(ClinicDoctorResource.class);

    private static final String ENTITY_NAME = "clinicDoctor";
        
    private final ClinicDoctorRepository clinicDoctorRepository;

    public ClinicDoctorResource(ClinicDoctorRepository clinicDoctorRepository) {
        this.clinicDoctorRepository = clinicDoctorRepository;
    }

    /**
     * POST  /clinic-doctors : Create a new clinicDoctor.
     *
     * @param clinicDoctor the clinicDoctor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clinicDoctor, or with status 400 (Bad Request) if the clinicDoctor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clinic-doctors")
    @Timed
    public ResponseEntity<ClinicDoctor> createClinicDoctor(@RequestBody ClinicDoctor clinicDoctor) throws URISyntaxException {
        log.debug("REST request to save ClinicDoctor : {}", clinicDoctor);
        if (clinicDoctor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clinicDoctor cannot already have an ID")).body(null);
        }
        ClinicDoctor result = clinicDoctorRepository.save(clinicDoctor);
        return ResponseEntity.created(new URI("/api/clinic-doctors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clinic-doctors : Updates an existing clinicDoctor.
     *
     * @param clinicDoctor the clinicDoctor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clinicDoctor,
     * or with status 400 (Bad Request) if the clinicDoctor is not valid,
     * or with status 500 (Internal Server Error) if the clinicDoctor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clinic-doctors")
    @Timed
    public ResponseEntity<ClinicDoctor> updateClinicDoctor(@RequestBody ClinicDoctor clinicDoctor) throws URISyntaxException {
        log.debug("REST request to update ClinicDoctor : {}", clinicDoctor);
        if (clinicDoctor.getId() == null) {
            return createClinicDoctor(clinicDoctor);
        }
        ClinicDoctor result = clinicDoctorRepository.save(clinicDoctor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clinicDoctor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clinic-doctors : get all the clinicDoctors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clinicDoctors in body
     */
    @GetMapping("/clinic-doctors")
    @Timed
    public List<ClinicDoctor> getAllClinicDoctors() {
        log.debug("REST request to get all ClinicDoctors");
        List<ClinicDoctor> clinicDoctors = clinicDoctorRepository.findAll();
        return clinicDoctors;
    }

    /**
     * GET  /clinic-doctors/:id : get the "id" clinicDoctor.
     *
     * @param id the id of the clinicDoctor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clinicDoctor, or with status 404 (Not Found)
     */
    @GetMapping("/clinic-doctors/{id}")
    @Timed
    public ResponseEntity<ClinicDoctor> getClinicDoctor(@PathVariable Long id) {
        log.debug("REST request to get ClinicDoctor : {}", id);
        ClinicDoctor clinicDoctor = clinicDoctorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clinicDoctor));
    }

    /**
     * DELETE  /clinic-doctors/:id : delete the "id" clinicDoctor.
     *
     * @param id the id of the clinicDoctor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clinic-doctors/{id}")
    @Timed
    public ResponseEntity<Void> deleteClinicDoctor(@PathVariable Long id) {
        log.debug("REST request to delete ClinicDoctor : {}", id);
        clinicDoctorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
