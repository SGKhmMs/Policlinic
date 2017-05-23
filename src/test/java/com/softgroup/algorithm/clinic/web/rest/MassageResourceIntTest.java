package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.Massage;
import com.softgroup.algorithm.clinic.repository.MassageRepository;
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
 * Test class for the MassageResource REST controller.
 *
 * @see MassageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class MassageResourceIntTest {

    private static final ZonedDateTime DEFAULT_TIME_OF_SENDING = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_OF_SENDING = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SENDER = "AAAAAAAAAA";
    private static final String UPDATED_SENDER = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private MassageRepository massageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMassageMockMvc;

    private Massage massage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MassageResource massageResource = new MassageResource(massageRepository);
        this.restMassageMockMvc = MockMvcBuilders.standaloneSetup(massageResource)
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
    public static Massage createEntity(EntityManager em) {
        Massage massage = new Massage()
            .timeOfSending(DEFAULT_TIME_OF_SENDING)
            .sender(DEFAULT_SENDER)
            .text(DEFAULT_TEXT);
        return massage;
    }

    @Before
    public void initTest() {
        massage = createEntity(em);
    }

    @Test
    @Transactional
    public void createMassage() throws Exception {
        int databaseSizeBeforeCreate = massageRepository.findAll().size();

        // Create the Massage
        restMassageMockMvc.perform(post("/api/massages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(massage)))
            .andExpect(status().isCreated());

        // Validate the Massage in the database
        List<Massage> massageList = massageRepository.findAll();
        assertThat(massageList).hasSize(databaseSizeBeforeCreate + 1);
        Massage testMassage = massageList.get(massageList.size() - 1);
        assertThat(testMassage.getTimeOfSending()).isEqualTo(DEFAULT_TIME_OF_SENDING);
        assertThat(testMassage.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testMassage.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createMassageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = massageRepository.findAll().size();

        // Create the Massage with an existing ID
        massage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMassageMockMvc.perform(post("/api/massages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(massage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Massage> massageList = massageRepository.findAll();
        assertThat(massageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMassages() throws Exception {
        // Initialize the database
        massageRepository.saveAndFlush(massage);

        // Get all the massageList
        restMassageMockMvc.perform(get("/api/massages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(massage.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeOfSending").value(hasItem(sameInstant(DEFAULT_TIME_OF_SENDING))))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getMassage() throws Exception {
        // Initialize the database
        massageRepository.saveAndFlush(massage);

        // Get the massage
        restMassageMockMvc.perform(get("/api/massages/{id}", massage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(massage.getId().intValue()))
            .andExpect(jsonPath("$.timeOfSending").value(sameInstant(DEFAULT_TIME_OF_SENDING)))
            .andExpect(jsonPath("$.sender").value(DEFAULT_SENDER.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMassage() throws Exception {
        // Get the massage
        restMassageMockMvc.perform(get("/api/massages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMassage() throws Exception {
        // Initialize the database
        massageRepository.saveAndFlush(massage);
        int databaseSizeBeforeUpdate = massageRepository.findAll().size();

        // Update the massage
        Massage updatedMassage = massageRepository.findOne(massage.getId());
        updatedMassage
            .timeOfSending(UPDATED_TIME_OF_SENDING)
            .sender(UPDATED_SENDER)
            .text(UPDATED_TEXT);

        restMassageMockMvc.perform(put("/api/massages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMassage)))
            .andExpect(status().isOk());

        // Validate the Massage in the database
        List<Massage> massageList = massageRepository.findAll();
        assertThat(massageList).hasSize(databaseSizeBeforeUpdate);
        Massage testMassage = massageList.get(massageList.size() - 1);
        assertThat(testMassage.getTimeOfSending()).isEqualTo(UPDATED_TIME_OF_SENDING);
        assertThat(testMassage.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testMassage.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingMassage() throws Exception {
        int databaseSizeBeforeUpdate = massageRepository.findAll().size();

        // Create the Massage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMassageMockMvc.perform(put("/api/massages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(massage)))
            .andExpect(status().isCreated());

        // Validate the Massage in the database
        List<Massage> massageList = massageRepository.findAll();
        assertThat(massageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMassage() throws Exception {
        // Initialize the database
        massageRepository.saveAndFlush(massage);
        int databaseSizeBeforeDelete = massageRepository.findAll().size();

        // Get the massage
        restMassageMockMvc.perform(delete("/api/massages/{id}", massage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Massage> massageList = massageRepository.findAll();
        assertThat(massageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Massage.class);
        Massage massage1 = new Massage();
        massage1.setId(1L);
        Massage massage2 = new Massage();
        massage2.setId(massage1.getId());
        assertThat(massage1).isEqualTo(massage2);
        massage2.setId(2L);
        assertThat(massage1).isNotEqualTo(massage2);
        massage1.setId(null);
        assertThat(massage1).isNotEqualTo(massage2);
    }
}
