package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.Clinic;
import com.softgroup.algorithm.clinic.repository.ClinicRepository;
import com.softgroup.algorithm.clinic.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClinicResource REST controller.
 *
 * @see ClinicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class ClinicResourceIntTest {

    private static final String DEFAULT_CLINIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLINIC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_WORK_DAT_BEGIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WORK_DAT_BEGIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_WORK_DAY_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WORK_DAY_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RECEPTION_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_RECEPTION_PHONE = "BBBBBBBBBB";

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClinicMockMvc;

    private Clinic clinic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClinicResource clinicResource = new ClinicResource(clinicRepository);
        this.restClinicMockMvc = MockMvcBuilders.standaloneSetup(clinicResource)
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
    public static Clinic createEntity(EntityManager em) {
        Clinic clinic = new Clinic()
            .clinicName(DEFAULT_CLINIC_NAME)
            .adress(DEFAULT_ADRESS)
            .workDatBegin(DEFAULT_WORK_DAT_BEGIN)
            .workDayEnd(DEFAULT_WORK_DAY_END)
            .receptionPhone(DEFAULT_RECEPTION_PHONE);
        return clinic;
    }

    @Before
    public void initTest() {
        clinic = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinic() throws Exception {
        int databaseSizeBeforeCreate = clinicRepository.findAll().size();

        // Create the Clinic
        restClinicMockMvc.perform(post("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isCreated());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeCreate + 1);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getClinicName()).isEqualTo(DEFAULT_CLINIC_NAME);
        assertThat(testClinic.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testClinic.getWorkDatBegin()).isEqualTo(DEFAULT_WORK_DAT_BEGIN);
        assertThat(testClinic.getWorkDayEnd()).isEqualTo(DEFAULT_WORK_DAY_END);
        assertThat(testClinic.getReceptionPhone()).isEqualTo(DEFAULT_RECEPTION_PHONE);
    }

    @Test
    @Transactional
    public void createClinicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clinicRepository.findAll().size();

        // Create the Clinic with an existing ID
        clinic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicMockMvc.perform(post("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClinics() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get all the clinicList
        restClinicMockMvc.perform(get("/api/clinics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinic.getId().intValue())))
            .andExpect(jsonPath("$.[*].clinicName").value(hasItem(DEFAULT_CLINIC_NAME.toString())))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS.toString())))
            .andExpect(jsonPath("$.[*].workDatBegin").value(hasItem(DEFAULT_WORK_DAT_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].workDayEnd").value(hasItem(DEFAULT_WORK_DAY_END.toString())))
            .andExpect(jsonPath("$.[*].receptionPhone").value(hasItem(DEFAULT_RECEPTION_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get the clinic
        restClinicMockMvc.perform(get("/api/clinics/{id}", clinic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clinic.getId().intValue()))
            .andExpect(jsonPath("$.clinicName").value(DEFAULT_CLINIC_NAME.toString()))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS.toString()))
            .andExpect(jsonPath("$.workDatBegin").value(DEFAULT_WORK_DAT_BEGIN.toString()))
            .andExpect(jsonPath("$.workDayEnd").value(DEFAULT_WORK_DAY_END.toString()))
            .andExpect(jsonPath("$.receptionPhone").value(DEFAULT_RECEPTION_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClinic() throws Exception {
        // Get the clinic
        restClinicMockMvc.perform(get("/api/clinics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Update the clinic
        Clinic updatedClinic = clinicRepository.findOne(clinic.getId());
        updatedClinic
            .clinicName(UPDATED_CLINIC_NAME)
            .adress(UPDATED_ADRESS)
            .workDatBegin(UPDATED_WORK_DAT_BEGIN)
            .workDayEnd(UPDATED_WORK_DAY_END)
            .receptionPhone(UPDATED_RECEPTION_PHONE);

        restClinicMockMvc.perform(put("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClinic)))
            .andExpect(status().isOk());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getClinicName()).isEqualTo(UPDATED_CLINIC_NAME);
        assertThat(testClinic.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testClinic.getWorkDatBegin()).isEqualTo(UPDATED_WORK_DAT_BEGIN);
        assertThat(testClinic.getWorkDayEnd()).isEqualTo(UPDATED_WORK_DAY_END);
        assertThat(testClinic.getReceptionPhone()).isEqualTo(UPDATED_RECEPTION_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingClinic() throws Exception {
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Create the Clinic

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClinicMockMvc.perform(put("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isCreated());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);
        int databaseSizeBeforeDelete = clinicRepository.findAll().size();

        // Get the clinic
        restClinicMockMvc.perform(delete("/api/clinics/{id}", clinic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clinic.class);
        Clinic clinic1 = new Clinic();
        clinic1.setId(1L);
        Clinic clinic2 = new Clinic();
        clinic2.setId(clinic1.getId());
        assertThat(clinic1).isEqualTo(clinic2);
        clinic2.setId(2L);
        assertThat(clinic1).isNotEqualTo(clinic2);
        clinic1.setId(null);
        assertThat(clinic1).isNotEqualTo(clinic2);
    }
}