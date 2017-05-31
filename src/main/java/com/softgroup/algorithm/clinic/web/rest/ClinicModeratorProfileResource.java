package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.ClinicModeratorProfile;

import com.softgroup.algorithm.clinic.repository.ClinicModeratorProfileRepository;
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
 * REST controller for managing ClinicModeratorProfile.
 */
@RestController
@RequestMapping("/api")
public class ClinicModeratorProfileResource {

    private final Logger log = LoggerFactory.getLogger(ClinicModeratorProfileResource.class);

    private static final String ENTITY_NAME = "clinicModeratorProfile";
        
    private final ClinicModeratorProfileRepository clinicModeratorProfileRepository;

    public ClinicModeratorProfileResource(ClinicModeratorProfileRepository clinicModeratorProfileRepository) {
        this.clinicModeratorProfileRepository = clinicModeratorProfileRepository;
    }

    /**
     * POST  /clinic-moderator-profiles : Create a new clinicModeratorProfile.
     *
     * @param clinicModeratorProfile the clinicModeratorProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clinicModeratorProfile, or with status 400 (Bad Request) if the clinicModeratorProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clinic-moderator-profiles")
    @Timed
    public ResponseEntity<ClinicModeratorProfile> createClinicModeratorProfile(@RequestBody ClinicModeratorProfile clinicModeratorProfile) throws URISyntaxException {
        log.debug("REST request to save ClinicModeratorProfile : {}", clinicModeratorProfile);
        if (clinicModeratorProfile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clinicModeratorProfile cannot already have an ID")).body(null);
        }
        ClinicModeratorProfile result = clinicModeratorProfileRepository.save(clinicModeratorProfile);
        return ResponseEntity.created(new URI("/api/clinic-moderator-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clinic-moderator-profiles : Updates an existing clinicModeratorProfile.
     *
     * @param clinicModeratorProfile the clinicModeratorProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clinicModeratorProfile,
     * or with status 400 (Bad Request) if the clinicModeratorProfile is not valid,
     * or with status 500 (Internal Server Error) if the clinicModeratorProfile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clinic-moderator-profiles")
    @Timed
    public ResponseEntity<ClinicModeratorProfile> updateClinicModeratorProfile(@RequestBody ClinicModeratorProfile clinicModeratorProfile) throws URISyntaxException {
        log.debug("REST request to update ClinicModeratorProfile : {}", clinicModeratorProfile);
        if (clinicModeratorProfile.getId() == null) {
            return createClinicModeratorProfile(clinicModeratorProfile);
        }
        ClinicModeratorProfile result = clinicModeratorProfileRepository.save(clinicModeratorProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clinicModeratorProfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clinic-moderator-profiles : get all the clinicModeratorProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clinicModeratorProfiles in body
     */
    @GetMapping("/clinic-moderator-profiles")
    @Timed
    public List<ClinicModeratorProfile> getAllClinicModeratorProfiles() {
        log.debug("REST request to get all ClinicModeratorProfiles");
        List<ClinicModeratorProfile> clinicModeratorProfiles = clinicModeratorProfileRepository.findAll();
        return clinicModeratorProfiles;
    }

    /**
     * GET  /clinic-moderator-profiles/:id : get the "id" clinicModeratorProfile.
     *
     * @param id the id of the clinicModeratorProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clinicModeratorProfile, or with status 404 (Not Found)
     */
    @GetMapping("/clinic-moderator-profiles/{id}")
    @Timed
    public ResponseEntity<ClinicModeratorProfile> getClinicModeratorProfile(@PathVariable Long id) {
        log.debug("REST request to get ClinicModeratorProfile : {}", id);
        ClinicModeratorProfile clinicModeratorProfile = clinicModeratorProfileRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clinicModeratorProfile));
    }

    /**
     * DELETE  /clinic-moderator-profiles/:id : delete the "id" clinicModeratorProfile.
     *
     * @param id the id of the clinicModeratorProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clinic-moderator-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteClinicModeratorProfile(@PathVariable Long id) {
        log.debug("REST request to delete ClinicModeratorProfile : {}", id);
        clinicModeratorProfileRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
