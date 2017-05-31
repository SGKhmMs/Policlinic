package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.ClinicDoctor;
import com.softgroup.algorithm.clinic.repository.ClinicDoctorRepository;
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
 * Test class for the ClinicDoctorResource REST controller.
 *
 * @see ClinicDoctorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class ClinicDoctorResourceIntTest {

    @Autowired
    private ClinicDoctorRepository clinicDoctorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClinicDoctorMockMvc;

    private ClinicDoctor clinicDoctor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClinicDoctorResource clinicDoctorResource = new ClinicDoctorResource(clinicDoctorRepository);
        this.restClinicDoctorMockMvc = MockMvcBuilders.standaloneSetup(clinicDoctorResource)
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
    public static ClinicDoctor createEntity(EntityManager em) {
        ClinicDoctor clinicDoctor = new ClinicDoctor();
        return clinicDoctor;
    }

    @Before
    public void initTest() {
        clinicDoctor = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinicDoctor() throws Exception {
        int databaseSizeBeforeCreate = clinicDoctorRepository.findAll().size();

        // Create the ClinicDoctor
        restClinicDoctorMockMvc.perform(post("/api/clinic-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicDoctor)))
            .andExpect(status().isCreated());

        // Validate the ClinicDoctor in the database
        List<ClinicDoctor> clinicDoctorList = clinicDoctorRepository.findAll();
        assertThat(clinicDoctorList).hasSize(databaseSizeBeforeCreate + 1);
        ClinicDoctor testClinicDoctor = clinicDoctorList.get(clinicDoctorList.size() - 1);
    }

    @Test
    @Transactional
    public void createClinicDoctorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clinicDoctorRepository.findAll().size();

        // Create the ClinicDoctor with an existing ID
        clinicDoctor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicDoctorMockMvc.perform(post("/api/clinic-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicDoctor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClinicDoctor> clinicDoctorList = clinicDoctorRepository.findAll();
        assertThat(clinicDoctorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClinicDoctors() throws Exception {
        // Initialize the database
        clinicDoctorRepository.saveAndFlush(clinicDoctor);

        // Get all the clinicDoctorList
        restClinicDoctorMockMvc.perform(get("/api/clinic-doctors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinicDoctor.getId().intValue())));
    }

    @Test
    @Transactional
    public void getClinicDoctor() throws Exception {
        // Initialize the database
        clinicDoctorRepository.saveAndFlush(clinicDoctor);

        // Get the clinicDoctor
        restClinicDoctorMockMvc.perform(get("/api/clinic-doctors/{id}", clinicDoctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clinicDoctor.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClinicDoctor() throws Exception {
        // Get the clinicDoctor
        restClinicDoctorMockMvc.perform(get("/api/clinic-doctors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinicDoctor() throws Exception {
        // Initialize the database
        clinicDoctorRepository.saveAndFlush(clinicDoctor);
        int databaseSizeBeforeUpdate = clinicDoctorRepository.findAll().size();

        // Update the clinicDoctor
        ClinicDoctor updatedClinicDoctor = clinicDoctorRepository.findOne(clinicDoctor.getId());

        restClinicDoctorMockMvc.perform(put("/api/clinic-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClinicDoctor)))
            .andExpect(status().isOk());

        // Validate the ClinicDoctor in the database
        List<ClinicDoctor> clinicDoctorList = clinicDoctorRepository.findAll();
        assertThat(clinicDoctorList).hasSize(databaseSizeBeforeUpdate);
        ClinicDoctor testClinicDoctor = clinicDoctorList.get(clinicDoctorList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingClinicDoctor() throws Exception {
        int databaseSizeBeforeUpdate = clinicDoctorRepository.findAll().size();

        // Create the ClinicDoctor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClinicDoctorMockMvc.perform(put("/api/clinic-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicDoctor)))
            .andExpect(status().isCreated());

        // Validate the ClinicDoctor in the database
        List<ClinicDoctor> clinicDoctorList = clinicDoctorRepository.findAll();
        assertThat(clinicDoctorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClinicDoctor() throws Exception {
        // Initialize the database
        clinicDoctorRepository.saveAndFlush(clinicDoctor);
        int databaseSizeBeforeDelete = clinicDoctorRepository.findAll().size();

        // Get the clinicDoctor
        restClinicDoctorMockMvc.perform(delete("/api/clinic-doctors/{id}", clinicDoctor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClinicDoctor> clinicDoctorList = clinicDoctorRepository.findAll();
        assertThat(clinicDoctorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClinicDoctor.class);
        ClinicDoctor clinicDoctor1 = new ClinicDoctor();
        clinicDoctor1.setId(1L);
        ClinicDoctor clinicDoctor2 = new ClinicDoctor();
        clinicDoctor2.setId(clinicDoctor1.getId());
        assertThat(clinicDoctor1).isEqualTo(clinicDoctor2);
        clinicDoctor2.setId(2L);
        assertThat(clinicDoctor1).isNotEqualTo(clinicDoctor2);
        clinicDoctor1.setId(null);
        assertThat(clinicDoctor1).isNotEqualTo(clinicDoctor2);
    }
}
