package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.Operation;

import com.softgroup.algorithm.clinic.repository.OperationRepository;
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
 * REST controller for managing Operation.
 */
@RestController
@RequestMapping("/api")
public class OperationResource {

    private final Logger log = LoggerFactory.getLogger(OperationResource.class);

    private static final String ENTITY_NAME = "operation";
        
    private final OperationRepository operationRepository;

    public OperationResource(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    /**
     * POST  /operations : Create a new operation.
     *
     * @param operation the operation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operation, or with status 400 (Bad Request) if the operation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operations")
    @Timed
    public ResponseEntity<Operation> createOperation(@RequestBody Operation operation) throws URISyntaxException {
        log.debug("REST request to save Operation : {}", operation);
        if (operation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new operation cannot already have an ID")).body(null);
        }
        Operation result = operationRepository.save(operation);
        return ResponseEntity.created(new URI("/api/operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operations : Updates an existing operation.
     *
     * @param operation the operation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operation,
     * or with status 400 (Bad Request) if the operation is not valid,
     * or with status 500 (Internal Server Error) if the operation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operations")
    @Timed
    public ResponseEntity<Operation> updateOperation(@RequestBody Operation operation) throws URISyntaxException {
        log.debug("REST request to update Operation : {}", operation);
        if (operation.getId() == null) {
            return createOperation(operation);
        }
        Operation result = operationRepository.save(operation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operations : get all the operations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of operations in body
     */
    @GetMapping("/operations")
    @Timed
    public List<Operation> getAllOperations() {
        log.debug("REST request to get all Operations");
        List<Operation> operations = operationRepository.findAll();
        return operations;
    }

    /**
     * GET  /operations/:id : get the "id" operation.
     *
     * @param id the id of the operation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operation, or with status 404 (Not Found)
     */
    @GetMapping("/operations/{id}")
    @Timed
    public ResponseEntity<Operation> getOperation(@PathVariable Long id) {
        log.debug("REST request to get Operation : {}", id);
        Operation operation = operationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operation));
    }

    /**
     * DELETE  /operations/:id : delete the "id" operation.
     *
     * @param id the id of the operation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        log.debug("REST request to delete Operation : {}", id);
        operationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
