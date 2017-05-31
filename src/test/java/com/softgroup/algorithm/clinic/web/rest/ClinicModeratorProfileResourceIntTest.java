package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.ClinicModeratorProfile;
import com.softgroup.algorithm.clinic.repository.ClinicModeratorProfileRepository;
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
 * Test class for the ClinicModeratorProfileResource REST controller.
 *
 * @see ClinicModeratorProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class ClinicModeratorProfileResourceIntTest {

    private static final Long DEFAULT_PASS_HASH = 1L;
    private static final Long UPDATED_PASS_HASH = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private ClinicModeratorProfileRepository clinicModeratorProfileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClinicModeratorProfileMockMvc;

    private ClinicModeratorProfile clinicModeratorProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClinicModeratorProfileResource clinicModeratorProfileResource = new ClinicModeratorProfileResource(clinicModeratorProfileRepository);
        this.restClinicModeratorProfileMockMvc = MockMvcBuilders.standaloneSetup(clinicModeratorProfileResource)
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
    public static ClinicModeratorProfile createEntity(EntityManager em) {
        ClinicModeratorProfile clinicModeratorProfile = new ClinicModeratorProfile()
            .passHash(DEFAULT_PASS_HASH)
            .email(DEFAULT_EMAIL);
        return clinicModeratorProfile;
    }

    @Before
    public void initTest() {
        clinicModeratorProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinicModeratorProfile() throws Exception {
        int databaseSizeBeforeCreate = clinicModeratorProfileRepository.findAll().size();

        // Create the ClinicModeratorProfile
        restClinicModeratorProfileMockMvc.perform(post("/api/clinic-moderator-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicModeratorProfile)))
            .andExpect(status().isCreated());

        // Validate the ClinicModeratorProfile in the database
        List<ClinicModeratorProfile> clinicModeratorProfileList = clinicModeratorProfileRepository.findAll();
        assertThat(clinicModeratorProfileList).hasSize(databaseSizeBeforeCreate + 1);
        ClinicModeratorProfile testClinicModeratorProfile = clinicModeratorProfileList.get(clinicModeratorProfileList.size() - 1);
        assertThat(testClinicModeratorProfile.getPassHash()).isEqualTo(DEFAULT_PASS_HASH);
        assertThat(testClinicModeratorProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createClinicModeratorProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clinicModeratorProfileRepository.findAll().size();

        // Create the ClinicModeratorProfile with an existing ID
        clinicModeratorProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicModeratorProfileMockMvc.perform(post("/api/clinic-moderator-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicModeratorProfile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClinicModeratorProfile> clinicModeratorProfileList = clinicModeratorProfileRepository.findAll();
        assertThat(clinicModeratorProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClinicModeratorProfiles() throws Exception {
        // Initialize the database
        clinicModeratorProfileRepository.saveAndFlush(clinicModeratorProfile);

        // Get all the clinicModeratorProfileList
        restClinicModeratorProfileMockMvc.perform(get("/api/clinic-moderator-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinicModeratorProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].passHash").value(hasItem(DEFAULT_PASS_HASH.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getClinicModeratorProfile() throws Exception {
        // Initialize the database
        clinicModeratorProfileRepository.saveAndFlush(clinicModeratorProfile);

        // Get the clinicModeratorProfile
        restClinicModeratorProfileMockMvc.perform(get("/api/clinic-moderator-profiles/{id}", clinicModeratorProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clinicModeratorProfile.getId().intValue()))
            .andExpect(jsonPath("$.passHash").value(DEFAULT_PASS_HASH.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClinicModeratorProfile() throws Exception {
        // Get the clinicModeratorProfile
        restClinicModeratorProfileMockMvc.perform(get("/api/clinic-moderator-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinicModeratorProfile() throws Exception {
        // Initialize the database
        clinicModeratorProfileRepository.saveAndFlush(clinicModeratorProfile);
        int databaseSizeBeforeUpdate = clinicModeratorProfileRepository.findAll().size();

        // Update the clinicModeratorProfile
        ClinicModeratorProfile updatedClinicModeratorProfile = clinicModeratorProfileRepository.findOne(clinicModeratorProfile.getId());
        updatedClinicModeratorProfile
            .passHash(UPDATED_PASS_HASH)
            .email(UPDATED_EMAIL);

        restClinicModeratorProfileMockMvc.perform(put("/api/clinic-moderator-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClinicModeratorProfile)))
            .andExpect(status().isOk());

        // Validate the ClinicModeratorProfile in the database
        List<ClinicModeratorProfile> clinicModeratorProfileList = clinicModeratorProfileRepository.findAll();
        assertThat(clinicModeratorProfileList).hasSize(databaseSizeBeforeUpdate);
        ClinicModeratorProfile testClinicModeratorProfile = clinicModeratorProfileList.get(clinicModeratorProfileList.size() - 1);
        assertThat(testClinicModeratorProfile.getPassHash()).isEqualTo(UPDATED_PASS_HASH);
        assertThat(testClinicModeratorProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingClinicModeratorProfile() throws Exception {
        int databaseSizeBeforeUpdate = clinicModeratorProfileRepository.findAll().size();

        // Create the ClinicModeratorProfile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClinicModeratorProfileMockMvc.perform(put("/api/clinic-moderator-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicModeratorProfile)))
            .andExpect(status().isCreated());

        // Validate the ClinicModeratorProfile in the database
        List<ClinicModeratorProfile> clinicModeratorProfileList = clinicModeratorProfileRepository.findAll();
        assertThat(clinicModeratorProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClinicModeratorProfile() throws Exception {
        // Initialize the database
        clinicModeratorProfileRepository.saveAndFlush(clinicModeratorProfile);
        int databaseSizeBeforeDelete = clinicModeratorProfileRepository.findAll().size();

        // Get the clinicModeratorProfile
        restClinicModeratorProfileMockMvc.perform(delete("/api/clinic-moderator-profiles/{id}", clinicModeratorProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClinicModeratorProfile> clinicModeratorProfileList = clinicModeratorProfileRepository.findAll();
        assertThat(clinicModeratorProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClinicModeratorProfile.class);
        ClinicModeratorProfile clinicModeratorProfile1 = new ClinicModeratorProfile();
        clinicModeratorProfile1.setId(1L);
        ClinicModeratorProfile clinicModeratorProfile2 = new ClinicModeratorProfile();
        clinicModeratorProfile2.setId(clinicModeratorProfile1.getId());
        assertThat(clinicModeratorProfile1).isEqualTo(clinicModeratorProfile2);
        clinicModeratorProfile2.setId(2L);
        assertThat(clinicModeratorProfile1).isNotEqualTo(clinicModeratorProfile2);
        clinicModeratorProfile1.setId(null);
        assertThat(clinicModeratorProfile1).isNotEqualTo(clinicModeratorProfile2);
    }
}
