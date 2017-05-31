package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.RoutineCase;

import com.softgroup.algorithm.clinic.repository.RoutineCaseRepository;
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
 * REST controller for managing RoutineCase.
 */
@RestController
@RequestMapping("/api")
public class RoutineCaseResource {

    private final Logger log = LoggerFactory.getLogger(RoutineCaseResource.class);

    private static final String ENTITY_NAME = "routineCase";
        
    private final RoutineCaseRepository routineCaseRepository;

    public RoutineCaseResource(RoutineCaseRepository routineCaseRepository) {
        this.routineCaseRepository = routineCaseRepository;
    }

    /**
     * POST  /routine-cases : Create a new routineCase.
     *
     * @param routineCase the routineCase to create
     * @return the ResponseEntity with status 201 (Created) and with body the new routineCase, or with status 400 (Bad Request) if the routineCase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/routine-cases")
    @Timed
    public ResponseEntity<RoutineCase> createRoutineCase(@RequestBody RoutineCase routineCase) throws URISyntaxException {
        log.debug("REST request to save RoutineCase : {}", routineCase);
        if (routineCase.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new routineCase cannot already have an ID")).body(null);
        }
        RoutineCase result = routineCaseRepository.save(routineCase);
        return ResponseEntity.created(new URI("/api/routine-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /routine-cases : Updates an existing routineCase.
     *
     * @param routineCase the routineCase to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated routineCase,
     * or with status 400 (Bad Request) if the routineCase is not valid,
     * or with status 500 (Internal Server Error) if the routineCase couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/routine-cases")
    @Timed
    public ResponseEntity<RoutineCase> updateRoutineCase(@RequestBody RoutineCase routineCase) throws URISyntaxException {
        log.debug("REST request to update RoutineCase : {}", routineCase);
        if (routineCase.getId() == null) {
            return createRoutineCase(routineCase);
        }
        RoutineCase result = routineCaseRepository.save(routineCase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, routineCase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /routine-cases : get all the routineCases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of routineCases in body
     */
    @GetMapping("/routine-cases")
    @Timed
    public List<RoutineCase> getAllRoutineCases() {
        log.debug("REST request to get all RoutineCases");
        List<RoutineCase> routineCases = routineCaseRepository.findAll();
        return routineCases;
    }

    /**
     * GET  /routine-cases/:id : get the "id" routineCase.
     *
     * @param id the id of the routineCase to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the routineCase, or with status 404 (Not Found)
     */
    @GetMapping("/routine-cases/{id}")
    @Timed
    public ResponseEntity<RoutineCase> getRoutineCase(@PathVariable Long id) {
        log.debug("REST request to get RoutineCase : {}", id);
        RoutineCase routineCase = routineCaseRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(routineCase));
    }

    /**
     * DELETE  /routine-cases/:id : delete the "id" routineCase.
     *
     * @param id the id of the routineCase to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/routine-cases/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoutineCase(@PathVariable Long id) {
        log.debug("REST request to delete RoutineCase : {}", id);
        routineCaseRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
