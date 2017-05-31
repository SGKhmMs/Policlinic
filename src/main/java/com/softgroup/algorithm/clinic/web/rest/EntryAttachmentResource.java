package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.EntryAttachment;

import com.softgroup.algorithm.clinic.repository.EntryAttachmentRepository;
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
 * REST controller for managing EntryAttachment.
 */
@RestController
@RequestMapping("/api")
public class EntryAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(EntryAttachmentResource.class);

    private static final String ENTITY_NAME = "entryAttachment";
        
    private final EntryAttachmentRepository entryAttachmentRepository;

    public EntryAttachmentResource(EntryAttachmentRepository entryAttachmentRepository) {
        this.entryAttachmentRepository = entryAttachmentRepository;
    }

    /**
     * POST  /entry-attachments : Create a new entryAttachment.
     *
     * @param entryAttachment the entryAttachment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entryAttachment, or with status 400 (Bad Request) if the entryAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entry-attachments")
    @Timed
    public ResponseEntity<EntryAttachment> createEntryAttachment(@RequestBody EntryAttachment entryAttachment) throws URISyntaxException {
        log.debug("REST request to save EntryAttachment : {}", entryAttachment);
        if (entryAttachment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entryAttachment cannot already have an ID")).body(null);
        }
        EntryAttachment result = entryAttachmentRepository.save(entryAttachment);
        return ResponseEntity.created(new URI("/api/entry-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entry-attachments : Updates an existing entryAttachment.
     *
     * @param entryAttachment the entryAttachment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entryAttachment,
     * or with status 400 (Bad Request) if the entryAttachment is not valid,
     * or with status 500 (Internal Server Error) if the entryAttachment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entry-attachments")
    @Timed
    public ResponseEntity<EntryAttachment> updateEntryAttachment(@RequestBody EntryAttachment entryAttachment) throws URISyntaxException {
        log.debug("REST request to update EntryAttachment : {}", entryAttachment);
        if (entryAttachment.getId() == null) {
            return createEntryAttachment(entryAttachment);
        }
        EntryAttachment result = entryAttachmentRepository.save(entryAttachment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entryAttachment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entry-attachments : get all the entryAttachments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entryAttachments in body
     */
    @GetMapping("/entry-attachments")
    @Timed
    public List<EntryAttachment> getAllEntryAttachments() {
        log.debug("REST request to get all EntryAttachments");
        List<EntryAttachment> entryAttachments = entryAttachmentRepository.findAll();
        return entryAttachments;
    }

    /**
     * GET  /entry-attachments/:id : get the "id" entryAttachment.
     *
     * @param id the id of the entryAttachment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entryAttachment, or with status 404 (Not Found)
     */
    @GetMapping("/entry-attachments/{id}")
    @Timed
    public ResponseEntity<EntryAttachment> getEntryAttachment(@PathVariable Long id) {
        log.debug("REST request to get EntryAttachment : {}", id);
        EntryAttachment entryAttachment = entryAttachmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entryAttachment));
    }

    /**
     * DELETE  /entry-attachments/:id : delete the "id" entryAttachment.
     *
     * @param id the id of the entryAttachment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entry-attachments/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntryAttachment(@PathVariable Long id) {
        log.debug("REST request to delete EntryAttachment : {}", id);
        entryAttachmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
