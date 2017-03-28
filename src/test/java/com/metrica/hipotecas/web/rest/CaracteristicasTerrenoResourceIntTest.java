package com.metrica.hipotecas.web.rest;

import com.metrica.hipotecas.HipotecasApp;

import com.metrica.hipotecas.domain.CaracteristicasTerreno;
import com.metrica.hipotecas.repository.CaracteristicasTerrenoRepository;
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
 * Test class for the CaracteristicasTerrenoResource REST controller.
 *
 * @see CaracteristicasTerrenoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipotecasApp.class)
public class CaracteristicasTerrenoResourceIntTest {

    private static final Integer DEFAULT_SUPERFICIE = 1;
    private static final Integer UPDATED_SUPERFICIE = 2;

    private static final Integer DEFAULT_TIPO = 1;
    private static final Integer UPDATED_TIPO = 2;

    @Autowired
    private CaracteristicasTerrenoRepository caracteristicasTerrenoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCaracteristicasTerrenoMockMvc;

    private CaracteristicasTerreno caracteristicasTerreno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CaracteristicasTerrenoResource caracteristicasTerrenoResource = new CaracteristicasTerrenoResource(caracteristicasTerrenoRepository);
        this.restCaracteristicasTerrenoMockMvc = MockMvcBuilders.standaloneSetup(caracteristicasTerrenoResource)
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
    public static CaracteristicasTerreno createEntity(EntityManager em) {
        CaracteristicasTerreno caracteristicasTerreno = new CaracteristicasTerreno()
            .superficie(DEFAULT_SUPERFICIE)
            .tipo(DEFAULT_TIPO);
        return caracteristicasTerreno;
    }

    @Before
    public void initTest() {
        caracteristicasTerreno = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaracteristicasTerreno() throws Exception {
        int databaseSizeBeforeCreate = caracteristicasTerrenoRepository.findAll().size();

        // Create the CaracteristicasTerreno
        restCaracteristicasTerrenoMockMvc.perform(post("/api/caracteristicas-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicasTerreno)))
            .andExpect(status().isCreated());

        // Validate the CaracteristicasTerreno in the database
        List<CaracteristicasTerreno> caracteristicasTerrenoList = caracteristicasTerrenoRepository.findAll();
        assertThat(caracteristicasTerrenoList).hasSize(databaseSizeBeforeCreate + 1);
        CaracteristicasTerreno testCaracteristicasTerreno = caracteristicasTerrenoList.get(caracteristicasTerrenoList.size() - 1);
        assertThat(testCaracteristicasTerreno.getSuperficie()).isEqualTo(DEFAULT_SUPERFICIE);
        assertThat(testCaracteristicasTerreno.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createCaracteristicasTerrenoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caracteristicasTerrenoRepository.findAll().size();

        // Create the CaracteristicasTerreno with an existing ID
        caracteristicasTerreno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaracteristicasTerrenoMockMvc.perform(post("/api/caracteristicas-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicasTerreno)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CaracteristicasTerreno> caracteristicasTerrenoList = caracteristicasTerrenoRepository.findAll();
        assertThat(caracteristicasTerrenoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCaracteristicasTerrenos() throws Exception {
        // Initialize the database
        caracteristicasTerrenoRepository.saveAndFlush(caracteristicasTerreno);

        // Get all the caracteristicasTerrenoList
        restCaracteristicasTerrenoMockMvc.perform(get("/api/caracteristicas-terrenos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristicasTerreno.getId().intValue())))
            .andExpect(jsonPath("$.[*].superficie").value(hasItem(DEFAULT_SUPERFICIE)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));
    }

    @Test
    @Transactional
    public void getCaracteristicasTerreno() throws Exception {
        // Initialize the database
        caracteristicasTerrenoRepository.saveAndFlush(caracteristicasTerreno);

        // Get the caracteristicasTerreno
        restCaracteristicasTerrenoMockMvc.perform(get("/api/caracteristicas-terrenos/{id}", caracteristicasTerreno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caracteristicasTerreno.getId().intValue()))
            .andExpect(jsonPath("$.superficie").value(DEFAULT_SUPERFICIE))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO));
    }

    @Test
    @Transactional
    public void getNonExistingCaracteristicasTerreno() throws Exception {
        // Get the caracteristicasTerreno
        restCaracteristicasTerrenoMockMvc.perform(get("/api/caracteristicas-terrenos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaracteristicasTerreno() throws Exception {
        // Initialize the database
        caracteristicasTerrenoRepository.saveAndFlush(caracteristicasTerreno);
        int databaseSizeBeforeUpdate = caracteristicasTerrenoRepository.findAll().size();

        // Update the caracteristicasTerreno
        CaracteristicasTerreno updatedCaracteristicasTerreno = caracteristicasTerrenoRepository.findOne(caracteristicasTerreno.getId());
        updatedCaracteristicasTerreno
            .superficie(UPDATED_SUPERFICIE)
            .tipo(UPDATED_TIPO);

        restCaracteristicasTerrenoMockMvc.perform(put("/api/caracteristicas-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaracteristicasTerreno)))
            .andExpect(status().isOk());

        // Validate the CaracteristicasTerreno in the database
        List<CaracteristicasTerreno> caracteristicasTerrenoList = caracteristicasTerrenoRepository.findAll();
        assertThat(caracteristicasTerrenoList).hasSize(databaseSizeBeforeUpdate);
        CaracteristicasTerreno testCaracteristicasTerreno = caracteristicasTerrenoList.get(caracteristicasTerrenoList.size() - 1);
        assertThat(testCaracteristicasTerreno.getSuperficie()).isEqualTo(UPDATED_SUPERFICIE);
        assertThat(testCaracteristicasTerreno.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingCaracteristicasTerreno() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicasTerrenoRepository.findAll().size();

        // Create the CaracteristicasTerreno

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCaracteristicasTerrenoMockMvc.perform(put("/api/caracteristicas-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicasTerreno)))
            .andExpect(status().isCreated());

        // Validate the CaracteristicasTerreno in the database
        List<CaracteristicasTerreno> caracteristicasTerrenoList = caracteristicasTerrenoRepository.findAll();
        assertThat(caracteristicasTerrenoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCaracteristicasTerreno() throws Exception {
        // Initialize the database
        caracteristicasTerrenoRepository.saveAndFlush(caracteristicasTerreno);
        int databaseSizeBeforeDelete = caracteristicasTerrenoRepository.findAll().size();

        // Get the caracteristicasTerreno
        restCaracteristicasTerrenoMockMvc.perform(delete("/api/caracteristicas-terrenos/{id}", caracteristicasTerreno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CaracteristicasTerreno> caracteristicasTerrenoList = caracteristicasTerrenoRepository.findAll();
        assertThat(caracteristicasTerrenoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaracteristicasTerreno.class);
    }
}
