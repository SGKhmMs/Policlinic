package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.Attechment;
import com.softgroup.algorithm.clinic.repository.AttechmentRepository;
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
 * Test class for the AttechmentResource REST controller.
 *
 * @see AttechmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class AttechmentResourceIntTest {

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    @Autowired
    private AttechmentRepository attechmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAttechmentMockMvc;

    private Attechment attechment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AttechmentResource attechmentResource = new AttechmentResource(attechmentRepository);
        this.restAttechmentMockMvc = MockMvcBuilders.standaloneSetup(attechmentResource)
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
    public static Attechment createEntity(EntityManager em) {
        Attechment attechment = new Attechment()
            .contentType(DEFAULT_CONTENT_TYPE);
        return attechment;
    }

    @Before
    public void initTest() {
        attechment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttechment() throws Exception {
        int databaseSizeBeforeCreate = attechmentRepository.findAll().size();

        // Create the Attechment
        restAttechmentMockMvc.perform(post("/api/attechments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attechment)))
            .andExpect(status().isCreated());

        // Validate the Attechment in the database
        List<Attechment> attechmentList = attechmentRepository.findAll();
        assertThat(attechmentList).hasSize(databaseSizeBeforeCreate + 1);
        Attechment testAttechment = attechmentList.get(attechmentList.size() - 1);
        assertThat(testAttechment.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAttechmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attechmentRepository.findAll().size();

        // Create the Attechment with an existing ID
        attechment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttechmentMockMvc.perform(post("/api/attechments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attechment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Attechment> attechmentList = attechmentRepository.findAll();
        assertThat(attechmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAttechments() throws Exception {
        // Initialize the database
        attechmentRepository.saveAndFlush(attechment);

        // Get all the attechmentList
        restAttechmentMockMvc.perform(get("/api/attechments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attechment.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getAttechment() throws Exception {
        // Initialize the database
        attechmentRepository.saveAndFlush(attechment);

        // Get the attechment
        restAttechmentMockMvc.perform(get("/api/attechments/{id}", attechment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attechment.getId().intValue()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttechment() throws Exception {
        // Get the attechment
        restAttechmentMockMvc.perform(get("/api/attechments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttechment() throws Exception {
        // Initialize the database
        attechmentRepository.saveAndFlush(attechment);
        int databaseSizeBeforeUpdate = attechmentRepository.findAll().size();

        // Update the attechment
        Attechment updatedAttechment = attechmentRepository.findOne(attechment.getId());
        updatedAttechment
            .contentType(UPDATED_CONTENT_TYPE);

        restAttechmentMockMvc.perform(put("/api/attechments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttechment)))
            .andExpect(status().isOk());

        // Validate the Attechment in the database
        List<Attechment> attechmentList = attechmentRepository.findAll();
        assertThat(attechmentList).hasSize(databaseSizeBeforeUpdate);
        Attechment testAttechment = attechmentList.get(attechmentList.size() - 1);
        assertThat(testAttechment.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttechment() throws Exception {
        int databaseSizeBeforeUpdate = attechmentRepository.findAll().size();

        // Create the Attechment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAttechmentMockMvc.perform(put("/api/attechments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attechment)))
            .andExpect(status().isCreated());

        // Validate the Attechment in the database
        List<Attechment> attechmentList = attechmentRepository.findAll();
        assertThat(attechmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAttechment() throws Exception {
        // Initialize the database
        attechmentRepository.saveAndFlush(attechment);
        int databaseSizeBeforeDelete = attechmentRepository.findAll().size();

        // Get the attechment
        restAttechmentMockMvc.perform(delete("/api/attechments/{id}", attechment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Attechment> attechmentList = attechmentRepository.findAll();
        assertThat(attechmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attechment.class);
        Attechment attechment1 = new Attechment();
        attechment1.setId(1L);
        Attechment attechment2 = new Attechment();
        attechment2.setId(attechment1.getId());
        assertThat(attechment1).isEqualTo(attechment2);
        attechment2.setId(2L);
        assertThat(attechment1).isNotEqualTo(attechment2);
        attechment1.setId(null);
        assertThat(attechment1).isNotEqualTo(attechment2);
    }
}
