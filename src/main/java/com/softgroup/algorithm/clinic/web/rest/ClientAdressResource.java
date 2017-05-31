package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.ClientAdress;

import com.softgroup.algorithm.clinic.repository.ClientAdressRepository;
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
 * REST controller for managing ClientAdress.
 */
@RestController
@RequestMapping("/api")
public class ClientAdressResource {

    private final Logger log = LoggerFactory.getLogger(ClientAdressResource.class);

    private static final String ENTITY_NAME = "clientAdress";
        
    private final ClientAdressRepository clientAdressRepository;

    public ClientAdressResource(ClientAdressRepository clientAdressRepository) {
        this.clientAdressRepository = clientAdressRepository;
    }

    /**
     * POST  /client-adresses : Create a new clientAdress.
     *
     * @param clientAdress the clientAdress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientAdress, or with status 400 (Bad Request) if the clientAdress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-adresses")
    @Timed
    public ResponseEntity<ClientAdress> createClientAdress(@RequestBody ClientAdress clientAdress) throws URISyntaxException {
        log.debug("REST request to save ClientAdress : {}", clientAdress);
        if (clientAdress.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientAdress cannot already have an ID")).body(null);
        }
        ClientAdress result = clientAdressRepository.save(clientAdress);
        return ResponseEntity.created(new URI("/api/client-adresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-adresses : Updates an existing clientAdress.
     *
     * @param clientAdress the clientAdress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientAdress,
     * or with status 400 (Bad Request) if the clientAdress is not valid,
     * or with status 500 (Internal Server Error) if the clientAdress couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-adresses")
    @Timed
    public ResponseEntity<ClientAdress> updateClientAdress(@RequestBody ClientAdress clientAdress) throws URISyntaxException {
        log.debug("REST request to update ClientAdress : {}", clientAdress);
        if (clientAdress.getId() == null) {
            return createClientAdress(clientAdress);
        }
        ClientAdress result = clientAdressRepository.save(clientAdress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientAdress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-adresses : get all the clientAdresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clientAdresses in body
     */
    @GetMapping("/client-adresses")
    @Timed
    public List<ClientAdress> getAllClientAdresses() {
        log.debug("REST request to get all ClientAdresses");
        List<ClientAdress> clientAdresses = clientAdressRepository.findAll();
        return clientAdresses;
    }

    /**
     * GET  /client-adresses/:id : get the "id" clientAdress.
     *
     * @param id the id of the clientAdress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientAdress, or with status 404 (Not Found)
     */
    @GetMapping("/client-adresses/{id}")
    @Timed
    public ResponseEntity<ClientAdress> getClientAdress(@PathVariable Long id) {
        log.debug("REST request to get ClientAdress : {}", id);
        ClientAdress clientAdress = clientAdressRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientAdress));
    }

    /**
     * DELETE  /client-adresses/:id : delete the "id" clientAdress.
     *
     * @param id the id of the clientAdress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-adresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientAdress(@PathVariable Long id) {
        log.debug("REST request to delete ClientAdress : {}", id);
        clientAdressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
