package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.ClientProfile;

import com.softgroup.algorithm.clinic.repository.ClientProfileRepository;
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
 * REST controller for managing ClientProfile.
 */
@RestController
@RequestMapping("/api")
public class ClientProfileResource {

    private final Logger log = LoggerFactory.getLogger(ClientProfileResource.class);

    private static final String ENTITY_NAME = "clientProfile";
        
    private final ClientProfileRepository clientProfileRepository;

    public ClientProfileResource(ClientProfileRepository clientProfileRepository) {
        this.clientProfileRepository = clientProfileRepository;
    }

    /**
     * POST  /client-profiles : Create a new clientProfile.
     *
     * @param clientProfile the clientProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientProfile, or with status 400 (Bad Request) if the clientProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-profiles")
    @Timed
    public ResponseEntity<ClientProfile> createClientProfile(@RequestBody ClientProfile clientProfile) throws URISyntaxException {
        log.debug("REST request to save ClientProfile : {}", clientProfile);
        if (clientProfile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientProfile cannot already have an ID")).body(null);
        }
        ClientProfile result = clientProfileRepository.save(clientProfile);
        return ResponseEntity.created(new URI("/api/client-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-profiles : Updates an existing clientProfile.
     *
     * @param clientProfile the clientProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientProfile,
     * or with status 400 (Bad Request) if the clientProfile is not valid,
     * or with status 500 (Internal Server Error) if the clientProfile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-profiles")
    @Timed
    public ResponseEntity<ClientProfile> updateClientProfile(@RequestBody ClientProfile clientProfile) throws URISyntaxException {
        log.debug("REST request to update ClientProfile : {}", clientProfile);
        if (clientProfile.getId() == null) {
            return createClientProfile(clientProfile);
        }
        ClientProfile result = clientProfileRepository.save(clientProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientProfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-profiles : get all the clientProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clientProfiles in body
     */
    @GetMapping("/client-profiles")
    @Timed
    public List<ClientProfile> getAllClientProfiles() {
        log.debug("REST request to get all ClientProfiles");
        List<ClientProfile> clientProfiles = clientProfileRepository.findAll();
        return clientProfiles;
    }

    /**
     * GET  /client-profiles/:id : get the "id" clientProfile.
     *
     * @param id the id of the clientProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientProfile, or with status 404 (Not Found)
     */
    @GetMapping("/client-profiles/{id}")
    @Timed
    public ResponseEntity<ClientProfile> getClientProfile(@PathVariable Long id) {
        log.debug("REST request to get ClientProfile : {}", id);
        ClientProfile clientProfile = clientProfileRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientProfile));
    }

    /**
     * DELETE  /client-profiles/:id : delete the "id" clientProfile.
     *
     * @param id the id of the clientProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientProfile(@PathVariable Long id) {
        log.debug("REST request to delete ClientProfile : {}", id);
        clientProfileRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
