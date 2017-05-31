package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.DoctorDayRoutine;

import com.softgroup.algorithm.clinic.repository.DoctorDayRoutineRepository;
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
 * REST controller for managing DoctorDayRoutine.
 */
@RestController
@RequestMapping("/api")
public class DoctorDayRoutineResource {

    private final Logger log = LoggerFactory.getLogger(DoctorDayRoutineResource.class);

    private static final String ENTITY_NAME = "doctorDayRoutine";
        
    private final DoctorDayRoutineRepository doctorDayRoutineRepository;

    public DoctorDayRoutineResource(DoctorDayRoutineRepository doctorDayRoutineRepository) {
        this.doctorDayRoutineRepository = doctorDayRoutineRepository;
    }

    /**
     * POST  /doctor-day-routines : Create a new doctorDayRoutine.
     *
     * @param doctorDayRoutine the doctorDayRoutine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doctorDayRoutine, or with status 400 (Bad Request) if the doctorDayRoutine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doctor-day-routines")
    @Timed
    public ResponseEntity<DoctorDayRoutine> createDoctorDayRoutine(@RequestBody DoctorDayRoutine doctorDayRoutine) throws URISyntaxException {
        log.debug("REST request to save DoctorDayRoutine : {}", doctorDayRoutine);
        if (doctorDayRoutine.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new doctorDayRoutine cannot already have an ID")).body(null);
        }
        DoctorDayRoutine result = doctorDayRoutineRepository.save(doctorDayRoutine);
        return ResponseEntity.created(new URI("/api/doctor-day-routines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doctor-day-routines : Updates an existing doctorDayRoutine.
     *
     * @param doctorDayRoutine the doctorDayRoutine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doctorDayRoutine,
     * or with status 400 (Bad Request) if the doctorDayRoutine is not valid,
     * or with status 500 (Internal Server Error) if the doctorDayRoutine couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doctor-day-routines")
    @Timed
    public ResponseEntity<DoctorDayRoutine> updateDoctorDayRoutine(@RequestBody DoctorDayRoutine doctorDayRoutine) throws URISyntaxException {
        log.debug("REST request to update DoctorDayRoutine : {}", doctorDayRoutine);
        if (doctorDayRoutine.getId() == null) {
            return createDoctorDayRoutine(doctorDayRoutine);
        }
        DoctorDayRoutine result = doctorDayRoutineRepository.save(doctorDayRoutine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doctorDayRoutine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doctor-day-routines : get all the doctorDayRoutines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of doctorDayRoutines in body
     */
    @GetMapping("/doctor-day-routines")
    @Timed
    public List<DoctorDayRoutine> getAllDoctorDayRoutines() {
        log.debug("REST request to get all DoctorDayRoutines");
        List<DoctorDayRoutine> doctorDayRoutines = doctorDayRoutineRepository.findAll();
        return doctorDayRoutines;
    }

    /**
     * GET  /doctor-day-routines/:id : get the "id" doctorDayRoutine.
     *
     * @param id the id of the doctorDayRoutine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doctorDayRoutine, or with status 404 (Not Found)
     */
    @GetMapping("/doctor-day-routines/{id}")
    @Timed
    public ResponseEntity<DoctorDayRoutine> getDoctorDayRoutine(@PathVariable Long id) {
        log.debug("REST request to get DoctorDayRoutine : {}", id);
        DoctorDayRoutine doctorDayRoutine = doctorDayRoutineRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(doctorDayRoutine));
    }

    /**
     * DELETE  /doctor-day-routines/:id : delete the "id" doctorDayRoutine.
     *
     * @param id the id of the doctorDayRoutine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doctor-day-routines/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoctorDayRoutine(@PathVariable Long id) {
        log.debug("REST request to delete DoctorDayRoutine : {}", id);
        doctorDayRoutineRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
