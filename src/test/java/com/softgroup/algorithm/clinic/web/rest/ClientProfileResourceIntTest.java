package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.ClientProfile;
import com.softgroup.algorithm.clinic.repository.ClientProfileRepository;
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
 * Test class for the ClientProfileResource REST controller.
 *
 * @see ClientProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class ClientProfileResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_PASS_HASH = 1L;
    private static final Long UPDATED_PASS_HASH = 2L;

    @Autowired
    private ClientProfileRepository clientProfileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientProfileMockMvc;

    private ClientProfile clientProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientProfileResource clientProfileResource = new ClientProfileResource(clientProfileRepository);
        this.restClientProfileMockMvc = MockMvcBuilders.standaloneSetup(clientProfileResource)
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
    public static ClientProfile createEntity(EntityManager em) {
        ClientProfile clientProfile = new ClientProfile()
            .email(DEFAULT_EMAIL)
            .passHash(DEFAULT_PASS_HASH);
        return clientProfile;
    }

    @Before
    public void initTest() {
        clientProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientProfile() throws Exception {
        int databaseSizeBeforeCreate = clientProfileRepository.findAll().size();

        // Create the ClientProfile
        restClientProfileMockMvc.perform(post("/api/client-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientProfile)))
            .andExpect(status().isCreated());

        // Validate the ClientProfile in the database
        List<ClientProfile> clientProfileList = clientProfileRepository.findAll();
        assertThat(clientProfileList).hasSize(databaseSizeBeforeCreate + 1);
        ClientProfile testClientProfile = clientProfileList.get(clientProfileList.size() - 1);
        assertThat(testClientProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClientProfile.getPassHash()).isEqualTo(DEFAULT_PASS_HASH);
    }

    @Test
    @Transactional
    public void createClientProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientProfileRepository.findAll().size();

        // Create the ClientProfile with an existing ID
        clientProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientProfileMockMvc.perform(post("/api/client-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientProfile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientProfile> clientProfileList = clientProfileRepository.findAll();
        assertThat(clientProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientProfiles() throws Exception {
        // Initialize the database
        clientProfileRepository.saveAndFlush(clientProfile);

        // Get all the clientProfileList
        restClientProfileMockMvc.perform(get("/api/client-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].passHash").value(hasItem(DEFAULT_PASS_HASH.intValue())));
    }

    @Test
    @Transactional
    public void getClientProfile() throws Exception {
        // Initialize the database
        clientProfileRepository.saveAndFlush(clientProfile);

        // Get the clientProfile
        restClientProfileMockMvc.perform(get("/api/client-profiles/{id}", clientProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientProfile.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.passHash").value(DEFAULT_PASS_HASH.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientProfile() throws Exception {
        // Get the clientProfile
        restClientProfileMockMvc.perform(get("/api/client-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientProfile() throws Exception {
        // Initialize the database
        clientProfileRepository.saveAndFlush(clientProfile);
        int databaseSizeBeforeUpdate = clientProfileRepository.findAll().size();

        // Update the clientProfile
        ClientProfile updatedClientProfile = clientProfileRepository.findOne(clientProfile.getId());
        updatedClientProfile
            .email(UPDATED_EMAIL)
            .passHash(UPDATED_PASS_HASH);

        restClientProfileMockMvc.perform(put("/api/client-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientProfile)))
            .andExpect(status().isOk());

        // Validate the ClientProfile in the database
        List<ClientProfile> clientProfileList = clientProfileRepository.findAll();
        assertThat(clientProfileList).hasSize(databaseSizeBeforeUpdate);
        ClientProfile testClientProfile = clientProfileList.get(clientProfileList.size() - 1);
        assertThat(testClientProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClientProfile.getPassHash()).isEqualTo(UPDATED_PASS_HASH);
    }

    @Test
    @Transactional
    public void updateNonExistingClientProfile() throws Exception {
        int databaseSizeBeforeUpdate = clientProfileRepository.findAll().size();

        // Create the ClientProfile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientProfileMockMvc.perform(put("/api/client-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientProfile)))
            .andExpect(status().isCreated());

        // Validate the ClientProfile in the database
        List<ClientProfile> clientProfileList = clientProfileRepository.findAll();
        assertThat(clientProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientProfile() throws Exception {
        // Initialize the database
        clientProfileRepository.saveAndFlush(clientProfile);
        int databaseSizeBeforeDelete = clientProfileRepository.findAll().size();

        // Get the clientProfile
        restClientProfileMockMvc.perform(delete("/api/client-profiles/{id}", clientProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientProfile> clientProfileList = clientProfileRepository.findAll();
        assertThat(clientProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientProfile.class);
        ClientProfile clientProfile1 = new ClientProfile();
        clientProfile1.setId(1L);
        ClientProfile clientProfile2 = new ClientProfile();
        clientProfile2.setId(clientProfile1.getId());
        assertThat(clientProfile1).isEqualTo(clientProfile2);
        clientProfile2.setId(2L);
        assertThat(clientProfile1).isNotEqualTo(clientProfile2);
        clientProfile1.setId(null);
        assertThat(clientProfile1).isNotEqualTo(clientProfile2);
    }
}
