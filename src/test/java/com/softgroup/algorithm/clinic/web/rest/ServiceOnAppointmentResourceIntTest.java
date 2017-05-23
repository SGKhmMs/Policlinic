package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.ServiceOnAppointment;
import com.softgroup.algorithm.clinic.repository.ServiceOnAppointmentRepository;
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
 * Test class for the ServiceOnAppointmentResource REST controller.
 *
 * @see ServiceOnAppointmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class ServiceOnAppointmentResourceIntTest {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private ServiceOnAppointmentRepository serviceOnAppointmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceOnAppointmentMockMvc;

    private ServiceOnAppointment serviceOnAppointment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceOnAppointmentResource serviceOnAppointmentResource = new ServiceOnAppointmentResource(serviceOnAppointmentRepository);
        this.restServiceOnAppointmentMockMvc = MockMvcBuilders.standaloneSetup(serviceOnAppointmentResource)
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
    public static ServiceOnAppointment createEntity(EntityManager em) {
        ServiceOnAppointment serviceOnAppointment = new ServiceOnAppointment()
            .price(DEFAULT_PRICE);
        return serviceOnAppointment;
    }

    @Before
    public void initTest() {
        serviceOnAppointment = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOnAppointment() throws Exception {
        int databaseSizeBeforeCreate = serviceOnAppointmentRepository.findAll().size();

        // Create the ServiceOnAppointment
        restServiceOnAppointmentMockMvc.perform(post("/api/service-on-appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOnAppointment)))
            .andExpect(status().isCreated());

        // Validate the ServiceOnAppointment in the database
        List<ServiceOnAppointment> serviceOnAppointmentList = serviceOnAppointmentRepository.findAll();
        assertThat(serviceOnAppointmentList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOnAppointment testServiceOnAppointment = serviceOnAppointmentList.get(serviceOnAppointmentList.size() - 1);
        assertThat(testServiceOnAppointment.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createServiceOnAppointmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOnAppointmentRepository.findAll().size();

        // Create the ServiceOnAppointment with an existing ID
        serviceOnAppointment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOnAppointmentMockMvc.perform(post("/api/service-on-appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOnAppointment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ServiceOnAppointment> serviceOnAppointmentList = serviceOnAppointmentRepository.findAll();
        assertThat(serviceOnAppointmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllServiceOnAppointments() throws Exception {
        // Initialize the database
        serviceOnAppointmentRepository.saveAndFlush(serviceOnAppointment);

        // Get all the serviceOnAppointmentList
        restServiceOnAppointmentMockMvc.perform(get("/api/service-on-appointments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOnAppointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getServiceOnAppointment() throws Exception {
        // Initialize the database
        serviceOnAppointmentRepository.saveAndFlush(serviceOnAppointment);

        // Get the serviceOnAppointment
        restServiceOnAppointmentMockMvc.perform(get("/api/service-on-appointments/{id}", serviceOnAppointment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOnAppointment.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceOnAppointment() throws Exception {
        // Get the serviceOnAppointment
        restServiceOnAppointmentMockMvc.perform(get("/api/service-on-appointments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOnAppointment() throws Exception {
        // Initialize the database
        serviceOnAppointmentRepository.saveAndFlush(serviceOnAppointment);
        int databaseSizeBeforeUpdate = serviceOnAppointmentRepository.findAll().size();

        // Update the serviceOnAppointment
        ServiceOnAppointment updatedServiceOnAppointment = serviceOnAppointmentRepository.findOne(serviceOnAppointment.getId());
        updatedServiceOnAppointment
            .price(UPDATED_PRICE);

        restServiceOnAppointmentMockMvc.perform(put("/api/service-on-appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceOnAppointment)))
            .andExpect(status().isOk());

        // Validate the ServiceOnAppointment in the database
        List<ServiceOnAppointment> serviceOnAppointmentList = serviceOnAppointmentRepository.findAll();
        assertThat(serviceOnAppointmentList).hasSize(databaseSizeBeforeUpdate);
        ServiceOnAppointment testServiceOnAppointment = serviceOnAppointmentList.get(serviceOnAppointmentList.size() - 1);
        assertThat(testServiceOnAppointment.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOnAppointment() throws Exception {
        int databaseSizeBeforeUpdate = serviceOnAppointmentRepository.findAll().size();

        // Create the ServiceOnAppointment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceOnAppointmentMockMvc.perform(put("/api/service-on-appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOnAppointment)))
            .andExpect(status().isCreated());

        // Validate the ServiceOnAppointment in the database
        List<ServiceOnAppointment> serviceOnAppointmentList = serviceOnAppointmentRepository.findAll();
        assertThat(serviceOnAppointmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServiceOnAppointment() throws Exception {
        // Initialize the database
        serviceOnAppointmentRepository.saveAndFlush(serviceOnAppointment);
        int databaseSizeBeforeDelete = serviceOnAppointmentRepository.findAll().size();

        // Get the serviceOnAppointment
        restServiceOnAppointmentMockMvc.perform(delete("/api/service-on-appointments/{id}", serviceOnAppointment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceOnAppointment> serviceOnAppointmentList = serviceOnAppointmentRepository.findAll();
        assertThat(serviceOnAppointmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOnAppointment.class);
        ServiceOnAppointment serviceOnAppointment1 = new ServiceOnAppointment();
        serviceOnAppointment1.setId(1L);
        ServiceOnAppointment serviceOnAppointment2 = new ServiceOnAppointment();
        serviceOnAppointment2.setId(serviceOnAppointment1.getId());
        assertThat(serviceOnAppointment1).isEqualTo(serviceOnAppointment2);
        serviceOnAppointment2.setId(2L);
        assertThat(serviceOnAppointment1).isNotEqualTo(serviceOnAppointment2);
        serviceOnAppointment1.setId(null);
        assertThat(serviceOnAppointment1).isNotEqualTo(serviceOnAppointment2);
    }
}
