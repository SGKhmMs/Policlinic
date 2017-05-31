package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.DoctorProfile;

import com.softgroup.algorithm.clinic.repository.DoctorProfileRepository;
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
 * REST controller for managing DoctorProfile.
 */
@RestController
@RequestMapping("/api")
public class DoctorProfileResource {

    private final Logger log = LoggerFactory.getLogger(DoctorProfileResource.class);

    private static final String ENTITY_NAME = "doctorProfile";
        
    private final DoctorProfileRepository doctorProfileRepository;

    public DoctorProfileResource(DoctorProfileRepository doctorProfileRepository) {
        this.doctorProfileRepository = doctorProfileRepository;
    }

    /**
     * POST  /doctor-profiles : Create a new doctorProfile.
     *
     * @param doctorProfile the doctorProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doctorProfile, or with status 400 (Bad Request) if the doctorProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doctor-profiles")
    @Timed
    public ResponseEntity<DoctorProfile> createDoctorProfile(@RequestBody DoctorProfile doctorProfile) throws URISyntaxException {
        log.debug("REST request to save DoctorProfile : {}", doctorProfile);
        if (doctorProfile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new doctorProfile cannot already have an ID")).body(null);
        }
        DoctorProfile result = doctorProfileRepository.save(doctorProfile);
        return ResponseEntity.created(new URI("/api/doctor-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doctor-profiles : Updates an existing doctorProfile.
     *
     * @param doctorProfile the doctorProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doctorProfile,
     * or with status 400 (Bad Request) if the doctorProfile is not valid,
     * or with status 500 (Internal Server Error) if the doctorProfile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doctor-profiles")
    @Timed
    public ResponseEntity<DoctorProfile> updateDoctorProfile(@RequestBody DoctorProfile doctorProfile) throws URISyntaxException {
        log.debug("REST request to update DoctorProfile : {}", doctorProfile);
        if (doctorProfile.getId() == null) {
            return createDoctorProfile(doctorProfile);
        }
        DoctorProfile result = doctorProfileRepository.save(doctorProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doctorProfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doctor-profiles : get all the doctorProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of doctorProfiles in body
     */
    @GetMapping("/doctor-profiles")
    @Timed
    public List<DoctorProfile> getAllDoctorProfiles() {
        log.debug("REST request to get all DoctorProfiles");
        List<DoctorProfile> doctorProfiles = doctorProfileRepository.findAll();
        return doctorProfiles;
    }

    /**
     * GET  /doctor-profiles/:id : get the "id" doctorProfile.
     *
     * @param id the id of the doctorProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doctorProfile, or with status 404 (Not Found)
     */
    @GetMapping("/doctor-profiles/{id}")
    @Timed
    public ResponseEntity<DoctorProfile> getDoctorProfile(@PathVariable Long id) {
        log.debug("REST request to get DoctorProfile : {}", id);
        DoctorProfile doctorProfile = doctorProfileRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(doctorProfile));
    }

    /**
     * DELETE  /doctor-profiles/:id : delete the "id" doctorProfile.
     *
     * @param id the id of the doctorProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doctor-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoctorProfile(@PathVariable Long id) {
        log.debug("REST request to delete DoctorProfile : {}", id);
        doctorProfileRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
