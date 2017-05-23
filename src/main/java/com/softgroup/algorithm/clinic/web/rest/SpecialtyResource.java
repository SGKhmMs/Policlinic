package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.Specialty;

import com.softgroup.algorithm.clinic.repository.SpecialtyRepository;
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
 * REST controller for managing Specialty.
 */
@RestController
@RequestMapping("/api")
public class SpecialtyResource {

    private final Logger log = LoggerFactory.getLogger(SpecialtyResource.class);

    private static final String ENTITY_NAME = "specialty";
        
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyResource(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    /**
     * POST  /specialties : Create a new specialty.
     *
     * @param specialty the specialty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new specialty, or with status 400 (Bad Request) if the specialty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/specialties")
    @Timed
    public ResponseEntity<Specialty> createSpecialty(@RequestBody Specialty specialty) throws URISyntaxException {
        log.debug("REST request to save Specialty : {}", specialty);
        if (specialty.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new specialty cannot already have an ID")).body(null);
        }
        Specialty result = specialtyRepository.save(specialty);
        return ResponseEntity.created(new URI("/api/specialties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /specialties : Updates an existing specialty.
     *
     * @param specialty the specialty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated specialty,
     * or with status 400 (Bad Request) if the specialty is not valid,
     * or with status 500 (Internal Server Error) if the specialty couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/specialties")
    @Timed
    public ResponseEntity<Specialty> updateSpecialty(@RequestBody Specialty specialty) throws URISyntaxException {
        log.debug("REST request to update Specialty : {}", specialty);
        if (specialty.getId() == null) {
            return createSpecialty(specialty);
        }
        Specialty result = specialtyRepository.save(specialty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, specialty.getId().toString()))
            .body(result);
    }

    /**
     * GET  /specialties : get all the specialties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of specialties in body
     */
    @GetMapping("/specialties")
    @Timed
    public List<Specialty> getAllSpecialties() {
        log.debug("REST request to get all Specialties");
        List<Specialty> specialties = specialtyRepository.findAll();
        return specialties;
    }

    /**
     * GET  /specialties/:id : get the "id" specialty.
     *
     * @param id the id of the specialty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the specialty, or with status 404 (Not Found)
     */
    @GetMapping("/specialties/{id}")
    @Timed
    public ResponseEntity<Specialty> getSpecialty(@PathVariable Long id) {
        log.debug("REST request to get Specialty : {}", id);
        Specialty specialty = specialtyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(specialty));
    }

    /**
     * DELETE  /specialties/:id : delete the "id" specialty.
     *
     * @param id the id of the specialty to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/specialties/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        log.debug("REST request to delete Specialty : {}", id);
        specialtyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
