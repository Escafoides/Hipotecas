package com.metrica.hipotecas.web.rest;

import com.metrica.hipotecas.HipotecasApp;

import com.metrica.hipotecas.domain.LocalizacionesTerreno;
import com.metrica.hipotecas.repository.LocalizacionesTerrenoRepository;
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
 * Test class for the LocalizacionesTerrenoResource REST controller.
 *
 * @see LocalizacionesTerrenoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipotecasApp.class)
public class LocalizacionesTerrenoResourceIntTest {

    private static final Integer DEFAULT_LATITUDE = 1;
    private static final Integer UPDATED_LATITUDE = 2;

    private static final Integer DEFAULT_LONGITUDE = 1;
    private static final Integer UPDATED_LONGITUDE = 2;

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_COMUNIDAD = "AAAAAAAAAA";
    private static final String UPDATED_COMUNIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    @Autowired
    private LocalizacionesTerrenoRepository localizacionesTerrenoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocalizacionesTerrenoMockMvc;

    private LocalizacionesTerreno localizacionesTerreno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocalizacionesTerrenoResource localizacionesTerrenoResource = new LocalizacionesTerrenoResource(localizacionesTerrenoRepository);
        this.restLocalizacionesTerrenoMockMvc = MockMvcBuilders.standaloneSetup(localizacionesTerrenoResource)
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
    public static LocalizacionesTerreno createEntity(EntityManager em) {
        LocalizacionesTerreno localizacionesTerreno = new LocalizacionesTerreno()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .pais(DEFAULT_PAIS)
            .comunidad(DEFAULT_COMUNIDAD)
            .provincia(DEFAULT_PROVINCIA);
        return localizacionesTerreno;
    }

    @Before
    public void initTest() {
        localizacionesTerreno = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalizacionesTerreno() throws Exception {
        int databaseSizeBeforeCreate = localizacionesTerrenoRepository.findAll().size();

        // Create the LocalizacionesTerreno
        restLocalizacionesTerrenoMockMvc.perform(post("/api/localizaciones-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacionesTerreno)))
            .andExpect(status().isCreated());

        // Validate the LocalizacionesTerreno in the database
        List<LocalizacionesTerreno> localizacionesTerrenoList = localizacionesTerrenoRepository.findAll();
        assertThat(localizacionesTerrenoList).hasSize(databaseSizeBeforeCreate + 1);
        LocalizacionesTerreno testLocalizacionesTerreno = localizacionesTerrenoList.get(localizacionesTerrenoList.size() - 1);
        assertThat(testLocalizacionesTerreno.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLocalizacionesTerreno.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLocalizacionesTerreno.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testLocalizacionesTerreno.getComunidad()).isEqualTo(DEFAULT_COMUNIDAD);
        assertThat(testLocalizacionesTerreno.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
    }

    @Test
    @Transactional
    public void createLocalizacionesTerrenoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localizacionesTerrenoRepository.findAll().size();

        // Create the LocalizacionesTerreno with an existing ID
        localizacionesTerreno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalizacionesTerrenoMockMvc.perform(post("/api/localizaciones-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacionesTerreno)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LocalizacionesTerreno> localizacionesTerrenoList = localizacionesTerrenoRepository.findAll();
        assertThat(localizacionesTerrenoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocalizacionesTerrenos() throws Exception {
        // Initialize the database
        localizacionesTerrenoRepository.saveAndFlush(localizacionesTerreno);

        // Get all the localizacionesTerrenoList
        restLocalizacionesTerrenoMockMvc.perform(get("/api/localizaciones-terrenos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localizacionesTerreno.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())))
            .andExpect(jsonPath("$.[*].comunidad").value(hasItem(DEFAULT_COMUNIDAD.toString())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())));
    }

    @Test
    @Transactional
    public void getLocalizacionesTerreno() throws Exception {
        // Initialize the database
        localizacionesTerrenoRepository.saveAndFlush(localizacionesTerreno);

        // Get the localizacionesTerreno
        restLocalizacionesTerrenoMockMvc.perform(get("/api/localizaciones-terrenos/{id}", localizacionesTerreno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localizacionesTerreno.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()))
            .andExpect(jsonPath("$.comunidad").value(DEFAULT_COMUNIDAD.toString()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalizacionesTerreno() throws Exception {
        // Get the localizacionesTerreno
        restLocalizacionesTerrenoMockMvc.perform(get("/api/localizaciones-terrenos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalizacionesTerreno() throws Exception {
        // Initialize the database
        localizacionesTerrenoRepository.saveAndFlush(localizacionesTerreno);
        int databaseSizeBeforeUpdate = localizacionesTerrenoRepository.findAll().size();

        // Update the localizacionesTerreno
        LocalizacionesTerreno updatedLocalizacionesTerreno = localizacionesTerrenoRepository.findOne(localizacionesTerreno.getId());
        updatedLocalizacionesTerreno
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .pais(UPDATED_PAIS)
            .comunidad(UPDATED_COMUNIDAD)
            .provincia(UPDATED_PROVINCIA);

        restLocalizacionesTerrenoMockMvc.perform(put("/api/localizaciones-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalizacionesTerreno)))
            .andExpect(status().isOk());

        // Validate the LocalizacionesTerreno in the database
        List<LocalizacionesTerreno> localizacionesTerrenoList = localizacionesTerrenoRepository.findAll();
        assertThat(localizacionesTerrenoList).hasSize(databaseSizeBeforeUpdate);
        LocalizacionesTerreno testLocalizacionesTerreno = localizacionesTerrenoList.get(localizacionesTerrenoList.size() - 1);
        assertThat(testLocalizacionesTerreno.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLocalizacionesTerreno.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLocalizacionesTerreno.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testLocalizacionesTerreno.getComunidad()).isEqualTo(UPDATED_COMUNIDAD);
        assertThat(testLocalizacionesTerreno.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalizacionesTerreno() throws Exception {
        int databaseSizeBeforeUpdate = localizacionesTerrenoRepository.findAll().size();

        // Create the LocalizacionesTerreno

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocalizacionesTerrenoMockMvc.perform(put("/api/localizaciones-terrenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacionesTerreno)))
            .andExpect(status().isCreated());

        // Validate the LocalizacionesTerreno in the database
        List<LocalizacionesTerreno> localizacionesTerrenoList = localizacionesTerrenoRepository.findAll();
        assertThat(localizacionesTerrenoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocalizacionesTerreno() throws Exception {
        // Initialize the database
        localizacionesTerrenoRepository.saveAndFlush(localizacionesTerreno);
        int databaseSizeBeforeDelete = localizacionesTerrenoRepository.findAll().size();

        // Get the localizacionesTerreno
        restLocalizacionesTerrenoMockMvc.perform(delete("/api/localizaciones-terrenos/{id}", localizacionesTerreno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LocalizacionesTerreno> localizacionesTerrenoList = localizacionesTerrenoRepository.findAll();
        assertThat(localizacionesTerrenoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalizacionesTerreno.class);
    }
}
