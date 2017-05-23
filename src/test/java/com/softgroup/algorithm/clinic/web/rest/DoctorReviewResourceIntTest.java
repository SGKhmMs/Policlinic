package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.DoctorReview;
import com.softgroup.algorithm.clinic.repository.DoctorReviewRepository;
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
 * Test class for the DoctorReviewResource REST controller.
 *
 * @see DoctorReviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class DoctorReviewResourceIntTest {

    private static final String DEFAULT_REVIEW_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_REVIEW_TEXT = "BBBBBBBBBB";

    @Autowired
    private DoctorReviewRepository doctorReviewRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDoctorReviewMockMvc;

    private DoctorReview doctorReview;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DoctorReviewResource doctorReviewResource = new DoctorReviewResource(doctorReviewRepository);
        this.restDoctorReviewMockMvc = MockMvcBuilders.standaloneSetup(doctorReviewResource)
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
    public static DoctorReview createEntity(EntityManager em) {
        DoctorReview doctorReview = new DoctorReview()
            .reviewText(DEFAULT_REVIEW_TEXT);
        return doctorReview;
    }

    @Before
    public void initTest() {
        doctorReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorReview() throws Exception {
        int databaseSizeBeforeCreate = doctorReviewRepository.findAll().size();

        // Create the DoctorReview
        restDoctorReviewMockMvc.perform(post("/api/doctor-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorReview)))
            .andExpect(status().isCreated());

        // Validate the DoctorReview in the database
        List<DoctorReview> doctorReviewList = doctorReviewRepository.findAll();
        assertThat(doctorReviewList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorReview testDoctorReview = doctorReviewList.get(doctorReviewList.size() - 1);
        assertThat(testDoctorReview.getReviewText()).isEqualTo(DEFAULT_REVIEW_TEXT);
    }

    @Test
    @Transactional
    public void createDoctorReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorReviewRepository.findAll().size();

        // Create the DoctorReview with an existing ID
        doctorReview.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorReviewMockMvc.perform(post("/api/doctor-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorReview)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DoctorReview> doctorReviewList = doctorReviewRepository.findAll();
        assertThat(doctorReviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoctorReviews() throws Exception {
        // Initialize the database
        doctorReviewRepository.saveAndFlush(doctorReview);

        // Get all the doctorReviewList
        restDoctorReviewMockMvc.perform(get("/api/doctor-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewText").value(hasItem(DEFAULT_REVIEW_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getDoctorReview() throws Exception {
        // Initialize the database
        doctorReviewRepository.saveAndFlush(doctorReview);

        // Get the doctorReview
        restDoctorReviewMockMvc.perform(get("/api/doctor-reviews/{id}", doctorReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctorReview.getId().intValue()))
            .andExpect(jsonPath("$.reviewText").value(DEFAULT_REVIEW_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoctorReview() throws Exception {
        // Get the doctorReview
        restDoctorReviewMockMvc.perform(get("/api/doctor-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorReview() throws Exception {
        // Initialize the database
        doctorReviewRepository.saveAndFlush(doctorReview);
        int databaseSizeBeforeUpdate = doctorReviewRepository.findAll().size();

        // Update the doctorReview
        DoctorReview updatedDoctorReview = doctorReviewRepository.findOne(doctorReview.getId());
        updatedDoctorReview
            .reviewText(UPDATED_REVIEW_TEXT);

        restDoctorReviewMockMvc.perform(put("/api/doctor-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctorReview)))
            .andExpect(status().isOk());

        // Validate the DoctorReview in the database
        List<DoctorReview> doctorReviewList = doctorReviewRepository.findAll();
        assertThat(doctorReviewList).hasSize(databaseSizeBeforeUpdate);
        DoctorReview testDoctorReview = doctorReviewList.get(doctorReviewList.size() - 1);
        assertThat(testDoctorReview.getReviewText()).isEqualTo(UPDATED_REVIEW_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorReview() throws Exception {
        int databaseSizeBeforeUpdate = doctorReviewRepository.findAll().size();

        // Create the DoctorReview

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoctorReviewMockMvc.perform(put("/api/doctor-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorReview)))
            .andExpect(status().isCreated());

        // Validate the DoctorReview in the database
        List<DoctorReview> doctorReviewList = doctorReviewRepository.findAll();
        assertThat(doctorReviewList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoctorReview() throws Exception {
        // Initialize the database
        doctorReviewRepository.saveAndFlush(doctorReview);
        int databaseSizeBeforeDelete = doctorReviewRepository.findAll().size();

        // Get the doctorReview
        restDoctorReviewMockMvc.perform(delete("/api/doctor-reviews/{id}", doctorReview.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DoctorReview> doctorReviewList = doctorReviewRepository.findAll();
        assertThat(doctorReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorReview.class);
        DoctorReview doctorReview1 = new DoctorReview();
        doctorReview1.setId(1L);
        DoctorReview doctorReview2 = new DoctorReview();
        doctorReview2.setId(doctorReview1.getId());
        assertThat(doctorReview1).isEqualTo(doctorReview2);
        doctorReview2.setId(2L);
        assertThat(doctorReview1).isNotEqualTo(doctorReview2);
        doctorReview1.setId(null);
        assertThat(doctorReview1).isNotEqualTo(doctorReview2);
    }
}
