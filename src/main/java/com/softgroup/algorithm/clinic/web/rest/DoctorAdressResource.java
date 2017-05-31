package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.DoctorAdress;

import com.softgroup.algorithm.clinic.repository.DoctorAdressRepository;
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
 * REST controller for managing DoctorAdress.
 */
@RestController
@RequestMapping("/api")
public class DoctorAdressResource {

    private final Logger log = LoggerFactory.getLogger(DoctorAdressResource.class);

    private static final String ENTITY_NAME = "doctorAdress";
        
    private final DoctorAdressRepository doctorAdressRepository;

    public DoctorAdressResource(DoctorAdressRepository doctorAdressRepository) {
        this.doctorAdressRepository = doctorAdressRepository;
    }

    /**
     * POST  /doctor-adresses : Create a new doctorAdress.
     *
     * @param doctorAdress the doctorAdress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doctorAdress, or with status 400 (Bad Request) if the doctorAdress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doctor-adresses")
    @Timed
    public ResponseEntity<DoctorAdress> createDoctorAdress(@RequestBody DoctorAdress doctorAdress) throws URISyntaxException {
        log.debug("REST request to save DoctorAdress : {}", doctorAdress);
        if (doctorAdress.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new doctorAdress cannot already have an ID")).body(null);
        }
        DoctorAdress result = doctorAdressRepository.save(doctorAdress);
        return ResponseEntity.created(new URI("/api/doctor-adresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doctor-adresses : Updates an existing doctorAdress.
     *
     * @param doctorAdress the doctorAdress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doctorAdress,
     * or with status 400 (Bad Request) if the doctorAdress is not valid,
     * or with status 500 (Internal Server Error) if the doctorAdress couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doctor-adresses")
    @Timed
    public ResponseEntity<DoctorAdress> updateDoctorAdress(@RequestBody DoctorAdress doctorAdress) throws URISyntaxException {
        log.debug("REST request to update DoctorAdress : {}", doctorAdress);
        if (doctorAdress.getId() == null) {
            return createDoctorAdress(doctorAdress);
        }
        DoctorAdress result = doctorAdressRepository.save(doctorAdress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doctorAdress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doctor-adresses : get all the doctorAdresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of doctorAdresses in body
     */
    @GetMapping("/doctor-adresses")
    @Timed
    public List<DoctorAdress> getAllDoctorAdresses() {
        log.debug("REST request to get all DoctorAdresses");
        List<DoctorAdress> doctorAdresses = doctorAdressRepository.findAll();
        return doctorAdresses;
    }

    /**
     * GET  /doctor-adresses/:id : get the "id" doctorAdress.
     *
     * @param id the id of the doctorAdress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doctorAdress, or with status 404 (Not Found)
     */
    @GetMapping("/doctor-adresses/{id}")
    @Timed
    public ResponseEntity<DoctorAdress> getDoctorAdress(@PathVariable Long id) {
        log.debug("REST request to get DoctorAdress : {}", id);
        DoctorAdress doctorAdress = doctorAdressRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(doctorAdress));
    }

    /**
     * DELETE  /doctor-adresses/:id : delete the "id" doctorAdress.
     *
     * @param id the id of the doctorAdress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doctor-adresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoctorAdress(@PathVariable Long id) {
        log.debug("REST request to delete DoctorAdress : {}", id);
        doctorAdressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
