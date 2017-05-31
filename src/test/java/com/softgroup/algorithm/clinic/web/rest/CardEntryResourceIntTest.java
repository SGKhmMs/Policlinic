package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.CardEntry;
import com.softgroup.algorithm.clinic.repository.CardEntryRepository;
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
 * Test class for the CardEntryResource REST controller.
 *
 * @see CardEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class CardEntryResourceIntTest {

    private static final String DEFAULT_ENTRY_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ENTRY_TEXT = "BBBBBBBBBB";

    @Autowired
    private CardEntryRepository cardEntryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCardEntryMockMvc;

    private CardEntry cardEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CardEntryResource cardEntryResource = new CardEntryResource(cardEntryRepository);
        this.restCardEntryMockMvc = MockMvcBuilders.standaloneSetup(cardEntryResource)
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
    public static CardEntry createEntity(EntityManager em) {
        CardEntry cardEntry = new CardEntry()
            .entryText(DEFAULT_ENTRY_TEXT);
        return cardEntry;
    }

    @Before
    public void initTest() {
        cardEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createCardEntry() throws Exception {
        int databaseSizeBeforeCreate = cardEntryRepository.findAll().size();

        // Create the CardEntry
        restCardEntryMockMvc.perform(post("/api/card-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardEntry)))
            .andExpect(status().isCreated());

        // Validate the CardEntry in the database
        List<CardEntry> cardEntryList = cardEntryRepository.findAll();
        assertThat(cardEntryList).hasSize(databaseSizeBeforeCreate + 1);
        CardEntry testCardEntry = cardEntryList.get(cardEntryList.size() - 1);
        assertThat(testCardEntry.getEntryText()).isEqualTo(DEFAULT_ENTRY_TEXT);
    }

    @Test
    @Transactional
    public void createCardEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardEntryRepository.findAll().size();

        // Create the CardEntry with an existing ID
        cardEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardEntryMockMvc.perform(post("/api/card-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardEntry)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CardEntry> cardEntryList = cardEntryRepository.findAll();
        assertThat(cardEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCardEntries() throws Exception {
        // Initialize the database
        cardEntryRepository.saveAndFlush(cardEntry);

        // Get all the cardEntryList
        restCardEntryMockMvc.perform(get("/api/card-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].entryText").value(hasItem(DEFAULT_ENTRY_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getCardEntry() throws Exception {
        // Initialize the database
        cardEntryRepository.saveAndFlush(cardEntry);

        // Get the cardEntry
        restCardEntryMockMvc.perform(get("/api/card-entries/{id}", cardEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cardEntry.getId().intValue()))
            .andExpect(jsonPath("$.entryText").value(DEFAULT_ENTRY_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCardEntry() throws Exception {
        // Get the cardEntry
        restCardEntryMockMvc.perform(get("/api/card-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCardEntry() throws Exception {
        // Initialize the database
        cardEntryRepository.saveAndFlush(cardEntry);
        int databaseSizeBeforeUpdate = cardEntryRepository.findAll().size();

        // Update the cardEntry
        CardEntry updatedCardEntry = cardEntryRepository.findOne(cardEntry.getId());
        updatedCardEntry
            .entryText(UPDATED_ENTRY_TEXT);

        restCardEntryMockMvc.perform(put("/api/card-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCardEntry)))
            .andExpect(status().isOk());

        // Validate the CardEntry in the database
        List<CardEntry> cardEntryList = cardEntryRepository.findAll();
        assertThat(cardEntryList).hasSize(databaseSizeBeforeUpdate);
        CardEntry testCardEntry = cardEntryList.get(cardEntryList.size() - 1);
        assertThat(testCardEntry.getEntryText()).isEqualTo(UPDATED_ENTRY_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingCardEntry() throws Exception {
        int databaseSizeBeforeUpdate = cardEntryRepository.findAll().size();

        // Create the CardEntry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCardEntryMockMvc.perform(put("/api/card-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardEntry)))
            .andExpect(status().isCreated());

        // Validate the CardEntry in the database
        List<CardEntry> cardEntryList = cardEntryRepository.findAll();
        assertThat(cardEntryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCardEntry() throws Exception {
        // Initialize the database
        cardEntryRepository.saveAndFlush(cardEntry);
        int databaseSizeBeforeDelete = cardEntryRepository.findAll().size();

        // Get the cardEntry
        restCardEntryMockMvc.perform(delete("/api/card-entries/{id}", cardEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CardEntry> cardEntryList = cardEntryRepository.findAll();
        assertThat(cardEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardEntry.class);
        CardEntry cardEntry1 = new CardEntry();
        cardEntry1.setId(1L);
        CardEntry cardEntry2 = new CardEntry();
        cardEntry2.setId(cardEntry1.getId());
        assertThat(cardEntry1).isEqualTo(cardEntry2);
        cardEntry2.setId(2L);
        assertThat(cardEntry1).isNotEqualTo(cardEntry2);
        cardEntry1.setId(null);
        assertThat(cardEntry1).isNotEqualTo(cardEntry2);
    }
}
