package com.metrica.hipotecas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.metrica.hipotecas.domain.CaracteristicasTerreno;

import com.metrica.hipotecas.repository.CaracteristicasTerrenoRepository;
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
 * REST controller for managing CaracteristicasTerreno.
 */
@RestController
@RequestMapping("/api")
public class CaracteristicasTerrenoResource {

    private final Logger log = LoggerFactory.getLogger(CaracteristicasTerrenoResource.class);

    private static final String ENTITY_NAME = "caracteristicasTerreno";
        
    private final CaracteristicasTerrenoRepository caracteristicasTerrenoRepository;

    public CaracteristicasTerrenoResource(CaracteristicasTerrenoRepository caracteristicasTerrenoRepository) {
        this.caracteristicasTerrenoRepository = caracteristicasTerrenoRepository;
    }

    /**
     * POST  /caracteristicas-terrenos : Create a new caracteristicasTerreno.
     *
     * @param caracteristicasTerreno the caracteristicasTerreno to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caracteristicasTerreno, or with status 400 (Bad Request) if the caracteristicasTerreno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caracteristicas-terrenos")
    @Timed
    public ResponseEntity<CaracteristicasTerreno> createCaracteristicasTerreno(@RequestBody CaracteristicasTerreno caracteristicasTerreno) throws URISyntaxException {
        log.debug("REST request to save CaracteristicasTerreno : {}", caracteristicasTerreno);
        if (caracteristicasTerreno.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new caracteristicasTerreno cannot already have an ID")).body(null);
        }
        CaracteristicasTerreno result = caracteristicasTerrenoRepository.save(caracteristicasTerreno);
        return ResponseEntity.created(new URI("/api/caracteristicas-terrenos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /caracteristicas-terrenos : Updates an existing caracteristicasTerreno.
     *
     * @param caracteristicasTerreno the caracteristicasTerreno to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caracteristicasTerreno,
     * or with status 400 (Bad Request) if the caracteristicasTerreno is not valid,
     * or with status 500 (Internal Server Error) if the caracteristicasTerreno couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/caracteristicas-terrenos")
    @Timed
    public ResponseEntity<CaracteristicasTerreno> updateCaracteristicasTerreno(@RequestBody CaracteristicasTerreno caracteristicasTerreno) throws URISyntaxException {
        log.debug("REST request to update CaracteristicasTerreno : {}", caracteristicasTerreno);
        if (caracteristicasTerreno.getId() == null) {
            return createCaracteristicasTerreno(caracteristicasTerreno);
        }
        CaracteristicasTerreno result = caracteristicasTerrenoRepository.save(caracteristicasTerreno);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caracteristicasTerreno.getId().toString()))
            .body(result);
    }

    /**
     * GET  /caracteristicas-terrenos : get all the caracteristicasTerrenos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of caracteristicasTerrenos in body
     */
    @GetMapping("/caracteristicas-terrenos")
    @Timed
    public List<CaracteristicasTerreno> getAllCaracteristicasTerrenos() {
        log.debug("REST request to get all CaracteristicasTerrenos");
        List<CaracteristicasTerreno> caracteristicasTerrenos = caracteristicasTerrenoRepository.findAll();
        return caracteristicasTerrenos;
    }

    /**
     * GET  /caracteristicas-terrenos/:id : get the "id" caracteristicasTerreno.
     *
     * @param id the id of the caracteristicasTerreno to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caracteristicasTerreno, or with status 404 (Not Found)
     */
    @GetMapping("/caracteristicas-terrenos/{id}")
    @Timed
    public ResponseEntity<CaracteristicasTerreno> getCaracteristicasTerreno(@PathVariable Long id) {
        log.debug("REST request to get CaracteristicasTerreno : {}", id);
        CaracteristicasTerreno caracteristicasTerreno = caracteristicasTerrenoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caracteristicasTerreno));
    }

    /**
     * DELETE  /caracteristicas-terrenos/:id : delete the "id" caracteristicasTerreno.
     *
     * @param id the id of the caracteristicasTerreno to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/caracteristicas-terrenos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaracteristicasTerreno(@PathVariable Long id) {
        log.debug("REST request to delete CaracteristicasTerreno : {}", id);
        caracteristicasTerrenoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
