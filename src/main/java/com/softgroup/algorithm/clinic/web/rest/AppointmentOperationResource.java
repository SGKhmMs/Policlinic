package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.AppointmentOperation;

import com.softgroup.algorithm.clinic.repository.AppointmentOperationRepository;
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
 * REST controller for managing AppointmentOperation.
 */
@RestController
@RequestMapping("/api")
public class AppointmentOperationResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentOperationResource.class);

    private static final String ENTITY_NAME = "appointmentOperation";
        
    private final AppointmentOperationRepository appointmentOperationRepository;

    public AppointmentOperationResource(AppointmentOperationRepository appointmentOperationRepository) {
        this.appointmentOperationRepository = appointmentOperationRepository;
    }

    /**
     * POST  /appointment-operations : Create a new appointmentOperation.
     *
     * @param appointmentOperation the appointmentOperation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appointmentOperation, or with status 400 (Bad Request) if the appointmentOperation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/appointment-operations")
    @Timed
    public ResponseEntity<AppointmentOperation> createAppointmentOperation(@RequestBody AppointmentOperation appointmentOperation) throws URISyntaxException {
        log.debug("REST request to save AppointmentOperation : {}", appointmentOperation);
        if (appointmentOperation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new appointmentOperation cannot already have an ID")).body(null);
        }
        AppointmentOperation result = appointmentOperationRepository.save(appointmentOperation);
        return ResponseEntity.created(new URI("/api/appointment-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appointment-operations : Updates an existing appointmentOperation.
     *
     * @param appointmentOperation the appointmentOperation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appointmentOperation,
     * or with status 400 (Bad Request) if the appointmentOperation is not valid,
     * or with status 500 (Internal Server Error) if the appointmentOperation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/appointment-operations")
    @Timed
    public ResponseEntity<AppointmentOperation> updateAppointmentOperation(@RequestBody AppointmentOperation appointmentOperation) throws URISyntaxException {
        log.debug("REST request to update AppointmentOperation : {}", appointmentOperation);
        if (appointmentOperation.getId() == null) {
            return createAppointmentOperation(appointmentOperation);
        }
        AppointmentOperation result = appointmentOperationRepository.save(appointmentOperation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appointmentOperation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appointment-operations : get all the appointmentOperations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appointmentOperations in body
     */
    @GetMapping("/appointment-operations")
    @Timed
    public List<AppointmentOperation> getAllAppointmentOperations() {
        log.debug("REST request to get all AppointmentOperations");
        List<AppointmentOperation> appointmentOperations = appointmentOperationRepository.findAll();
        return appointmentOperations;
    }

    /**
     * GET  /appointment-operations/:id : get the "id" appointmentOperation.
     *
     * @param id the id of the appointmentOperation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appointmentOperation, or with status 404 (Not Found)
     */
    @GetMapping("/appointment-operations/{id}")
    @Timed
    public ResponseEntity<AppointmentOperation> getAppointmentOperation(@PathVariable Long id) {
        log.debug("REST request to get AppointmentOperation : {}", id);
        AppointmentOperation appointmentOperation = appointmentOperationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appointmentOperation));
    }

    /**
     * DELETE  /appointment-operations/:id : delete the "id" appointmentOperation.
     *
     * @param id the id of the appointmentOperation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/appointment-operations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppointmentOperation(@PathVariable Long id) {
        log.debug("REST request to delete AppointmentOperation : {}", id);
        appointmentOperationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
