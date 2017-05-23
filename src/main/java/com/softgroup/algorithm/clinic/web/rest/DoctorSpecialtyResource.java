package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.DoctorSpecialty;

import com.softgroup.algorithm.clinic.repository.DoctorSpecialtyRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing DoctorSpecialty.
 */
@RestController
@RequestMapping("/api")
public class DoctorSpecialtyResource {

    private final Logger log = LoggerFactory.getLogger(DoctorSpecialtyResource.class);

    private static final String ENTITY_NAME = "doctorSpecialty";
        
    private final DoctorSpecialtyRepository doctorSpecialtyRepository;

    public DoctorSpecialtyResource(DoctorSpecialtyRepository doctorSpecialtyRepository) {
        this.doctorSpecialtyRepository = doctorSpecialtyRepository;
    }

    /**
     * POST  /doctor-specialties : Create a new doctorSpecialty.
     *
     * @param doctorSpecialty the doctorSpecialty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doctorSpecialty, or with status 400 (Bad Request) if the doctorSpecialty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doctor-specialties")
    @Timed
    public ResponseEntity<DoctorSpecialty> createDoctorSpecialty(@RequestBody DoctorSpecialty doctorSpecialty) throws URISyntaxException {
        log.debug("REST request to save DoctorSpecialty : {}", doctorSpecialty);
        if (doctorSpecialty.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new doctorSpecialty cannot already have an ID")).body(null);
        }
        DoctorSpecialty result = doctorSpecialtyRepository.save(doctorSpecialty);
        return ResponseEntity.created(new URI("/api/doctor-specialties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doctor-specialties : Updates an existing doctorSpecialty.
     *
     * @param doctorSpecialty the doctorSpecialty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doctorSpecialty,
     * or with status 400 (Bad Request) if the doctorSpecialty is not valid,
     * or with status 500 (Internal Server Error) if the doctorSpecialty couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doctor-specialties")
    @Timed
    public ResponseEntity<DoctorSpecialty> updateDoctorSpecialty(@RequestBody DoctorSpecialty doctorSpecialty) throws URISyntaxException {
        log.debug("REST request to update DoctorSpecialty : {}", doctorSpecialty);
        if (doctorSpecialty.getId() == null) {
            return createDoctorSpecialty(doctorSpecialty);
        }
        DoctorSpecialty result = doctorSpecialtyRepository.save(doctorSpecialty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doctorSpecialty.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doctor-specialties : get all the doctorSpecialties.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of doctorSpecialties in body
     */
    @GetMapping("/doctor-specialties")
    @Timed
    public List<DoctorSpecialty> getAllDoctorSpecialties(@RequestParam(required = false) String filter) {
        if ("doctor-is-null".equals(filter)) {
            log.debug("REST request to get all DoctorSpecialtys where doctor is null");
            return StreamSupport
                .stream(doctorSpecialtyRepository.findAll().spliterator(), false)
                .filter(doctorSpecialty -> doctorSpecialty.getDoctor() == null)
                .collect(Collectors.toList());
        }
        if ("specialty-is-null".equals(filter)) {
            log.debug("REST request to get all DoctorSpecialtys where specialty is null");
            return StreamSupport
                .stream(doctorSpecialtyRepository.findAll().spliterator(), false)
                .filter(doctorSpecialty -> doctorSpecialty.getSpecialty() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all DoctorSpecialties");
        List<DoctorSpecialty> doctorSpecialties = doctorSpecialtyRepository.findAll();
        return doctorSpecialties;
    }

    /**
     * GET  /doctor-specialties/:id : get the "id" doctorSpecialty.
     *
     * @param id the id of the doctorSpecialty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doctorSpecialty, or with status 404 (Not Found)
     */
    @GetMapping("/doctor-specialties/{id}")
    @Timed
    public ResponseEntity<DoctorSpecialty> getDoctorSpecialty(@PathVariable Long id) {
        log.debug("REST request to get DoctorSpecialty : {}", id);
        DoctorSpecialty doctorSpecialty = doctorSpecialtyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(doctorSpecialty));
    }

    /**
     * DELETE  /doctor-specialties/:id : delete the "id" doctorSpecialty.
     *
     * @param id the id of the doctorSpecialty to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doctor-specialties/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoctorSpecialty(@PathVariable Long id) {
        log.debug("REST request to delete DoctorSpecialty : {}", id);
        doctorSpecialtyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
