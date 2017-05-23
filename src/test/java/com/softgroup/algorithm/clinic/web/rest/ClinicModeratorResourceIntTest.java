package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.ClinicModerator;
import com.softgroup.algorithm.clinic.repository.ClinicModeratorRepository;
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
 * Test class for the ClinicModeratorResource REST controller.
 *
 * @see ClinicModeratorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class ClinicModeratorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Long DEFAULT_PASS_HASH = 1L;
    private static final Long UPDATED_PASS_HASH = 2L;

    @Autowired
    private ClinicModeratorRepository clinicModeratorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClinicModeratorMockMvc;

    private ClinicModerator clinicModerator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClinicModeratorResource clinicModeratorResource = new ClinicModeratorResource(clinicModeratorRepository);
        this.restClinicModeratorMockMvc = MockMvcBuilders.standaloneSetup(clinicModeratorResource)
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
    public static ClinicModerator createEntity(EntityManager em) {
        ClinicModerator clinicModerator = new ClinicModerator()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .lastname(DEFAULT_LASTNAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .passHash(DEFAULT_PASS_HASH);
        return clinicModerator;
    }

    @Before
    public void initTest() {
        clinicModerator = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinicModerator() throws Exception {
        int databaseSizeBeforeCreate = clinicModeratorRepository.findAll().size();

        // Create the ClinicModerator
        restClinicModeratorMockMvc.perform(post("/api/clinic-moderators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicModerator)))
            .andExpect(status().isCreated());

        // Validate the ClinicModerator in the database
        List<ClinicModerator> clinicModeratorList = clinicModeratorRepository.findAll();
        assertThat(clinicModeratorList).hasSize(databaseSizeBeforeCreate + 1);
        ClinicModerator testClinicModerator = clinicModeratorList.get(clinicModeratorList.size() - 1);
        assertThat(testClinicModerator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClinicModerator.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testClinicModerator.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testClinicModerator.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClinicModerator.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testClinicModerator.getPassHash()).isEqualTo(DEFAULT_PASS_HASH);
    }

    @Test
    @Transactional
    public void createClinicModeratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clinicModeratorRepository.findAll().size();

        // Create the ClinicModerator with an existing ID
        clinicModerator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicModeratorMockMvc.perform(post("/api/clinic-moderators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicModerator)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClinicModerator> clinicModeratorList = clinicModeratorRepository.findAll();
        assertThat(clinicModeratorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClinicModerators() throws Exception {
        // Initialize the database
        clinicModeratorRepository.saveAndFlush(clinicModerator);

        // Get all the clinicModeratorList
        restClinicModeratorMockMvc.perform(get("/api/clinic-moderators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinicModerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].passHash").value(hasItem(DEFAULT_PASS_HASH.intValue())));
    }

    @Test
    @Transactional
    public void getClinicModerator() throws Exception {
        // Initialize the database
        clinicModeratorRepository.saveAndFlush(clinicModerator);

        // Get the clinicModerator
        restClinicModeratorMockMvc.perform(get("/api/clinic-moderators/{id}", clinicModerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clinicModerator.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.passHash").value(DEFAULT_PASS_HASH.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClinicModerator() throws Exception {
        // Get the clinicModerator
        restClinicModeratorMockMvc.perform(get("/api/clinic-moderators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinicModerator() throws Exception {
        // Initialize the database
        clinicModeratorRepository.saveAndFlush(clinicModerator);
        int databaseSizeBeforeUpdate = clinicModeratorRepository.findAll().size();

        // Update the clinicModerator
        ClinicModerator updatedClinicModerator = clinicModeratorRepository.findOne(clinicModerator.getId());
        updatedClinicModerator
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .passHash(UPDATED_PASS_HASH);

        restClinicModeratorMockMvc.perform(put("/api/clinic-moderators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClinicModerator)))
            .andExpect(status().isOk());

        // Validate the ClinicModerator in the database
        List<ClinicModerator> clinicModeratorList = clinicModeratorRepository.findAll();
        assertThat(clinicModeratorList).hasSize(databaseSizeBeforeUpdate);
        ClinicModerator testClinicModerator = clinicModeratorList.get(clinicModeratorList.size() - 1);
        assertThat(testClinicModerator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClinicModerator.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testClinicModerator.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testClinicModerator.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClinicModerator.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClinicModerator.getPassHash()).isEqualTo(UPDATED_PASS_HASH);
    }

    @Test
    @Transactional
    public void updateNonExistingClinicModerator() throws Exception {
        int databaseSizeBeforeUpdate = clinicModeratorRepository.findAll().size();

        // Create the ClinicModerator

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClinicModeratorMockMvc.perform(put("/api/clinic-moderators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicModerator)))
            .andExpect(status().isCreated());

        // Validate the ClinicModerator in the database
        List<ClinicModerator> clinicModeratorList = clinicModeratorRepository.findAll();
        assertThat(clinicModeratorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClinicModerator() throws Exception {
        // Initialize the database
        clinicModeratorRepository.saveAndFlush(clinicModerator);
        int databaseSizeBeforeDelete = clinicModeratorRepository.findAll().size();

        // Get the clinicModerator
        restClinicModeratorMockMvc.perform(delete("/api/clinic-moderators/{id}", clinicModerator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClinicModerator> clinicModeratorList = clinicModeratorRepository.findAll();
        assertThat(clinicModeratorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClinicModerator.class);
        ClinicModerator clinicModerator1 = new ClinicModerator();
        clinicModerator1.setId(1L);
        ClinicModerator clinicModerator2 = new ClinicModerator();
        clinicModerator2.setId(clinicModerator1.getId());
        assertThat(clinicModerator1).isEqualTo(clinicModerator2);
        clinicModerator2.setId(2L);
        assertThat(clinicModerator1).isNotEqualTo(clinicModerator2);
        clinicModerator1.setId(null);
        assertThat(clinicModerator1).isNotEqualTo(clinicModerator2);
    }
}
