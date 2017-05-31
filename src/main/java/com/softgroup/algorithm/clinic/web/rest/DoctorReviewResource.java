package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.DoctorReview;

import com.softgroup.algorithm.clinic.repository.DoctorReviewRepository;
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
 * REST controller for managing DoctorReview.
 */
@RestController
@RequestMapping("/api")
public class DoctorReviewResource {

    private final Logger log = LoggerFactory.getLogger(DoctorReviewResource.class);

    private static final String ENTITY_NAME = "doctorReview";
        
    private final DoctorReviewRepository doctorReviewRepository;

    public DoctorReviewResource(DoctorReviewRepository doctorReviewRepository) {
        this.doctorReviewRepository = doctorReviewRepository;
    }

    /**
     * POST  /doctor-reviews : Create a new doctorReview.
     *
     * @param doctorReview the doctorReview to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doctorReview, or with status 400 (Bad Request) if the doctorReview has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doctor-reviews")
    @Timed
    public ResponseEntity<DoctorReview> createDoctorReview(@RequestBody DoctorReview doctorReview) throws URISyntaxException {
        log.debug("REST request to save DoctorReview : {}", doctorReview);
        if (doctorReview.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new doctorReview cannot already have an ID")).body(null);
        }
        DoctorReview result = doctorReviewRepository.save(doctorReview);
        return ResponseEntity.created(new URI("/api/doctor-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doctor-reviews : Updates an existing doctorReview.
     *
     * @param doctorReview the doctorReview to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doctorReview,
     * or with status 400 (Bad Request) if the doctorReview is not valid,
     * or with status 500 (Internal Server Error) if the doctorReview couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doctor-reviews")
    @Timed
    public ResponseEntity<DoctorReview> updateDoctorReview(@RequestBody DoctorReview doctorReview) throws URISyntaxException {
        log.debug("REST request to update DoctorReview : {}", doctorReview);
        if (doctorReview.getId() == null) {
            return createDoctorReview(doctorReview);
        }
        DoctorReview result = doctorReviewRepository.save(doctorReview);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doctorReview.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doctor-reviews : get all the doctorReviews.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of doctorReviews in body
     */
    @GetMapping("/doctor-reviews")
    @Timed
    public List<DoctorReview> getAllDoctorReviews() {
        log.debug("REST request to get all DoctorReviews");
        List<DoctorReview> doctorReviews = doctorReviewRepository.findAll();
        return doctorReviews;
    }

    /**
     * GET  /doctor-reviews/:id : get the "id" doctorReview.
     *
     * @param id the id of the doctorReview to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doctorReview, or with status 404 (Not Found)
     */
    @GetMapping("/doctor-reviews/{id}")
    @Timed
    public ResponseEntity<DoctorReview> getDoctorReview(@PathVariable Long id) {
        log.debug("REST request to get DoctorReview : {}", id);
        DoctorReview doctorReview = doctorReviewRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(doctorReview));
    }

    /**
     * DELETE  /doctor-reviews/:id : delete the "id" doctorReview.
     *
     * @param id the id of the doctorReview to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doctor-reviews/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoctorReview(@PathVariable Long id) {
        log.debug("REST request to delete DoctorReview : {}", id);
        doctorReviewRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
