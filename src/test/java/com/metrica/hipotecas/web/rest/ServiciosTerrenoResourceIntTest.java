package com.metrica.hipotecas.web.rest;

import com.metrica.hipotecas.HipotecasApp;

import com.metrica.hipotecas.domain.ServiciosTerreno;
import com.metrica.hipotecas.repository.ServiciosTerrenoRepository;
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
 * Test class for the ServiciosTerrenoResource REST controller.
 *
 * @see ServiciosTerrenoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipotecasApp.class)
public class ServiciosTerrenoResourceIntTest {

    private static final Boolean DEFAULT_AGUA = false;
    private static final Boolean UPDATED_AGUA = true;

    private static final Boolean DEFAULT_LUZ = false;
    private static final Boolean UPDATED_LUZ = true;

    private static final Boolean DEFAULT_TELEFONO = false;
    private static final Boolean UPDATED_TELEFONO = true;

    private static final Boolean DEFAULT_CARRETERA = false;
    private static final Boolean UPDATED_CARRETERA = true;

    @Autowired
    private ServiciosTerrenoRepository serviciosTerrenoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiciosTerrenoMockMvc;

    private ServiciosTerreno serviciosTerreno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiciosTerrenoResource serviciosTerrenoResource = new ServiciosTerrenoResource(serviciosTerrenoRepository);
        this.restServiciosTerrenoMockMvc = MockMvcBuilders.standaloneSetup(serviciosTerrenoResource)
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
    public static ServiciosTerreno createEntity(EntityManager em) {
        ServiciosTerreno serviciosTerreno = new ServiciosTerreno()
            .agua(DEFAULT_AGUA)
            .luz(DEFAULT_LUZ)
            .telefono(DEFAULT_TELEFONO)
            .carretera(DEFAULT_CARRETERA);
        return serviciosTerreno;
    }

    @Before
    public void initTest() {
        serviciosTerreno = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiciosTerreno() throws Exception {
        int databaseSizeBeforeCreate = serviciosTerrenoRepository.findAll().size();

        // Create the ServiciosTerreno
        restServiciosTerrenoMockMvc.perform(post("/api/servicios-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviciosTerreno)))
            .andExpect(status().isCreated());

        // Validate the ServiciosTerreno in the database
        List<ServiciosTerreno> serviciosTerrenoList = serviciosTerrenoRepository.findAll();
        assertThat(serviciosTerrenoList).hasSize(databaseSizeBeforeCreate + 1);
        ServiciosTerreno testServiciosTerreno = serviciosTerrenoList.get(serviciosTerrenoList.size() - 1);
        assertThat(testServiciosTerreno.isAgua()).isEqualTo(DEFAULT_AGUA);
        assertThat(testServiciosTerreno.isLuz()).isEqualTo(DEFAULT_LUZ);
        assertThat(testServiciosTerreno.isTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testServiciosTerreno.isCarretera()).isEqualTo(DEFAULT_CARRETERA);
    }

    @Test
    @Transactional
    public void createServiciosTerrenoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviciosTerrenoRepository.findAll().size();

        // Create the ServiciosTerreno with an existing ID
        serviciosTerreno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiciosTerrenoMockMvc.perform(post("/api/servicios-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviciosTerreno)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ServiciosTerreno> serviciosTerrenoList = serviciosTerrenoRepository.findAll();
        assertThat(serviciosTerrenoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllServiciosTerrenos() throws Exception {
        // Initialize the database
        serviciosTerrenoRepository.saveAndFlush(serviciosTerreno);

        // Get all the serviciosTerrenoList
        restServiciosTerrenoMockMvc.perform(get("/api/servicios-terrenos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviciosTerreno.getId().intValue())))
            .andExpect(jsonPath("$.[*].agua").value(hasItem(DEFAULT_AGUA.booleanValue())))
            .andExpect(jsonPath("$.[*].luz").value(hasItem(DEFAULT_LUZ.booleanValue())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.booleanValue())))
            .andExpect(jsonPath("$.[*].carretera").value(hasItem(DEFAULT_CARRETERA.booleanValue())));
    }

    @Test
    @Transactional
    public void getServiciosTerreno() throws Exception {
        // Initialize the database
        serviciosTerrenoRepository.saveAndFlush(serviciosTerreno);

        // Get the serviciosTerreno
        restServiciosTerrenoMockMvc.perform(get("/api/servicios-terrenos/{id}", serviciosTerreno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviciosTerreno.getId().intValue()))
            .andExpect(jsonPath("$.agua").value(DEFAULT_AGUA.booleanValue()))
            .andExpect(jsonPath("$.luz").value(DEFAULT_LUZ.booleanValue()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.booleanValue()))
            .andExpect(jsonPath("$.carretera").value(DEFAULT_CARRETERA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiciosTerreno() throws Exception {
        // Get the serviciosTerreno
        restServiciosTerrenoMockMvc.perform(get("/api/servicios-terrenos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiciosTerreno() throws Exception {
        // Initialize the database
        serviciosTerrenoRepository.saveAndFlush(serviciosTerreno);
        int databaseSizeBeforeUpdate = serviciosTerrenoRepository.findAll().size();

        // Update the serviciosTerreno
        ServiciosTerreno updatedServiciosTerreno = serviciosTerrenoRepository.findOne(serviciosTerreno.getId());
        updatedServiciosTerreno
            .agua(UPDATED_AGUA)
            .luz(UPDATED_LUZ)
            .telefono(UPDATED_TELEFONO)
            .carretera(UPDATED_CARRETERA);

        restServiciosTerrenoMockMvc.perform(put("/api/servicios-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiciosTerreno)))
            .andExpect(status().isOk());

        // Validate the ServiciosTerreno in the database
        List<ServiciosTerreno> serviciosTerrenoList = serviciosTerrenoRepository.findAll();
        assertThat(serviciosTerrenoList).hasSize(databaseSizeBeforeUpdate);
        ServiciosTerreno testServiciosTerreno = serviciosTerrenoList.get(serviciosTerrenoList.size() - 1);
        assertThat(testServiciosTerreno.isAgua()).isEqualTo(UPDATED_AGUA);
        assertThat(testServiciosTerreno.isLuz()).isEqualTo(UPDATED_LUZ);
        assertThat(testServiciosTerreno.isTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testServiciosTerreno.isCarretera()).isEqualTo(UPDATED_CARRETERA);
    }

    @Test
    @Transactional
    public void updateNonExistingServiciosTerreno() throws Exception {
        int databaseSizeBeforeUpdate = serviciosTerrenoRepository.findAll().size();

        // Create the ServiciosTerreno

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiciosTerrenoMockMvc.perform(put("/api/servicios-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviciosTerreno)))
            .andExpect(status().isCreated());

        // Validate the ServiciosTerreno in the database
        List<ServiciosTerreno> serviciosTerrenoList = serviciosTerrenoRepository.findAll();
        assertThat(serviciosTerrenoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServiciosTerreno() throws Exception {
        // Initialize the database
        serviciosTerrenoRepository.saveAndFlush(serviciosTerreno);
        int databaseSizeBeforeDelete = serviciosTerrenoRepository.findAll().size();

        // Get the serviciosTerreno
        restServiciosTerrenoMockMvc.perform(delete("/api/servicios-terrenos/{id}", serviciosTerreno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiciosTerreno> serviciosTerrenoList = serviciosTerrenoRepository.findAll();
        assertThat(serviciosTerrenoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiciosTerreno.class);
    }
}
