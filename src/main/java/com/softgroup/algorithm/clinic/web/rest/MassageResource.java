package com.softgroup.algorithm.clinic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softgroup.algorithm.clinic.domain.Massage;

import com.softgroup.algorithm.clinic.repository.MassageRepository;
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
 * REST controller for managing Massage.
 */
@RestController
@RequestMapping("/api")
public class MassageResource {

    private final Logger log = LoggerFactory.getLogger(MassageResource.class);

    private static final String ENTITY_NAME = "massage";
        
    private final MassageRepository massageRepository;

    public MassageResource(MassageRepository massageRepository) {
        this.massageRepository = massageRepository;
    }

    /**
     * POST  /massages : Create a new massage.
     *
     * @param massage the massage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new massage, or with status 400 (Bad Request) if the massage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/massages")
    @Timed
    public ResponseEntity<Massage> createMassage(@RequestBody Massage massage) throws URISyntaxException {
        log.debug("REST request to save Massage : {}", massage);
        if (massage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new massage cannot already have an ID")).body(null);
        }
        Massage result = massageRepository.save(massage);
        return ResponseEntity.created(new URI("/api/massages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /massages : Updates an existing massage.
     *
     * @param massage the massage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated massage,
     * or with status 400 (Bad Request) if the massage is not valid,
     * or with status 500 (Internal Server Error) if the massage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/massages")
    @Timed
    public ResponseEntity<Massage> updateMassage(@RequestBody Massage massage) throws URISyntaxException {
        log.debug("REST request to update Massage : {}", massage);
        if (massage.getId() == null) {
            return createMassage(massage);
        }
        Massage result = massageRepository.save(massage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, massage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /massages : get all the massages.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of massages in body
     */
    @GetMapping("/massages")
    @Timed
    public List<Massage> getAllMassages(@RequestParam(required = false) String filter) {
        if ("chat-is-null".equals(filter)) {
            log.debug("REST request to get all Massages where chat is null");
            return StreamSupport
                .stream(massageRepository.findAll().spliterator(), false)
                .filter(massage -> massage.getChat() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Massages");
        List<Massage> massages = massageRepository.findAll();
        return massages;
    }

    /**
     * GET  /massages/:id : get the "id" massage.
     *
     * @param id the id of the massage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the massage, or with status 404 (Not Found)
     */
    @GetMapping("/massages/{id}")
    @Timed
    public ResponseEntity<Massage> getMassage(@PathVariable Long id) {
        log.debug("REST request to get Massage : {}", id);
        Massage massage = massageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(massage));
    }

    /**
     * DELETE  /massages/:id : delete the "id" massage.
     *
     * @param id the id of the massage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/massages/{id}")
    @Timed
    public ResponseEntity<Void> deleteMassage(@PathVariable Long id) {
        log.debug("REST request to delete Massage : {}", id);
        massageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
