package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.ClinicModerator;

import com.softgroup.algorithm.clinic.repository.ClinicModeratorRepository;
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
 * REST controller for managing ClinicModerator.
 */
@RestController
@RequestMapping("/api")
public class ClinicModeratorResource {

    private final Logger log = LoggerFactory.getLogger(ClinicModeratorResource.class);

    private static final String ENTITY_NAME = "clinicModerator";
        
    private final ClinicModeratorRepository clinicModeratorRepository;

    public ClinicModeratorResource(ClinicModeratorRepository clinicModeratorRepository) {
        this.clinicModeratorRepository = clinicModeratorRepository;
    }

    /**
     * POST  /clinic-moderators : Create a new clinicModerator.
     *
     * @param clinicModerator the clinicModerator to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clinicModerator, or with status 400 (Bad Request) if the clinicModerator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clinic-moderators")
    @Timed
    public ResponseEntity<ClinicModerator> createClinicModerator(@RequestBody ClinicModerator clinicModerator) throws URISyntaxException {
        log.debug("REST request to save ClinicModerator : {}", clinicModerator);
        if (clinicModerator.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clinicModerator cannot already have an ID")).body(null);
        }
        ClinicModerator result = clinicModeratorRepository.save(clinicModerator);
        return ResponseEntity.created(new URI("/api/clinic-moderators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clinic-moderators : Updates an existing clinicModerator.
     *
     * @param clinicModerator the clinicModerator to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clinicModerator,
     * or with status 400 (Bad Request) if the clinicModerator is not valid,
     * or with status 500 (Internal Server Error) if the clinicModerator couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clinic-moderators")
    @Timed
    public ResponseEntity<ClinicModerator> updateClinicModerator(@RequestBody ClinicModerator clinicModerator) throws URISyntaxException {
        log.debug("REST request to update ClinicModerator : {}", clinicModerator);
        if (clinicModerator.getId() == null) {
            return createClinicModerator(clinicModerator);
        }
        ClinicModerator result = clinicModeratorRepository.save(clinicModerator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clinicModerator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clinic-moderators : get all the clinicModerators.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of clinicModerators in body
     */
    @GetMapping("/clinic-moderators")
    @Timed
    public List<ClinicModerator> getAllClinicModerators(@RequestParam(required = false) String filter) {
        if ("clinic-is-null".equals(filter)) {
            log.debug("REST request to get all ClinicModerators where clinic is null");
            return StreamSupport
                .stream(clinicModeratorRepository.findAll().spliterator(), false)
                .filter(clinicModerator -> clinicModerator.getClinic() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ClinicModerators");
        List<ClinicModerator> clinicModerators = clinicModeratorRepository.findAll();
        return clinicModerators;
    }

    /**
     * GET  /clinic-moderators/:id : get the "id" clinicModerator.
     *
     * @param id the id of the clinicModerator to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clinicModerator, or with status 404 (Not Found)
     */
    @GetMapping("/clinic-moderators/{id}")
    @Timed
    public ResponseEntity<ClinicModerator> getClinicModerator(@PathVariable Long id) {
        log.debug("REST request to get ClinicModerator : {}", id);
        ClinicModerator clinicModerator = clinicModeratorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clinicModerator));
    }

    /**
     * DELETE  /clinic-moderators/:id : delete the "id" clinicModerator.
     *
     * @param id the id of the clinicModerator to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clinic-moderators/{id}")
    @Timed
    public ResponseEntity<Void> deleteClinicModerator(@PathVariable Long id) {
        log.debug("REST request to delete ClinicModerator : {}", id);
        clinicModeratorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
