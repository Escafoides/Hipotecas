package com.metrica.hipotecas.web.rest;

import com.metrica.hipotecas.HipotecasApp;

import com.metrica.hipotecas.domain.Terreno;
import com.metrica.hipotecas.repository.TerrenoRepository;
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
 * Test class for the TerrenoResource REST controller.
 *
 * @see TerrenoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipotecasApp.class)
public class TerrenoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Float DEFAULT_PRECIO = 1F;
    private static final Float UPDATED_PRECIO = 2F;

    @Autowired
    private TerrenoRepository terrenoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTerrenoMockMvc;

    private Terreno terreno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TerrenoResource terrenoResource = new TerrenoResource(terrenoRepository);
        this.restTerrenoMockMvc = MockMvcBuilders.standaloneSetup(terrenoResource)
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
    public static Terreno createEntity(EntityManager em) {
        Terreno terreno = new Terreno()
            .nombre(DEFAULT_NOMBRE)
            .precio(DEFAULT_PRECIO);
        return terreno;
    }

    @Before
    public void initTest() {
        terreno = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerreno() throws Exception {
        int databaseSizeBeforeCreate = terrenoRepository.findAll().size();

        // Create the Terreno
        restTerrenoMockMvc.perform(post("/api/terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terreno)))
            .andExpect(status().isCreated());

        // Validate the Terreno in the database
        List<Terreno> terrenoList = terrenoRepository.findAll();
        assertThat(terrenoList).hasSize(databaseSizeBeforeCreate + 1);
        Terreno testTerreno = terrenoList.get(terrenoList.size() - 1);
        assertThat(testTerreno.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTerreno.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createTerrenoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = terrenoRepository.findAll().size();

        // Create the Terreno with an existing ID
        terreno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerrenoMockMvc.perform(post("/api/terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terreno)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Terreno> terrenoList = terrenoRepository.findAll();
        assertThat(terrenoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTerrenos() throws Exception {
        // Initialize the database
        terrenoRepository.saveAndFlush(terreno);

        // Get all the terrenoList
        restTerrenoMockMvc.perform(get("/api/terrenos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terreno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getTerreno() throws Exception {
        // Initialize the database
        terrenoRepository.saveAndFlush(terreno);

        // Get the terreno
        restTerrenoMockMvc.perform(get("/api/terrenos/{id}", terreno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(terreno.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTerreno() throws Exception {
        // Get the terreno
        restTerrenoMockMvc.perform(get("/api/terrenos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerreno() throws Exception {
        // Initialize the database
        terrenoRepository.saveAndFlush(terreno);
        int databaseSizeBeforeUpdate = terrenoRepository.findAll().size();

        // Update the terreno
        Terreno updatedTerreno = terrenoRepository.findOne(terreno.getId());
        updatedTerreno
            .nombre(UPDATED_NOMBRE)
            .precio(UPDATED_PRECIO);

        restTerrenoMockMvc.perform(put("/api/terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerreno)))
            .andExpect(status().isOk());

        // Validate the Terreno in the database
        List<Terreno> terrenoList = terrenoRepository.findAll();
        assertThat(terrenoList).hasSize(databaseSizeBeforeUpdate);
        Terreno testTerreno = terrenoList.get(terrenoList.size() - 1);
        assertThat(testTerreno.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTerreno.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTerreno() throws Exception {
        int databaseSizeBeforeUpdate = terrenoRepository.findAll().size();

        // Create the Terreno

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTerrenoMockMvc.perform(put("/api/terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terreno)))
            .andExpect(status().isCreated());

        // Validate the Terreno in the database
        List<Terreno> terrenoList = terrenoRepository.findAll();
        assertThat(terrenoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTerreno() throws Exception {
        // Initialize the database
        terrenoRepository.saveAndFlush(terreno);
        int databaseSizeBeforeDelete = terrenoRepository.findAll().size();

        // Get the terreno
        restTerrenoMockMvc.perform(delete("/api/terrenos/{id}", terreno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Terreno> terrenoList = terrenoRepository.findAll();
        assertThat(terrenoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Terreno.class);
    }
}
