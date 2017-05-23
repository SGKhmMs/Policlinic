package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.ServiceOnAppointment;

import com.softgroup.algorithm.clinic.repository.ServiceOnAppointmentRepository;
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
 * REST controller for managing ServiceOnAppointment.
 */
@RestController
@RequestMapping("/api")
public class ServiceOnAppointmentResource {

    private final Logger log = LoggerFactory.getLogger(ServiceOnAppointmentResource.class);

    private static final String ENTITY_NAME = "serviceOnAppointment";
        
    private final ServiceOnAppointmentRepository serviceOnAppointmentRepository;

    public ServiceOnAppointmentResource(ServiceOnAppointmentRepository serviceOnAppointmentRepository) {
        this.serviceOnAppointmentRepository = serviceOnAppointmentRepository;
    }

    /**
     * POST  /service-on-appointments : Create a new serviceOnAppointment.
     *
     * @param serviceOnAppointment the serviceOnAppointment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceOnAppointment, or with status 400 (Bad Request) if the serviceOnAppointment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-on-appointments")
    @Timed
    public ResponseEntity<ServiceOnAppointment> createServiceOnAppointment(@RequestBody ServiceOnAppointment serviceOnAppointment) throws URISyntaxException {
        log.debug("REST request to save ServiceOnAppointment : {}", serviceOnAppointment);
        if (serviceOnAppointment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serviceOnAppointment cannot already have an ID")).body(null);
        }
        ServiceOnAppointment result = serviceOnAppointmentRepository.save(serviceOnAppointment);
        return ResponseEntity.created(new URI("/api/service-on-appointments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-on-appointments : Updates an existing serviceOnAppointment.
     *
     * @param serviceOnAppointment the serviceOnAppointment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceOnAppointment,
     * or with status 400 (Bad Request) if the serviceOnAppointment is not valid,
     * or with status 500 (Internal Server Error) if the serviceOnAppointment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-on-appointments")
    @Timed
    public ResponseEntity<ServiceOnAppointment> updateServiceOnAppointment(@RequestBody ServiceOnAppointment serviceOnAppointment) throws URISyntaxException {
        log.debug("REST request to update ServiceOnAppointment : {}", serviceOnAppointment);
        if (serviceOnAppointment.getId() == null) {
            return createServiceOnAppointment(serviceOnAppointment);
        }
        ServiceOnAppointment result = serviceOnAppointmentRepository.save(serviceOnAppointment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceOnAppointment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-on-appointments : get all the serviceOnAppointments.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of serviceOnAppointments in body
     */
    @GetMapping("/service-on-appointments")
    @Timed
    public List<ServiceOnAppointment> getAllServiceOnAppointments(@RequestParam(required = false) String filter) {
        if ("service-is-null".equals(filter)) {
            log.debug("REST request to get all ServiceOnAppointments where service is null");
            return StreamSupport
                .stream(serviceOnAppointmentRepository.findAll().spliterator(), false)
                .filter(serviceOnAppointment -> serviceOnAppointment.getService() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ServiceOnAppointments");
        List<ServiceOnAppointment> serviceOnAppointments = serviceOnAppointmentRepository.findAll();
        return serviceOnAppointments;
    }

    /**
     * GET  /service-on-appointments/:id : get the "id" serviceOnAppointment.
     *
     * @param id the id of the serviceOnAppointment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceOnAppointment, or with status 404 (Not Found)
     */
    @GetMapping("/service-on-appointments/{id}")
    @Timed
    public ResponseEntity<ServiceOnAppointment> getServiceOnAppointment(@PathVariable Long id) {
        log.debug("REST request to get ServiceOnAppointment : {}", id);
        ServiceOnAppointment serviceOnAppointment = serviceOnAppointmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serviceOnAppointment));
    }

    /**
     * DELETE  /service-on-appointments/:id : delete the "id" serviceOnAppointment.
     *
     * @param id the id of the serviceOnAppointment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-on-appointments/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceOnAppointment(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOnAppointment : {}", id);
        serviceOnAppointmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
