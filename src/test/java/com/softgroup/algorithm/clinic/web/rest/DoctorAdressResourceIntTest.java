package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.DoctorAdress;
import com.softgroup.algorithm.clinic.repository.DoctorAdressRepository;
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
 * Test class for the DoctorAdressResource REST controller.
 *
 * @see DoctorAdressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class DoctorAdressResourceIntTest {

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    @Autowired
    private DoctorAdressRepository doctorAdressRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDoctorAdressMockMvc;

    private DoctorAdress doctorAdress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DoctorAdressResource doctorAdressResource = new DoctorAdressResource(doctorAdressRepository);
        this.restDoctorAdressMockMvc = MockMvcBuilders.standaloneSetup(doctorAdressResource)
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
    public static DoctorAdress createEntity(EntityManager em) {
        DoctorAdress doctorAdress = new DoctorAdress()
            .adress(DEFAULT_ADRESS);
        return doctorAdress;
    }

    @Before
    public void initTest() {
        doctorAdress = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorAdress() throws Exception {
        int databaseSizeBeforeCreate = doctorAdressRepository.findAll().size();

        // Create the DoctorAdress
        restDoctorAdressMockMvc.perform(post("/api/doctor-adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorAdress)))
            .andExpect(status().isCreated());

        // Validate the DoctorAdress in the database
        List<DoctorAdress> doctorAdressList = doctorAdressRepository.findAll();
        assertThat(doctorAdressList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorAdress testDoctorAdress = doctorAdressList.get(doctorAdressList.size() - 1);
        assertThat(testDoctorAdress.getAdress()).isEqualTo(DEFAULT_ADRESS);
    }

    @Test
    @Transactional
    public void createDoctorAdressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorAdressRepository.findAll().size();

        // Create the DoctorAdress with an existing ID
        doctorAdress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorAdressMockMvc.perform(post("/api/doctor-adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorAdress)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DoctorAdress> doctorAdressList = doctorAdressRepository.findAll();
        assertThat(doctorAdressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoctorAdresses() throws Exception {
        // Initialize the database
        doctorAdressRepository.saveAndFlush(doctorAdress);

        // Get all the doctorAdressList
        restDoctorAdressMockMvc.perform(get("/api/doctor-adresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorAdress.getId().intValue())))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS.toString())));
    }

    @Test
    @Transactional
    public void getDoctorAdress() throws Exception {
        // Initialize the database
        doctorAdressRepository.saveAndFlush(doctorAdress);

        // Get the doctorAdress
        restDoctorAdressMockMvc.perform(get("/api/doctor-adresses/{id}", doctorAdress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctorAdress.getId().intValue()))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoctorAdress() throws Exception {
        // Get the doctorAdress
        restDoctorAdressMockMvc.perform(get("/api/doctor-adresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorAdress() throws Exception {
        // Initialize the database
        doctorAdressRepository.saveAndFlush(doctorAdress);
        int databaseSizeBeforeUpdate = doctorAdressRepository.findAll().size();

        // Update the doctorAdress
        DoctorAdress updatedDoctorAdress = doctorAdressRepository.findOne(doctorAdress.getId());
        updatedDoctorAdress
            .adress(UPDATED_ADRESS);

        restDoctorAdressMockMvc.perform(put("/api/doctor-adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctorAdress)))
            .andExpect(status().isOk());

        // Validate the DoctorAdress in the database
        List<DoctorAdress> doctorAdressList = doctorAdressRepository.findAll();
        assertThat(doctorAdressList).hasSize(databaseSizeBeforeUpdate);
        DoctorAdress testDoctorAdress = doctorAdressList.get(doctorAdressList.size() - 1);
        assertThat(testDoctorAdress.getAdress()).isEqualTo(UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorAdress() throws Exception {
        int databaseSizeBeforeUpdate = doctorAdressRepository.findAll().size();

        // Create the DoctorAdress

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoctorAdressMockMvc.perform(put("/api/doctor-adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorAdress)))
            .andExpect(status().isCreated());

        // Validate the DoctorAdress in the database
        List<DoctorAdress> doctorAdressList = doctorAdressRepository.findAll();
        assertThat(doctorAdressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoctorAdress() throws Exception {
        // Initialize the database
        doctorAdressRepository.saveAndFlush(doctorAdress);
        int databaseSizeBeforeDelete = doctorAdressRepository.findAll().size();

        // Get the doctorAdress
        restDoctorAdressMockMvc.perform(delete("/api/doctor-adresses/{id}", doctorAdress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DoctorAdress> doctorAdressList = doctorAdressRepository.findAll();
        assertThat(doctorAdressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorAdress.class);
        DoctorAdress doctorAdress1 = new DoctorAdress();
        doctorAdress1.setId(1L);
        DoctorAdress doctorAdress2 = new DoctorAdress();
        doctorAdress2.setId(doctorAdress1.getId());
        assertThat(doctorAdress1).isEqualTo(doctorAdress2);
        doctorAdress2.setId(2L);
        assertThat(doctorAdress1).isNotEqualTo(doctorAdress2);
        doctorAdress1.setId(null);
        assertThat(doctorAdress1).isNotEqualTo(doctorAdress2);
    }
}
