package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.Attechment;

import com.softgroup.algorithm.clinic.repository.AttechmentRepository;
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
 * REST controller for managing Attechment.
 */
@RestController
@RequestMapping("/api")
public class AttechmentResource {

    private final Logger log = LoggerFactory.getLogger(AttechmentResource.class);

    private static final String ENTITY_NAME = "attechment";
        
    private final AttechmentRepository attechmentRepository;

    public AttechmentResource(AttechmentRepository attechmentRepository) {
        this.attechmentRepository = attechmentRepository;
    }

    /**
     * POST  /attechments : Create a new attechment.
     *
     * @param attechment the attechment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attechment, or with status 400 (Bad Request) if the attechment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attechments")
    @Timed
    public ResponseEntity<Attechment> createAttechment(@RequestBody Attechment attechment) throws URISyntaxException {
        log.debug("REST request to save Attechment : {}", attechment);
        if (attechment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new attechment cannot already have an ID")).body(null);
        }
        Attechment result = attechmentRepository.save(attechment);
        return ResponseEntity.created(new URI("/api/attechments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attechments : Updates an existing attechment.
     *
     * @param attechment the attechment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attechment,
     * or with status 400 (Bad Request) if the attechment is not valid,
     * or with status 500 (Internal Server Error) if the attechment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attechments")
    @Timed
    public ResponseEntity<Attechment> updateAttechment(@RequestBody Attechment attechment) throws URISyntaxException {
        log.debug("REST request to update Attechment : {}", attechment);
        if (attechment.getId() == null) {
            return createAttechment(attechment);
        }
        Attechment result = attechmentRepository.save(attechment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attechment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attechments : get all the attechments.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of attechments in body
     */
    @GetMapping("/attechments")
    @Timed
    public List<Attechment> getAllAttechments(@RequestParam(required = false) String filter) {
        if ("massage-is-null".equals(filter)) {
            log.debug("REST request to get all Attechments where massage is null");
            return StreamSupport
                .stream(attechmentRepository.findAll().spliterator(), false)
                .filter(attechment -> attechment.getMassage() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Attechments");
        List<Attechment> attechments = attechmentRepository.findAll();
        return attechments;
    }

    /**
     * GET  /attechments/:id : get the "id" attechment.
     *
     * @param id the id of the attechment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attechment, or with status 404 (Not Found)
     */
    @GetMapping("/attechments/{id}")
    @Timed
    public ResponseEntity<Attechment> getAttechment(@PathVariable Long id) {
        log.debug("REST request to get Attechment : {}", id);
        Attechment attechment = attechmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(attechment));
    }

    /**
     * DELETE  /attechments/:id : delete the "id" attechment.
     *
     * @param id the id of the attechment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attechments/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttechment(@PathVariable Long id) {
        log.debug("REST request to delete Attechment : {}", id);
        attechmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
