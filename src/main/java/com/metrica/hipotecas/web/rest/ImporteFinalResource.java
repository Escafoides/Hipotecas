package com.metrica.hipotecas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.metrica.hipotecas.domain.ImporteFinal;

import com.metrica.hipotecas.repository.ImporteFinalRepository;
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
 * REST controller for managing ImporteFinal.
 */
@RestController
@RequestMapping("/api")
public class ImporteFinalResource {

    private final Logger log = LoggerFactory.getLogger(ImporteFinalResource.class);

    private static final String ENTITY_NAME = "importeFinal";
        
    private final ImporteFinalRepository importeFinalRepository;

    public ImporteFinalResource(ImporteFinalRepository importeFinalRepository) {
        this.importeFinalRepository = importeFinalRepository;
    }

    /**
     * POST  /importe-finals : Create a new importeFinal.
     *
     * @param importeFinal the importeFinal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new importeFinal, or with status 400 (Bad Request) if the importeFinal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/importe-finals")
    @Timed
    public ResponseEntity<ImporteFinal> createImporteFinal(@RequestBody ImporteFinal importeFinal) throws URISyntaxException {
        log.debug("REST request to save ImporteFinal : {}", importeFinal);
        if (importeFinal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new importeFinal cannot already have an ID")).body(null);
        }
        ImporteFinal result = importeFinalRepository.save(importeFinal);
        return ResponseEntity.created(new URI("/api/importe-finals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /importe-finals : Updates an existing importeFinal.
     *
     * @param importeFinal the importeFinal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated importeFinal,
     * or with status 400 (Bad Request) if the importeFinal is not valid,
     * or with status 500 (Internal Server Error) if the importeFinal couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/importe-finals")
    @Timed
    public ResponseEntity<ImporteFinal> updateImporteFinal(@RequestBody ImporteFinal importeFinal) throws URISyntaxException {
        log.debug("REST request to update ImporteFinal : {}", importeFinal);
        if (importeFinal.getId() == null) {
            return createImporteFinal(importeFinal);
        }
        ImporteFinal result = importeFinalRepository.save(importeFinal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, importeFinal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /importe-finals : get all the importeFinals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of importeFinals in body
     */
    @GetMapping("/importe-finals")
    @Timed
    public List<ImporteFinal> getAllImporteFinals() {
        log.debug("REST request to get all ImporteFinals");
        List<ImporteFinal> importeFinals = importeFinalRepository.findAll();
        return importeFinals;
    }

    /**
     * GET  /importe-finals/:id : get the "id" importeFinal.
     *
     * @param id the id of the importeFinal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the importeFinal, or with status 404 (Not Found)
     */
    @GetMapping("/importe-finals/{id}")
    @Timed
    public ResponseEntity<ImporteFinal> getImporteFinal(@PathVariable Long id) {
        log.debug("REST request to get ImporteFinal : {}", id);
        ImporteFinal importeFinal = importeFinalRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(importeFinal));
    }

    /**
     * DELETE  /importe-finals/:id : delete the "id" importeFinal.
     *
     * @param id the id of the importeFinal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/importe-finals/{id}")
    @Timed
    public ResponseEntity<Void> deleteImporteFinal(@PathVariable Long id) {
        log.debug("REST request to delete ImporteFinal : {}", id);
        importeFinalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
