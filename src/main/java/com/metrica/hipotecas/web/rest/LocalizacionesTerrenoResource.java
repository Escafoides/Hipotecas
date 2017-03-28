package com.metrica.hipotecas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.metrica.hipotecas.domain.LocalizacionesTerreno;

import com.metrica.hipotecas.repository.LocalizacionesTerrenoRepository;
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
 * REST controller for managing LocalizacionesTerreno.
 */
@RestController
@RequestMapping("/api")
public class LocalizacionesTerrenoResource {

    private final Logger log = LoggerFactory.getLogger(LocalizacionesTerrenoResource.class);

    private static final String ENTITY_NAME = "localizacionesTerreno";
        
    private final LocalizacionesTerrenoRepository localizacionesTerrenoRepository;

    public LocalizacionesTerrenoResource(LocalizacionesTerrenoRepository localizacionesTerrenoRepository) {
        this.localizacionesTerrenoRepository = localizacionesTerrenoRepository;
    }

    /**
     * POST  /localizaciones-terrenos : Create a new localizacionesTerreno.
     *
     * @param localizacionesTerreno the localizacionesTerreno to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localizacionesTerreno, or with status 400 (Bad Request) if the localizacionesTerreno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/localizaciones-terrenos")
    @Timed
    public ResponseEntity<LocalizacionesTerreno> createLocalizacionesTerreno(@RequestBody LocalizacionesTerreno localizacionesTerreno) throws URISyntaxException {
        log.debug("REST request to save LocalizacionesTerreno : {}", localizacionesTerreno);
        if (localizacionesTerreno.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new localizacionesTerreno cannot already have an ID")).body(null);
        }
        LocalizacionesTerreno result = localizacionesTerrenoRepository.save(localizacionesTerreno);
        return ResponseEntity.created(new URI("/api/localizaciones-terrenos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /localizaciones-terrenos : Updates an existing localizacionesTerreno.
     *
     * @param localizacionesTerreno the localizacionesTerreno to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localizacionesTerreno,
     * or with status 400 (Bad Request) if the localizacionesTerreno is not valid,
     * or with status 500 (Internal Server Error) if the localizacionesTerreno couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/localizaciones-terrenos")
    @Timed
    public ResponseEntity<LocalizacionesTerreno> updateLocalizacionesTerreno(@RequestBody LocalizacionesTerreno localizacionesTerreno) throws URISyntaxException {
        log.debug("REST request to update LocalizacionesTerreno : {}", localizacionesTerreno);
        if (localizacionesTerreno.getId() == null) {
            return createLocalizacionesTerreno(localizacionesTerreno);
        }
        LocalizacionesTerreno result = localizacionesTerrenoRepository.save(localizacionesTerreno);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localizacionesTerreno.getId().toString()))
            .body(result);
    }

    /**
     * GET  /localizaciones-terrenos : get all the localizacionesTerrenos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of localizacionesTerrenos in body
     */
    @GetMapping("/localizaciones-terrenos")
    @Timed
    public List<LocalizacionesTerreno> getAllLocalizacionesTerrenos() {
        log.debug("REST request to get all LocalizacionesTerrenos");
        List<LocalizacionesTerreno> localizacionesTerrenos = localizacionesTerrenoRepository.findAll();
        return localizacionesTerrenos;
    }

    /**
     * GET  /localizaciones-terrenos/:id : get the "id" localizacionesTerreno.
     *
     * @param id the id of the localizacionesTerreno to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localizacionesTerreno, or with status 404 (Not Found)
     */
    @GetMapping("/localizaciones-terrenos/{id}")
    @Timed
    public ResponseEntity<LocalizacionesTerreno> getLocalizacionesTerreno(@PathVariable Long id) {
        log.debug("REST request to get LocalizacionesTerreno : {}", id);
        LocalizacionesTerreno localizacionesTerreno = localizacionesTerrenoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localizacionesTerreno));
    }

    /**
     * DELETE  /localizaciones-terrenos/:id : delete the "id" localizacionesTerreno.
     *
     * @param id the id of the localizacionesTerreno to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/localizaciones-terrenos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalizacionesTerreno(@PathVariable Long id) {
        log.debug("REST request to delete LocalizacionesTerreno : {}", id);
        localizacionesTerrenoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
