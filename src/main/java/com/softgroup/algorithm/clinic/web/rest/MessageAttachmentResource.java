package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.MessageAttachment;

import com.softgroup.algorithm.clinic.repository.MessageAttachmentRepository;
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
 * REST controller for managing MessageAttachment.
 */
@RestController
@RequestMapping("/api")
public class MessageAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(MessageAttachmentResource.class);

    private static final String ENTITY_NAME = "messageAttachment";
        
    private final MessageAttachmentRepository messageAttachmentRepository;

    public MessageAttachmentResource(MessageAttachmentRepository messageAttachmentRepository) {
        this.messageAttachmentRepository = messageAttachmentRepository;
    }

    /**
     * POST  /message-attachments : Create a new messageAttachment.
     *
     * @param messageAttachment the messageAttachment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messageAttachment, or with status 400 (Bad Request) if the messageAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/message-attachments")
    @Timed
    public ResponseEntity<MessageAttachment> createMessageAttachment(@RequestBody MessageAttachment messageAttachment) throws URISyntaxException {
        log.debug("REST request to save MessageAttachment : {}", messageAttachment);
        if (messageAttachment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new messageAttachment cannot already have an ID")).body(null);
        }
        MessageAttachment result = messageAttachmentRepository.save(messageAttachment);
        return ResponseEntity.created(new URI("/api/message-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /message-attachments : Updates an existing messageAttachment.
     *
     * @param messageAttachment the messageAttachment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageAttachment,
     * or with status 400 (Bad Request) if the messageAttachment is not valid,
     * or with status 500 (Internal Server Error) if the messageAttachment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/message-attachments")
    @Timed
    public ResponseEntity<MessageAttachment> updateMessageAttachment(@RequestBody MessageAttachment messageAttachment) throws URISyntaxException {
        log.debug("REST request to update MessageAttachment : {}", messageAttachment);
        if (messageAttachment.getId() == null) {
            return createMessageAttachment(messageAttachment);
        }
        MessageAttachment result = messageAttachmentRepository.save(messageAttachment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageAttachment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /message-attachments : get all the messageAttachments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of messageAttachments in body
     */
    @GetMapping("/message-attachments")
    @Timed
    public List<MessageAttachment> getAllMessageAttachments() {
        log.debug("REST request to get all MessageAttachments");
        List<MessageAttachment> messageAttachments = messageAttachmentRepository.findAll();
        return messageAttachments;
    }

    /**
     * GET  /message-attachments/:id : get the "id" messageAttachment.
     *
     * @param id the id of the messageAttachment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageAttachment, or with status 404 (Not Found)
     */
    @GetMapping("/message-attachments/{id}")
    @Timed
    public ResponseEntity<MessageAttachment> getMessageAttachment(@PathVariable Long id) {
        log.debug("REST request to get MessageAttachment : {}", id);
        MessageAttachment messageAttachment = messageAttachmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageAttachment));
    }

    /**
     * DELETE  /message-attachments/:id : delete the "id" messageAttachment.
     *
     * @param id the id of the messageAttachment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/message-attachments/{id}")
    @Timed
    public ResponseEntity<Void> deleteMessageAttachment(@PathVariable Long id) {
        log.debug("REST request to delete MessageAttachment : {}", id);
        messageAttachmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
