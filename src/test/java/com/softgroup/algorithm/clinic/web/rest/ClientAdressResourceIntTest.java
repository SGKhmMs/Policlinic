package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.ClientAdress;
import com.softgroup.algorithm.clinic.repository.ClientAdressRepository;
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
 * Test class for the ClientAdressResource REST controller.
 *
 * @see ClientAdressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class ClientAdressResourceIntTest {

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    @Autowired
    private ClientAdressRepository clientAdressRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientAdressMockMvc;

    private ClientAdress clientAdress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientAdressResource clientAdressResource = new ClientAdressResource(clientAdressRepository);
        this.restClientAdressMockMvc = MockMvcBuilders.standaloneSetup(clientAdressResource)
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
    public static ClientAdress createEntity(EntityManager em) {
        ClientAdress clientAdress = new ClientAdress()
            .adress(DEFAULT_ADRESS);
        return clientAdress;
    }

    @Before
    public void initTest() {
        clientAdress = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientAdress() throws Exception {
        int databaseSizeBeforeCreate = clientAdressRepository.findAll().size();

        // Create the ClientAdress
        restClientAdressMockMvc.perform(post("/api/client-adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientAdress)))
            .andExpect(status().isCreated());

        // Validate the ClientAdress in the database
        List<ClientAdress> clientAdressList = clientAdressRepository.findAll();
        assertThat(clientAdressList).hasSize(databaseSizeBeforeCreate + 1);
        ClientAdress testClientAdress = clientAdressList.get(clientAdressList.size() - 1);
        assertThat(testClientAdress.getAdress()).isEqualTo(DEFAULT_ADRESS);
    }

    @Test
    @Transactional
    public void createClientAdressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientAdressRepository.findAll().size();

        // Create the ClientAdress with an existing ID
        clientAdress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientAdressMockMvc.perform(post("/api/client-adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientAdress)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientAdress> clientAdressList = clientAdressRepository.findAll();
        assertThat(clientAdressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientAdresses() throws Exception {
        // Initialize the database
        clientAdressRepository.saveAndFlush(clientAdress);

        // Get all the clientAdressList
        restClientAdressMockMvc.perform(get("/api/client-adresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientAdress.getId().intValue())))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS.toString())));
    }

    @Test
    @Transactional
    public void getClientAdress() throws Exception {
        // Initialize the database
        clientAdressRepository.saveAndFlush(clientAdress);

        // Get the clientAdress
        restClientAdressMockMvc.perform(get("/api/client-adresses/{id}", clientAdress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientAdress.getId().intValue()))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClientAdress() throws Exception {
        // Get the clientAdress
        restClientAdressMockMvc.perform(get("/api/client-adresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientAdress() throws Exception {
        // Initialize the database
        clientAdressRepository.saveAndFlush(clientAdress);
        int databaseSizeBeforeUpdate = clientAdressRepository.findAll().size();

        // Update the clientAdress
        ClientAdress updatedClientAdress = clientAdressRepository.findOne(clientAdress.getId());
        updatedClientAdress
            .adress(UPDATED_ADRESS);

        restClientAdressMockMvc.perform(put("/api/client-adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientAdress)))
            .andExpect(status().isOk());

        // Validate the ClientAdress in the database
        List<ClientAdress> clientAdressList = clientAdressRepository.findAll();
        assertThat(clientAdressList).hasSize(databaseSizeBeforeUpdate);
        ClientAdress testClientAdress = clientAdressList.get(clientAdressList.size() - 1);
        assertThat(testClientAdress.getAdress()).isEqualTo(UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingClientAdress() throws Exception {
        int databaseSizeBeforeUpdate = clientAdressRepository.findAll().size();

        // Create the ClientAdress

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientAdressMockMvc.perform(put("/api/client-adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientAdress)))
            .andExpect(status().isCreated());

        // Validate the ClientAdress in the database
        List<ClientAdress> clientAdressList = clientAdressRepository.findAll();
        assertThat(clientAdressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientAdress() throws Exception {
        // Initialize the database
        clientAdressRepository.saveAndFlush(clientAdress);
        int databaseSizeBeforeDelete = clientAdressRepository.findAll().size();

        // Get the clientAdress
        restClientAdressMockMvc.perform(delete("/api/client-adresses/{id}", clientAdress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientAdress> clientAdressList = clientAdressRepository.findAll();
        assertThat(clientAdressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientAdress.class);
        ClientAdress clientAdress1 = new ClientAdress();
        clientAdress1.setId(1L);
        ClientAdress clientAdress2 = new ClientAdress();
        clientAdress2.setId(clientAdress1.getId());
        assertThat(clientAdress1).isEqualTo(clientAdress2);
        clientAdress2.setId(2L);
        assertThat(clientAdress1).isNotEqualTo(clientAdress2);
        clientAdress1.setId(null);
        assertThat(clientAdress1).isNotEqualTo(clientAdress2);
    }
}
