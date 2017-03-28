package com.metrica.hipotecas.web.rest;

import com.metrica.hipotecas.HipotecasApp;

import com.metrica.hipotecas.domain.ImporteFinal;
import com.metrica.hipotecas.repository.ImporteFinalRepository;
import com.metrica.hipotecas.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImporteFinalResource REST controller.
 *
 * @see ImporteFinalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipotecasApp.class)
public class ImporteFinalResourceIntTest {

    private static final Integer DEFAULT_INTERVALO = 1;
    private static final Integer UPDATED_INTERVALO = 2;

    private static final Float DEFAULT_CAPITAL_INICIAL = 1F;
    private static final Float UPDATED_CAPITAL_INICIAL = 2F;

    private static final Float DEFAULT_INTERES_ANUAL = 1F;
    private static final Float UPDATED_INTERES_ANUAL = 2F;

    private static final Float DEFAULT_IMPORTE_ANUAL = 1F;
    private static final Float UPDATED_IMPORTE_ANUAL = 2F;

    private static final Float DEFAULT_IMPORTE_MES = 1F;
    private static final Float UPDATED_IMPORTE_MES = 2F;

    private static final Float DEFAULT_IMPORTE_TOTAL = 1F;
    private static final Float UPDATED_IMPORTE_TOTAL = 2F;

    @Autowired
    private ImporteFinalRepository importeFinalRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImporteFinalMockMvc;

    private ImporteFinal importeFinal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImporteFinalResource importeFinalResource = new ImporteFinalResource(importeFinalRepository);
        this.restImporteFinalMockMvc = MockMvcBuilders.standaloneSetup(importeFinalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImporteFinal createEntity(EntityManager em) {
        ImporteFinal importeFinal = new ImporteFinal()
            .intervalo(DEFAULT_INTERVALO)
            .capitalInicial(DEFAULT_CAPITAL_INICIAL)
            .interesAnual(DEFAULT_INTERES_ANUAL)
            .importeAnual(DEFAULT_IMPORTE_ANUAL)
            .importeMes(DEFAULT_IMPORTE_MES)
            .importeTotal(DEFAULT_IMPORTE_TOTAL);
        return importeFinal;
    }

    @Before
    public void initTest() {
        importeFinal = createEntity(em);
    }

    @Test
    @Transactional
    public void createImporteFinal() throws Exception {
        int databaseSizeBeforeCreate = importeFinalRepository.findAll().size();

        // Create the ImporteFinal
        restImporteFinalMockMvc.perform(post("/api/importe-finals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importeFinal)))
            .andExpect(status().isCreated());

        // Validate the ImporteFinal in the database
        List<ImporteFinal> importeFinalList = importeFinalRepository.findAll();
        assertThat(importeFinalList).hasSize(databaseSizeBeforeCreate + 1);
        ImporteFinal testImporteFinal = importeFinalList.get(importeFinalList.size() - 1);
        assertThat(testImporteFinal.getIntervalo()).isEqualTo(DEFAULT_INTERVALO);
        assertThat(testImporteFinal.getCapitalInicial()).isEqualTo(DEFAULT_CAPITAL_INICIAL);
        assertThat(testImporteFinal.getInteresAnual()).isEqualTo(DEFAULT_INTERES_ANUAL);
        assertThat(testImporteFinal.getImporteAnual()).isEqualTo(DEFAULT_IMPORTE_ANUAL);
        assertThat(testImporteFinal.getImporteMes()).isEqualTo(DEFAULT_IMPORTE_MES);
        assertThat(testImporteFinal.getImporteTotal()).isEqualTo(DEFAULT_IMPORTE_TOTAL);
    }

    @Test
    @Transactional
    public void createImporteFinalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = importeFinalRepository.findAll().size();

        // Create the ImporteFinal with an existing ID
        importeFinal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImporteFinalMockMvc.perform(post("/api/importe-finals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importeFinal)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ImporteFinal> importeFinalList = importeFinalRepository.findAll();
        assertThat(importeFinalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImporteFinals() throws Exception {
        // Initialize the database
        importeFinalRepository.saveAndFlush(importeFinal);

        // Get all the importeFinalList
        restImporteFinalMockMvc.perform(get("/api/importe-finals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(importeFinal.getId().intValue())))
            .andExpect(jsonPath("$.[*].intervalo").value(hasItem(DEFAULT_INTERVALO)))
            .andExpect(jsonPath("$.[*].capitalInicial").value(hasItem(DEFAULT_CAPITAL_INICIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].interesAnual").value(hasItem(DEFAULT_INTERES_ANUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].importeAnual").value(hasItem(DEFAULT_IMPORTE_ANUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].importeMes").value(hasItem(DEFAULT_IMPORTE_MES.doubleValue())))
            .andExpect(jsonPath("$.[*].importeTotal").value(hasItem(DEFAULT_IMPORTE_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getImporteFinal() throws Exception {
        // Initialize the database
        importeFinalRepository.saveAndFlush(importeFinal);

        // Get the importeFinal
        restImporteFinalMockMvc.perform(get("/api/importe-finals/{id}", importeFinal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(importeFinal.getId().intValue()))
            .andExpect(jsonPath("$.intervalo").value(DEFAULT_INTERVALO))
            .andExpect(jsonPath("$.capitalInicial").value(DEFAULT_CAPITAL_INICIAL.doubleValue()))
            .andExpect(jsonPath("$.interesAnual").value(DEFAULT_INTERES_ANUAL.doubleValue()))
            .andExpect(jsonPath("$.importeAnual").value(DEFAULT_IMPORTE_ANUAL.doubleValue()))
            .andExpect(jsonPath("$.importeMes").value(DEFAULT_IMPORTE_MES.doubleValue()))
            .andExpect(jsonPath("$.importeTotal").value(DEFAULT_IMPORTE_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImporteFinal() throws Exception {
        // Get the importeFinal
        restImporteFinalMockMvc.perform(get("/api/importe-finals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImporteFinal() throws Exception {
        // Initialize the database
        importeFinalRepository.saveAndFlush(importeFinal);
        int databaseSizeBeforeUpdate = importeFinalRepository.findAll().size();

        // Update the importeFinal
        ImporteFinal updatedImporteFinal = importeFinalRepository.findOne(importeFinal.getId());
        updatedImporteFinal
            .intervalo(UPDATED_INTERVALO)
            .capitalInicial(UPDATED_CAPITAL_INICIAL)
            .interesAnual(UPDATED_INTERES_ANUAL)
            .importeAnual(UPDATED_IMPORTE_ANUAL)
            .importeMes(UPDATED_IMPORTE_MES)
            .importeTotal(UPDATED_IMPORTE_TOTAL);

        restImporteFinalMockMvc.perform(put("/api/importe-finals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImporteFinal)))
            .andExpect(status().isOk());

        // Validate the ImporteFinal in the database
        List<ImporteFinal> importeFinalList = importeFinalRepository.findAll();
        assertThat(importeFinalList).hasSize(databaseSizeBeforeUpdate);
        ImporteFinal testImporteFinal = importeFinalList.get(importeFinalList.size() - 1);
        assertThat(testImporteFinal.getIntervalo()).isEqualTo(UPDATED_INTERVALO);
        assertThat(testImporteFinal.getCapitalInicial()).isEqualTo(UPDATED_CAPITAL_INICIAL);
        assertThat(testImporteFinal.getInteresAnual()).isEqualTo(UPDATED_INTERES_ANUAL);
        assertThat(testImporteFinal.getImporteAnual()).isEqualTo(UPDATED_IMPORTE_ANUAL);
        assertThat(testImporteFinal.getImporteMes()).isEqualTo(UPDATED_IMPORTE_MES);
        assertThat(testImporteFinal.getImporteTotal()).isEqualTo(UPDATED_IMPORTE_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingImporteFinal() throws Exception {
        int databaseSizeBeforeUpdate = importeFinalRepository.findAll().size();

        // Create the ImporteFinal

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImporteFinalMockMvc.perform(put("/api/importe-finals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importeFinal)))
            .andExpect(status().isCreated());

        // Validate the ImporteFinal in the database
        List<ImporteFinal> importeFinalList = importeFinalRepository.findAll();
        assertThat(importeFinalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImporteFinal() throws Exception {
        // Initialize the database
        importeFinalRepository.saveAndFlush(importeFinal);
        int databaseSizeBeforeDelete = importeFinalRepository.findAll().size();

        // Get the importeFinal
        restImporteFinalMockMvc.perform(delete("/api/importe-finals/{id}", importeFinal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ImporteFinal> importeFinalList = importeFinalRepository.findAll();
        assertThat(importeFinalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImporteFinal.class);
    }
}
