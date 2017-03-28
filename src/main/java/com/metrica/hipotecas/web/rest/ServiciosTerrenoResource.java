package com.metrica.hipotecas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.metrica.hipotecas.domain.ServiciosTerreno;

import com.metrica.hipotecas.repository.ServiciosTerrenoRepository;
import com.metrica.hipotecas.web.rest.util.HeaderUtil;
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
 * REST controller for managing ServiciosTerreno.
 */
@RestController
@RequestMapping("/api")
public class ServiciosTerrenoResource {

    private final Logger log = LoggerFactory.getLogger(ServiciosTerrenoResource.class);

    private static final String ENTITY_NAME = "serviciosTerreno";
        
    private final ServiciosTerrenoRepository serviciosTerrenoRepository;

    public ServiciosTerrenoResource(ServiciosTerrenoRepository serviciosTerrenoRepository) {
        this.serviciosTerrenoRepository = serviciosTerrenoRepository;
    }

    /**
     * POST  /servicios-terrenos : Create a new serviciosTerreno.
     *
     * @param serviciosTerreno the serviciosTerreno to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviciosTerreno, or with status 400 (Bad Request) if the serviciosTerreno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servicios-terrenos")
    @Timed
    public ResponseEntity<ServiciosTerreno> createServiciosTerreno(@RequestBody ServiciosTerreno serviciosTerreno) throws URISyntaxException {
        log.debug("REST request to save ServiciosTerreno : {}", serviciosTerreno);
        if (serviciosTerreno.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serviciosTerreno cannot already have an ID")).body(null);
        }
        ServiciosTerreno result = serviciosTerrenoRepository.save(serviciosTerreno);
        return ResponseEntity.created(new URI("/api/servicios-terrenos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servicios-terrenos : Updates an existing serviciosTerreno.
     *
     * @param serviciosTerreno the serviciosTerreno to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviciosTerreno,
     * or with status 400 (Bad Request) if the serviciosTerreno is not valid,
     * or with status 500 (Internal Server Error) if the serviciosTerreno couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servicios-terrenos")
    @Timed
    public ResponseEntity<ServiciosTerreno> updateServiciosTerreno(@RequestBody ServiciosTerreno serviciosTerreno) throws URISyntaxException {
        log.debug("REST request to update ServiciosTerreno : {}", serviciosTerreno);
        if (serviciosTerreno.getId() == null) {
            return createServiciosTerreno(serviciosTerreno);
        }
        ServiciosTerreno result = serviciosTerrenoRepository.save(serviciosTerreno);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviciosTerreno.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servicios-terrenos : get all the serviciosTerrenos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of serviciosTerrenos in body
     */
    @GetMapping("/servicios-terrenos")
    @Timed
    public List<ServiciosTerreno> getAllServiciosTerrenos() {
        log.debug("REST request to get all ServiciosTerrenos");
        List<ServiciosTerreno> serviciosTerrenos = serviciosTerrenoRepository.findAll();
        return serviciosTerrenos;
    }

    /**
     * GET  /servicios-terrenos/:id : get the "id" serviciosTerreno.
     *
     * @param id the id of the serviciosTerreno to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviciosTerreno, or with status 404 (Not Found)
     */
    @GetMapping("/servicios-terrenos/{id}")
    @Timed
    public ResponseEntity<ServiciosTerreno> getServiciosTerreno(@PathVariable Long id) {
        log.debug("REST request to get ServiciosTerreno : {}", id);
        ServiciosTerreno serviciosTerreno = serviciosTerrenoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serviciosTerreno));
    }

    /**
     * DELETE  /servicios-terrenos/:id : delete the "id" serviciosTerreno.
     *
     * @param id the id of the serviciosTerreno to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servicios-terrenos/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiciosTerreno(@PathVariable Long id) {
        log.debug("REST request to delete ServiciosTerreno : {}", id);
        serviciosTerrenoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
