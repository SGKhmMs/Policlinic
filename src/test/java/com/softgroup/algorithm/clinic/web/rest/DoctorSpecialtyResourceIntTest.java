package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.DoctorSpecialty;
import com.softgroup.algorithm.clinic.repository.DoctorSpecialtyRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DoctorSpecialtyResource REST controller.
 *
 * @see DoctorSpecialtyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class DoctorSpecialtyResourceIntTest {

    @Autowired
    private DoctorSpecialtyRepository doctorSpecialtyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDoctorSpecialtyMockMvc;

    private DoctorSpecialty doctorSpecialty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DoctorSpecialtyResource doctorSpecialtyResource = new DoctorSpecialtyResource(doctorSpecialtyRepository);
        this.restDoctorSpecialtyMockMvc = MockMvcBuilders.standaloneSetup(doctorSpecialtyResource)
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
    public static DoctorSpecialty createEntity(EntityManager em) {
        DoctorSpecialty doctorSpecialty = new DoctorSpecialty();
        return doctorSpecialty;
    }

    @Before
    public void initTest() {
        doctorSpecialty = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorSpecialty() throws Exception {
        int databaseSizeBeforeCreate = doctorSpecialtyRepository.findAll().size();

        // Create the DoctorSpecialty
        restDoctorSpecialtyMockMvc.perform(post("/api/doctor-specialties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorSpecialty)))
            .andExpect(status().isCreated());

        // Validate the DoctorSpecialty in the database
        List<DoctorSpecialty> doctorSpecialtyList = doctorSpecialtyRepository.findAll();
        assertThat(doctorSpecialtyList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorSpecialty testDoctorSpecialty = doctorSpecialtyList.get(doctorSpecialtyList.size() - 1);
    }

    @Test
    @Transactional
    public void createDoctorSpecialtyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorSpecialtyRepository.findAll().size();

        // Create the DoctorSpecialty with an existing ID
        doctorSpecialty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorSpecialtyMockMvc.perform(post("/api/doctor-specialties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorSpecialty)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DoctorSpecialty> doctorSpecialtyList = doctorSpecialtyRepository.findAll();
        assertThat(doctorSpecialtyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoctorSpecialties() throws Exception {
        // Initialize the database
        doctorSpecialtyRepository.saveAndFlush(doctorSpecialty);

        // Get all the doctorSpecialtyList
        restDoctorSpecialtyMockMvc.perform(get("/api/doctor-specialties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorSpecialty.getId().intValue())));
    }

    @Test
    @Transactional
    public void getDoctorSpecialty() throws Exception {
        // Initialize the database
        doctorSpecialtyRepository.saveAndFlush(doctorSpecialty);

        // Get the doctorSpecialty
        restDoctorSpecialtyMockMvc.perform(get("/api/doctor-specialties/{id}", doctorSpecialty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctorSpecialty.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDoctorSpecialty() throws Exception {
        // Get the doctorSpecialty
        restDoctorSpecialtyMockMvc.perform(get("/api/doctor-specialties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorSpecialty() throws Exception {
        // Initialize the database
        doctorSpecialtyRepository.saveAndFlush(doctorSpecialty);
        int databaseSizeBeforeUpdate = doctorSpecialtyRepository.findAll().size();

        // Update the doctorSpecialty
        DoctorSpecialty updatedDoctorSpecialty = doctorSpecialtyRepository.findOne(doctorSpecialty.getId());

        restDoctorSpecialtyMockMvc.perform(put("/api/doctor-specialties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctorSpecialty)))
            .andExpect(status().isOk());

        // Validate the DoctorSpecialty in the database
        List<DoctorSpecialty> doctorSpecialtyList = doctorSpecialtyRepository.findAll();
        assertThat(doctorSpecialtyList).hasSize(databaseSizeBeforeUpdate);
        DoctorSpecialty testDoctorSpecialty = doctorSpecialtyList.get(doctorSpecialtyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorSpecialty() throws Exception {
        int databaseSizeBeforeUpdate = doctorSpecialtyRepository.findAll().size();

        // Create the DoctorSpecialty

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoctorSpecialtyMockMvc.perform(put("/api/doctor-specialties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorSpecialty)))
            .andExpect(status().isCreated());

        // Validate the DoctorSpecialty in the database
        List<DoctorSpecialty> doctorSpecialtyList = doctorSpecialtyRepository.findAll();
        assertThat(doctorSpecialtyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoctorSpecialty() throws Exception {
        // Initialize the database
        doctorSpecialtyRepository.saveAndFlush(doctorSpecialty);
        int databaseSizeBeforeDelete = doctorSpecialtyRepository.findAll().size();

        // Get the doctorSpecialty
        restDoctorSpecialtyMockMvc.perform(delete("/api/doctor-specialties/{id}", doctorSpecialty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DoctorSpecialty> doctorSpecialtyList = doctorSpecialtyRepository.findAll();
        assertThat(doctorSpecialtyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorSpecialty.class);
        DoctorSpecialty doctorSpecialty1 = new DoctorSpecialty();
        doctorSpecialty1.setId(1L);
        DoctorSpecialty doctorSpecialty2 = new DoctorSpecialty();
        doctorSpecialty2.setId(doctorSpecialty1.getId());
        assertThat(doctorSpecialty1).isEqualTo(doctorSpecialty2);
        doctorSpecialty2.setId(2L);
        assertThat(doctorSpecialty1).isNotEqualTo(doctorSpecialty2);
        doctorSpecialty1.setId(null);
        assertThat(doctorSpecialty1).isNotEqualTo(doctorSpecialty2);
    }
}
