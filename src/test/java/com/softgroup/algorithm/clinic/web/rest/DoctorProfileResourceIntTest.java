package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.DoctorProfile;
import com.softgroup.algorithm.clinic.repository.DoctorProfileRepository;
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
 * Test class for the DoctorProfileResource REST controller.
 *
 * @see DoctorProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class DoctorProfileResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_PASS_HASH = 1L;
    private static final Long UPDATED_PASS_HASH = 2L;

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDoctorProfileMockMvc;

    private DoctorProfile doctorProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DoctorProfileResource doctorProfileResource = new DoctorProfileResource(doctorProfileRepository);
        this.restDoctorProfileMockMvc = MockMvcBuilders.standaloneSetup(doctorProfileResource)
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
    public static DoctorProfile createEntity(EntityManager em) {
        DoctorProfile doctorProfile = new DoctorProfile()
            .email(DEFAULT_EMAIL)
            .passHash(DEFAULT_PASS_HASH);
        return doctorProfile;
    }

    @Before
    public void initTest() {
        doctorProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorProfile() throws Exception {
        int databaseSizeBeforeCreate = doctorProfileRepository.findAll().size();

        // Create the DoctorProfile
        restDoctorProfileMockMvc.perform(post("/api/doctor-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorProfile)))
            .andExpect(status().isCreated());

        // Validate the DoctorProfile in the database
        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();
        assertThat(doctorProfileList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorProfile testDoctorProfile = doctorProfileList.get(doctorProfileList.size() - 1);
        assertThat(testDoctorProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDoctorProfile.getPassHash()).isEqualTo(DEFAULT_PASS_HASH);
    }

    @Test
    @Transactional
    public void createDoctorProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorProfileRepository.findAll().size();

        // Create the DoctorProfile with an existing ID
        doctorProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorProfileMockMvc.perform(post("/api/doctor-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorProfile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();
        assertThat(doctorProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoctorProfiles() throws Exception {
        // Initialize the database
        doctorProfileRepository.saveAndFlush(doctorProfile);

        // Get all the doctorProfileList
        restDoctorProfileMockMvc.perform(get("/api/doctor-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].passHash").value(hasItem(DEFAULT_PASS_HASH.intValue())));
    }

    @Test
    @Transactional
    public void getDoctorProfile() throws Exception {
        // Initialize the database
        doctorProfileRepository.saveAndFlush(doctorProfile);

        // Get the doctorProfile
        restDoctorProfileMockMvc.perform(get("/api/doctor-profiles/{id}", doctorProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctorProfile.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.passHash").value(DEFAULT_PASS_HASH.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDoctorProfile() throws Exception {
        // Get the doctorProfile
        restDoctorProfileMockMvc.perform(get("/api/doctor-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorProfile() throws Exception {
        // Initialize the database
        doctorProfileRepository.saveAndFlush(doctorProfile);
        int databaseSizeBeforeUpdate = doctorProfileRepository.findAll().size();

        // Update the doctorProfile
        DoctorProfile updatedDoctorProfile = doctorProfileRepository.findOne(doctorProfile.getId());
        updatedDoctorProfile
            .email(UPDATED_EMAIL)
            .passHash(UPDATED_PASS_HASH);

        restDoctorProfileMockMvc.perform(put("/api/doctor-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctorProfile)))
            .andExpect(status().isOk());

        // Validate the DoctorProfile in the database
        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();
        assertThat(doctorProfileList).hasSize(databaseSizeBeforeUpdate);
        DoctorProfile testDoctorProfile = doctorProfileList.get(doctorProfileList.size() - 1);
        assertThat(testDoctorProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDoctorProfile.getPassHash()).isEqualTo(UPDATED_PASS_HASH);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorProfile() throws Exception {
        int databaseSizeBeforeUpdate = doctorProfileRepository.findAll().size();

        // Create the DoctorProfile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoctorProfileMockMvc.perform(put("/api/doctor-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorProfile)))
            .andExpect(status().isCreated());

        // Validate the DoctorProfile in the database
        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();
        assertThat(doctorProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoctorProfile() throws Exception {
        // Initialize the database
        doctorProfileRepository.saveAndFlush(doctorProfile);
        int databaseSizeBeforeDelete = doctorProfileRepository.findAll().size();

        // Get the doctorProfile
        restDoctorProfileMockMvc.perform(delete("/api/doctor-profiles/{id}", doctorProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DoctorProfile> doctorProfileList = doctorProfileRepository.findAll();
        assertThat(doctorProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorProfile.class);
        DoctorProfile doctorProfile1 = new DoctorProfile();
        doctorProfile1.setId(1L);
        DoctorProfile doctorProfile2 = new DoctorProfile();
        doctorProfile2.setId(doctorProfile1.getId());
        assertThat(doctorProfile1).isEqualTo(doctorProfile2);
        doctorProfile2.setId(2L);
        assertThat(doctorProfile1).isNotEqualTo(doctorProfile2);
        doctorProfile1.setId(null);
        assertThat(doctorProfile1).isNotEqualTo(doctorProfile2);
    }
}
