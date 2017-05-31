package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.CardEntry;

import com.softgroup.algorithm.clinic.repository.CardEntryRepository;
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
 * REST controller for managing CardEntry.
 */
@RestController
@RequestMapping("/api")
public class CardEntryResource {

    private final Logger log = LoggerFactory.getLogger(CardEntryResource.class);

    private static final String ENTITY_NAME = "cardEntry";
        
    private final CardEntryRepository cardEntryRepository;

    public CardEntryResource(CardEntryRepository cardEntryRepository) {
        this.cardEntryRepository = cardEntryRepository;
    }

    /**
     * POST  /card-entries : Create a new cardEntry.
     *
     * @param cardEntry the cardEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cardEntry, or with status 400 (Bad Request) if the cardEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/card-entries")
    @Timed
    public ResponseEntity<CardEntry> createCardEntry(@RequestBody CardEntry cardEntry) throws URISyntaxException {
        log.debug("REST request to save CardEntry : {}", cardEntry);
        if (cardEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cardEntry cannot already have an ID")).body(null);
        }
        CardEntry result = cardEntryRepository.save(cardEntry);
        return ResponseEntity.created(new URI("/api/card-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /card-entries : Updates an existing cardEntry.
     *
     * @param cardEntry the cardEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cardEntry,
     * or with status 400 (Bad Request) if the cardEntry is not valid,
     * or with status 500 (Internal Server Error) if the cardEntry couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/card-entries")
    @Timed
    public ResponseEntity<CardEntry> updateCardEntry(@RequestBody CardEntry cardEntry) throws URISyntaxException {
        log.debug("REST request to update CardEntry : {}", cardEntry);
        if (cardEntry.getId() == null) {
            return createCardEntry(cardEntry);
        }
        CardEntry result = cardEntryRepository.save(cardEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cardEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /card-entries : get all the cardEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cardEntries in body
     */
    @GetMapping("/card-entries")
    @Timed
    public List<CardEntry> getAllCardEntries() {
        log.debug("REST request to get all CardEntries");
        List<CardEntry> cardEntries = cardEntryRepository.findAll();
        return cardEntries;
    }

    /**
     * GET  /card-entries/:id : get the "id" cardEntry.
     *
     * @param id the id of the cardEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cardEntry, or with status 404 (Not Found)
     */
    @GetMapping("/card-entries/{id}")
    @Timed
    public ResponseEntity<CardEntry> getCardEntry(@PathVariable Long id) {
        log.debug("REST request to get CardEntry : {}", id);
        CardEntry cardEntry = cardEntryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cardEntry));
    }

    /**
     * DELETE  /card-entries/:id : delete the "id" cardEntry.
     *
     * @param id the id of the cardEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/card-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCardEntry(@PathVariable Long id) {
        log.debug("REST request to delete CardEntry : {}", id);
        cardEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
