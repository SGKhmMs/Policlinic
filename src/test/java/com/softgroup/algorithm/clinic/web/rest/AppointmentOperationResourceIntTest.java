package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.AppointmentOperation;
import com.softgroup.algorithm.clinic.repository.AppointmentOperationRepository;
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
 * Test class for the AppointmentOperationResource REST controller.
 *
 * @see AppointmentOperationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class AppointmentOperationResourceIntTest {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private AppointmentOperationRepository appointmentOperationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppointmentOperationMockMvc;

    private AppointmentOperation appointmentOperation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppointmentOperationResource appointmentOperationResource = new AppointmentOperationResource(appointmentOperationRepository);
        this.restAppointmentOperationMockMvc = MockMvcBuilders.standaloneSetup(appointmentOperationResource)
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
    public static AppointmentOperation createEntity(EntityManager em) {
        AppointmentOperation appointmentOperation = new AppointmentOperation()
            .price(DEFAULT_PRICE);
        return appointmentOperation;
    }

    @Before
    public void initTest() {
        appointmentOperation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppointmentOperation() throws Exception {
        int databaseSizeBeforeCreate = appointmentOperationRepository.findAll().size();

        // Create the AppointmentOperation
        restAppointmentOperationMockMvc.perform(post("/api/appointment-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentOperation)))
            .andExpect(status().isCreated());

        // Validate the AppointmentOperation in the database
        List<AppointmentOperation> appointmentOperationList = appointmentOperationRepository.findAll();
        assertThat(appointmentOperationList).hasSize(databaseSizeBeforeCreate + 1);
        AppointmentOperation testAppointmentOperation = appointmentOperationList.get(appointmentOperationList.size() - 1);
        assertThat(testAppointmentOperation.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createAppointmentOperationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appointmentOperationRepository.findAll().size();

        // Create the AppointmentOperation with an existing ID
        appointmentOperation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppointmentOperationMockMvc.perform(post("/api/appointment-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentOperation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AppointmentOperation> appointmentOperationList = appointmentOperationRepository.findAll();
        assertThat(appointmentOperationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAppointmentOperations() throws Exception {
        // Initialize the database
        appointmentOperationRepository.saveAndFlush(appointmentOperation);

        // Get all the appointmentOperationList
        restAppointmentOperationMockMvc.perform(get("/api/appointment-operations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointmentOperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAppointmentOperation() throws Exception {
        // Initialize the database
        appointmentOperationRepository.saveAndFlush(appointmentOperation);

        // Get the appointmentOperation
        restAppointmentOperationMockMvc.perform(get("/api/appointment-operations/{id}", appointmentOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appointmentOperation.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAppointmentOperation() throws Exception {
        // Get the appointmentOperation
        restAppointmentOperationMockMvc.perform(get("/api/appointment-operations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppointmentOperation() throws Exception {
        // Initialize the database
        appointmentOperationRepository.saveAndFlush(appointmentOperation);
        int databaseSizeBeforeUpdate = appointmentOperationRepository.findAll().size();

        // Update the appointmentOperation
        AppointmentOperation updatedAppointmentOperation = appointmentOperationRepository.findOne(appointmentOperation.getId());
        updatedAppointmentOperation
            .price(UPDATED_PRICE);

        restAppointmentOperationMockMvc.perform(put("/api/appointment-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppointmentOperation)))
            .andExpect(status().isOk());

        // Validate the AppointmentOperation in the database
        List<AppointmentOperation> appointmentOperationList = appointmentOperationRepository.findAll();
        assertThat(appointmentOperationList).hasSize(databaseSizeBeforeUpdate);
        AppointmentOperation testAppointmentOperation = appointmentOperationList.get(appointmentOperationList.size() - 1);
        assertThat(testAppointmentOperation.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingAppointmentOperation() throws Exception {
        int databaseSizeBeforeUpdate = appointmentOperationRepository.findAll().size();

        // Create the AppointmentOperation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppointmentOperationMockMvc.perform(put("/api/appointment-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentOperation)))
            .andExpect(status().isCreated());

        // Validate the AppointmentOperation in the database
        List<AppointmentOperation> appointmentOperationList = appointmentOperationRepository.findAll();
        assertThat(appointmentOperationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppointmentOperation() throws Exception {
        // Initialize the database
        appointmentOperationRepository.saveAndFlush(appointmentOperation);
        int databaseSizeBeforeDelete = appointmentOperationRepository.findAll().size();

        // Get the appointmentOperation
        restAppointmentOperationMockMvc.perform(delete("/api/appointment-operations/{id}", appointmentOperation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppointmentOperation> appointmentOperationList = appointmentOperationRepository.findAll();
        assertThat(appointmentOperationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppointmentOperation.class);
        AppointmentOperation appointmentOperation1 = new AppointmentOperation();
        appointmentOperation1.setId(1L);
        AppointmentOperation appointmentOperation2 = new AppointmentOperation();
        appointmentOperation2.setId(appointmentOperation1.getId());
        assertThat(appointmentOperation1).isEqualTo(appointmentOperation2);
        appointmentOperation2.setId(2L);
        assertThat(appointmentOperation1).isNotEqualTo(appointmentOperation2);
        appointmentOperation1.setId(null);
        assertThat(appointmentOperation1).isNotEqualTo(appointmentOperation2);
    }
}
