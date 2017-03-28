package com.metrica.hipotecas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.metrica.hipotecas.domain.Terreno;

import com.metrica.hipotecas.repository.TerrenoRepository;
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
 * REST controller for managing Terreno.
 */
@RestController
@RequestMapping("/api")
public class TerrenoResource {

    private final Logger log = LoggerFactory.getLogger(TerrenoResource.class);

    private static final String ENTITY_NAME = "terreno";
        
    private final TerrenoRepository terrenoRepository;

    public TerrenoResource(TerrenoRepository terrenoRepository) {
        this.terrenoRepository = terrenoRepository;
    }

    /**
     * POST  /terrenos : Create a new terreno.
     *
     * @param terreno the terreno to create
     * @return the ResponseEntity with status 201 (Created) and with body the new terreno, or with status 400 (Bad Request) if the terreno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/terrenos")
    @Timed
    public ResponseEntity<Terreno> createTerreno(@RequestBody Terreno terreno) throws URISyntaxException {
        log.debug("REST request to save Terreno : {}", terreno);
        if (terreno.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new terreno cannot already have an ID")).body(null);
        }
        Terreno result = terrenoRepository.save(terreno);
        return ResponseEntity.created(new URI("/api/terrenos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /terrenos : Updates an existing terreno.
     *
     * @param terreno the terreno to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated terreno,
     * or with status 400 (Bad Request) if the terreno is not valid,
     * or with status 500 (Internal Server Error) if the terreno couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/terrenos")
    @Timed
    public ResponseEntity<Terreno> updateTerreno(@RequestBody Terreno terreno) throws URISyntaxException {
        log.debug("REST request to update Terreno : {}", terreno);
        if (terreno.getId() == null) {
            return createTerreno(terreno);
        }
        Terreno result = terrenoRepository.save(terreno);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, terreno.getId().toString()))
            .body(result);
    }

    /**
     * GET  /terrenos : get all the terrenos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of terrenos in body
     */
    @GetMapping("/terrenos")
    @Timed
    public List<Terreno> getAllTerrenos() {
        log.debug("REST request to get all Terrenos");
        List<Terreno> terrenos = terrenoRepository.findAll();
        return terrenos;
    }

    /**
     * GET  /terrenos/:id : get the "id" terreno.
     *
     * @param id the id of the terreno to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the terreno, or with status 404 (Not Found)
     */
    @GetMapping("/terrenos/{id}")
    @Timed
    public ResponseEntity<Terreno> getTerreno(@PathVariable Long id) {
        log.debug("REST request to get Terreno : {}", id);
        Terreno terreno = terrenoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(terreno));
    }

    /**
     * DELETE  /terrenos/:id : delete the "id" terreno.
     *
     * @param id the id of the terreno to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/terrenos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTerreno(@PathVariable Long id) {
        log.debug("REST request to delete Terreno : {}", id);
        terrenoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
