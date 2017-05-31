package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.RoutineCase;
import com.softgroup.algorithm.clinic.repository.RoutineCaseRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.softgroup.algorithm.clinic.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RoutineCaseResource REST controller.
 *
 * @see RoutineCaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class RoutineCaseResourceIntTest {

    private static final ZonedDateTime DEFAULT_BEGIN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BEGIN_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RoutineCaseRepository routineCaseRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRoutineCaseMockMvc;

    private RoutineCase routineCase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RoutineCaseResource routineCaseResource = new RoutineCaseResource(routineCaseRepository);
        this.restRoutineCaseMockMvc = MockMvcBuilders.standaloneSetup(routineCaseResource)
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
    public static RoutineCase createEntity(EntityManager em) {
        RoutineCase routineCase = new RoutineCase()
            .beginTime(DEFAULT_BEGIN_TIME)
            .endTime(DEFAULT_END_TIME)
            .description(DEFAULT_DESCRIPTION);
        return routineCase;
    }

    @Before
    public void initTest() {
        routineCase = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoutineCase() throws Exception {
        int databaseSizeBeforeCreate = routineCaseRepository.findAll().size();

        // Create the RoutineCase
        restRoutineCaseMockMvc.perform(post("/api/routine-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routineCase)))
            .andExpect(status().isCreated());

        // Validate the RoutineCase in the database
        List<RoutineCase> routineCaseList = routineCaseRepository.findAll();
        assertThat(routineCaseList).hasSize(databaseSizeBeforeCreate + 1);
        RoutineCase testRoutineCase = routineCaseList.get(routineCaseList.size() - 1);
        assertThat(testRoutineCase.getBeginTime()).isEqualTo(DEFAULT_BEGIN_TIME);
        assertThat(testRoutineCase.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testRoutineCase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRoutineCaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = routineCaseRepository.findAll().size();

        // Create the RoutineCase with an existing ID
        routineCase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoutineCaseMockMvc.perform(post("/api/routine-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routineCase)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RoutineCase> routineCaseList = routineCaseRepository.findAll();
        assertThat(routineCaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRoutineCases() throws Exception {
        // Initialize the database
        routineCaseRepository.saveAndFlush(routineCase);

        // Get all the routineCaseList
        restRoutineCaseMockMvc.perform(get("/api/routine-cases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(routineCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].beginTime").value(hasItem(sameInstant(DEFAULT_BEGIN_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRoutineCase() throws Exception {
        // Initialize the database
        routineCaseRepository.saveAndFlush(routineCase);

        // Get the routineCase
        restRoutineCaseMockMvc.perform(get("/api/routine-cases/{id}", routineCase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(routineCase.getId().intValue()))
            .andExpect(jsonPath("$.beginTime").value(sameInstant(DEFAULT_BEGIN_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoutineCase() throws Exception {
        // Get the routineCase
        restRoutineCaseMockMvc.perform(get("/api/routine-cases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoutineCase() throws Exception {
        // Initialize the database
        routineCaseRepository.saveAndFlush(routineCase);
        int databaseSizeBeforeUpdate = routineCaseRepository.findAll().size();

        // Update the routineCase
        RoutineCase updatedRoutineCase = routineCaseRepository.findOne(routineCase.getId());
        updatedRoutineCase
            .beginTime(UPDATED_BEGIN_TIME)
            .endTime(UPDATED_END_TIME)
            .description(UPDATED_DESCRIPTION);

        restRoutineCaseMockMvc.perform(put("/api/routine-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoutineCase)))
            .andExpect(status().isOk());

        // Validate the RoutineCase in the database
        List<RoutineCase> routineCaseList = routineCaseRepository.findAll();
        assertThat(routineCaseList).hasSize(databaseSizeBeforeUpdate);
        RoutineCase testRoutineCase = routineCaseList.get(routineCaseList.size() - 1);
        assertThat(testRoutineCase.getBeginTime()).isEqualTo(UPDATED_BEGIN_TIME);
        assertThat(testRoutineCase.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testRoutineCase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRoutineCase() throws Exception {
        int databaseSizeBeforeUpdate = routineCaseRepository.findAll().size();

        // Create the RoutineCase

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoutineCaseMockMvc.perform(put("/api/routine-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routineCase)))
            .andExpect(status().isCreated());

        // Validate the RoutineCase in the database
        List<RoutineCase> routineCaseList = routineCaseRepository.findAll();
        assertThat(routineCaseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRoutineCase() throws Exception {
        // Initialize the database
        routineCaseRepository.saveAndFlush(routineCase);
        int databaseSizeBeforeDelete = routineCaseRepository.findAll().size();

        // Get the routineCase
        restRoutineCaseMockMvc.perform(delete("/api/routine-cases/{id}", routineCase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoutineCase> routineCaseList = routineCaseRepository.findAll();
        assertThat(routineCaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoutineCase.class);
        RoutineCase routineCase1 = new RoutineCase();
        routineCase1.setId(1L);
        RoutineCase routineCase2 = new RoutineCase();
        routineCase2.setId(routineCase1.getId());
        assertThat(routineCase1).isEqualTo(routineCase2);
        routineCase2.setId(2L);
        assertThat(routineCase1).isNotEqualTo(routineCase2);
        routineCase1.setId(null);
        assertThat(routineCase1).isNotEqualTo(routineCase2);
    }
}
