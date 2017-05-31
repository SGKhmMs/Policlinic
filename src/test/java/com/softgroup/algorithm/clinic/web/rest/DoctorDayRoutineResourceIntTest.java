package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.DoctorDayRoutine;
import com.softgroup.algorithm.clinic.repository.DoctorDayRoutineRepository;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.softgroup.algorithm.clinic.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DoctorDayRoutineResource REST controller.
 *
 * @see DoctorDayRoutineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class DoctorDayRoutineResourceIntTest {

    private static final ZonedDateTime DEFAULT_DAY_BEGIN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DAY_BEGIN_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DAY_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DAY_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DoctorDayRoutineRepository doctorDayRoutineRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDoctorDayRoutineMockMvc;

    private DoctorDayRoutine doctorDayRoutine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DoctorDayRoutineResource doctorDayRoutineResource = new DoctorDayRoutineResource(doctorDayRoutineRepository);
        this.restDoctorDayRoutineMockMvc = MockMvcBuilders.standaloneSetup(doctorDayRoutineResource)
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
    public static DoctorDayRoutine createEntity(EntityManager em) {
        DoctorDayRoutine doctorDayRoutine = new DoctorDayRoutine()
            .dayBeginTime(DEFAULT_DAY_BEGIN_TIME)
            .dayEndTime(DEFAULT_DAY_END_TIME)
            .date(DEFAULT_DATE);
        return doctorDayRoutine;
    }

    @Before
    public void initTest() {
        doctorDayRoutine = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorDayRoutine() throws Exception {
        int databaseSizeBeforeCreate = doctorDayRoutineRepository.findAll().size();

        // Create the DoctorDayRoutine
        restDoctorDayRoutineMockMvc.perform(post("/api/doctor-day-routines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorDayRoutine)))
            .andExpect(status().isCreated());

        // Validate the DoctorDayRoutine in the database
        List<DoctorDayRoutine> doctorDayRoutineList = doctorDayRoutineRepository.findAll();
        assertThat(doctorDayRoutineList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorDayRoutine testDoctorDayRoutine = doctorDayRoutineList.get(doctorDayRoutineList.size() - 1);
        assertThat(testDoctorDayRoutine.getDayBeginTime()).isEqualTo(DEFAULT_DAY_BEGIN_TIME);
        assertThat(testDoctorDayRoutine.getDayEndTime()).isEqualTo(DEFAULT_DAY_END_TIME);
        assertThat(testDoctorDayRoutine.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDoctorDayRoutineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorDayRoutineRepository.findAll().size();

        // Create the DoctorDayRoutine with an existing ID
        doctorDayRoutine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorDayRoutineMockMvc.perform(post("/api/doctor-day-routines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorDayRoutine)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DoctorDayRoutine> doctorDayRoutineList = doctorDayRoutineRepository.findAll();
        assertThat(doctorDayRoutineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoctorDayRoutines() throws Exception {
        // Initialize the database
        doctorDayRoutineRepository.saveAndFlush(doctorDayRoutine);

        // Get all the doctorDayRoutineList
        restDoctorDayRoutineMockMvc.perform(get("/api/doctor-day-routines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorDayRoutine.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayBeginTime").value(hasItem(sameInstant(DEFAULT_DAY_BEGIN_TIME))))
            .andExpect(jsonPath("$.[*].dayEndTime").value(hasItem(sameInstant(DEFAULT_DAY_END_TIME))))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDoctorDayRoutine() throws Exception {
        // Initialize the database
        doctorDayRoutineRepository.saveAndFlush(doctorDayRoutine);

        // Get the doctorDayRoutine
        restDoctorDayRoutineMockMvc.perform(get("/api/doctor-day-routines/{id}", doctorDayRoutine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctorDayRoutine.getId().intValue()))
            .andExpect(jsonPath("$.dayBeginTime").value(sameInstant(DEFAULT_DAY_BEGIN_TIME)))
            .andExpect(jsonPath("$.dayEndTime").value(sameInstant(DEFAULT_DAY_END_TIME)))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoctorDayRoutine() throws Exception {
        // Get the doctorDayRoutine
        restDoctorDayRoutineMockMvc.perform(get("/api/doctor-day-routines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorDayRoutine() throws Exception {
        // Initialize the database
        doctorDayRoutineRepository.saveAndFlush(doctorDayRoutine);
        int databaseSizeBeforeUpdate = doctorDayRoutineRepository.findAll().size();

        // Update the doctorDayRoutine
        DoctorDayRoutine updatedDoctorDayRoutine = doctorDayRoutineRepository.findOne(doctorDayRoutine.getId());
        updatedDoctorDayRoutine
            .dayBeginTime(UPDATED_DAY_BEGIN_TIME)
            .dayEndTime(UPDATED_DAY_END_TIME)
            .date(UPDATED_DATE);

        restDoctorDayRoutineMockMvc.perform(put("/api/doctor-day-routines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctorDayRoutine)))
            .andExpect(status().isOk());

        // Validate the DoctorDayRoutine in the database
        List<DoctorDayRoutine> doctorDayRoutineList = doctorDayRoutineRepository.findAll();
        assertThat(doctorDayRoutineList).hasSize(databaseSizeBeforeUpdate);
        DoctorDayRoutine testDoctorDayRoutine = doctorDayRoutineList.get(doctorDayRoutineList.size() - 1);
        assertThat(testDoctorDayRoutine.getDayBeginTime()).isEqualTo(UPDATED_DAY_BEGIN_TIME);
        assertThat(testDoctorDayRoutine.getDayEndTime()).isEqualTo(UPDATED_DAY_END_TIME);
        assertThat(testDoctorDayRoutine.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorDayRoutine() throws Exception {
        int databaseSizeBeforeUpdate = doctorDayRoutineRepository.findAll().size();

        // Create the DoctorDayRoutine

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoctorDayRoutineMockMvc.perform(put("/api/doctor-day-routines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorDayRoutine)))
            .andExpect(status().isCreated());

        // Validate the DoctorDayRoutine in the database
        List<DoctorDayRoutine> doctorDayRoutineList = doctorDayRoutineRepository.findAll();
        assertThat(doctorDayRoutineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoctorDayRoutine() throws Exception {
        // Initialize the database
        doctorDayRoutineRepository.saveAndFlush(doctorDayRoutine);
        int databaseSizeBeforeDelete = doctorDayRoutineRepository.findAll().size();

        // Get the doctorDayRoutine
        restDoctorDayRoutineMockMvc.perform(delete("/api/doctor-day-routines/{id}", doctorDayRoutine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DoctorDayRoutine> doctorDayRoutineList = doctorDayRoutineRepository.findAll();
        assertThat(doctorDayRoutineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorDayRoutine.class);
        DoctorDayRoutine doctorDayRoutine1 = new DoctorDayRoutine();
        doctorDayRoutine1.setId(1L);
        DoctorDayRoutine doctorDayRoutine2 = new DoctorDayRoutine();
        doctorDayRoutine2.setId(doctorDayRoutine1.getId());
        assertThat(doctorDayRoutine1).isEqualTo(doctorDayRoutine2);
        doctorDayRoutine2.setId(2L);
        assertThat(doctorDayRoutine1).isNotEqualTo(doctorDayRoutine2);
        doctorDayRoutine1.setId(null);
        assertThat(doctorDayRoutine1).isNotEqualTo(doctorDayRoutine2);
    }
}
