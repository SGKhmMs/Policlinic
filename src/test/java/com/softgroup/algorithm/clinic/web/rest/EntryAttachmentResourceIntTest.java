package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.EntryAttachment;
import com.softgroup.algorithm.clinic.repository.EntryAttachmentRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntryAttachmentResource REST controller.
 *
 * @see EntryAttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class EntryAttachmentResourceIntTest {

    private static final byte[] DEFAULT_ATTACHMENT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private EntryAttachmentRepository entryAttachmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntryAttachmentMockMvc;

    private EntryAttachment entryAttachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntryAttachmentResource entryAttachmentResource = new EntryAttachmentResource(entryAttachmentRepository);
        this.restEntryAttachmentMockMvc = MockMvcBuilders.standaloneSetup(entryAttachmentResource)
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
    public static EntryAttachment createEntity(EntityManager em) {
        EntryAttachment entryAttachment = new EntryAttachment()
            .attachmentFile(DEFAULT_ATTACHMENT_FILE)
            .attachmentFileContentType(DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE);
        return entryAttachment;
    }

    @Before
    public void initTest() {
        entryAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntryAttachment() throws Exception {
        int databaseSizeBeforeCreate = entryAttachmentRepository.findAll().size();

        // Create the EntryAttachment
        restEntryAttachmentMockMvc.perform(post("/api/entry-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryAttachment)))
            .andExpect(status().isCreated());

        // Validate the EntryAttachment in the database
        List<EntryAttachment> entryAttachmentList = entryAttachmentRepository.findAll();
        assertThat(entryAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        EntryAttachment testEntryAttachment = entryAttachmentList.get(entryAttachmentList.size() - 1);
        assertThat(testEntryAttachment.getAttachmentFile()).isEqualTo(DEFAULT_ATTACHMENT_FILE);
        assertThat(testEntryAttachment.getAttachmentFileContentType()).isEqualTo(DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEntryAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entryAttachmentRepository.findAll().size();

        // Create the EntryAttachment with an existing ID
        entryAttachment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntryAttachmentMockMvc.perform(post("/api/entry-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryAttachment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EntryAttachment> entryAttachmentList = entryAttachmentRepository.findAll();
        assertThat(entryAttachmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEntryAttachments() throws Exception {
        // Initialize the database
        entryAttachmentRepository.saveAndFlush(entryAttachment);

        // Get all the entryAttachmentList
        restEntryAttachmentMockMvc.perform(get("/api/entry-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entryAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachmentFileContentType").value(hasItem(DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachmentFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT_FILE))));
    }

    @Test
    @Transactional
    public void getEntryAttachment() throws Exception {
        // Initialize the database
        entryAttachmentRepository.saveAndFlush(entryAttachment);

        // Get the entryAttachment
        restEntryAttachmentMockMvc.perform(get("/api/entry-attachments/{id}", entryAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entryAttachment.getId().intValue()))
            .andExpect(jsonPath("$.attachmentFileContentType").value(DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachmentFile").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingEntryAttachment() throws Exception {
        // Get the entryAttachment
        restEntryAttachmentMockMvc.perform(get("/api/entry-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntryAttachment() throws Exception {
        // Initialize the database
        entryAttachmentRepository.saveAndFlush(entryAttachment);
        int databaseSizeBeforeUpdate = entryAttachmentRepository.findAll().size();

        // Update the entryAttachment
        EntryAttachment updatedEntryAttachment = entryAttachmentRepository.findOne(entryAttachment.getId());
        updatedEntryAttachment
            .attachmentFile(UPDATED_ATTACHMENT_FILE)
            .attachmentFileContentType(UPDATED_ATTACHMENT_FILE_CONTENT_TYPE);

        restEntryAttachmentMockMvc.perform(put("/api/entry-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntryAttachment)))
            .andExpect(status().isOk());

        // Validate the EntryAttachment in the database
        List<EntryAttachment> entryAttachmentList = entryAttachmentRepository.findAll();
        assertThat(entryAttachmentList).hasSize(databaseSizeBeforeUpdate);
        EntryAttachment testEntryAttachment = entryAttachmentList.get(entryAttachmentList.size() - 1);
        assertThat(testEntryAttachment.getAttachmentFile()).isEqualTo(UPDATED_ATTACHMENT_FILE);
        assertThat(testEntryAttachment.getAttachmentFileContentType()).isEqualTo(UPDATED_ATTACHMENT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEntryAttachment() throws Exception {
        int databaseSizeBeforeUpdate = entryAttachmentRepository.findAll().size();

        // Create the EntryAttachment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntryAttachmentMockMvc.perform(put("/api/entry-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entryAttachment)))
            .andExpect(status().isCreated());

        // Validate the EntryAttachment in the database
        List<EntryAttachment> entryAttachmentList = entryAttachmentRepository.findAll();
        assertThat(entryAttachmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntryAttachment() throws Exception {
        // Initialize the database
        entryAttachmentRepository.saveAndFlush(entryAttachment);
        int databaseSizeBeforeDelete = entryAttachmentRepository.findAll().size();

        // Get the entryAttachment
        restEntryAttachmentMockMvc.perform(delete("/api/entry-attachments/{id}", entryAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntryAttachment> entryAttachmentList = entryAttachmentRepository.findAll();
        assertThat(entryAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryAttachment.class);
        EntryAttachment entryAttachment1 = new EntryAttachment();
        entryAttachment1.setId(1L);
        EntryAttachment entryAttachment2 = new EntryAttachment();
        entryAttachment2.setId(entryAttachment1.getId());
        assertThat(entryAttachment1).isEqualTo(entryAttachment2);
        entryAttachment2.setId(2L);
        assertThat(entryAttachment1).isNotEqualTo(entryAttachment2);
        entryAttachment1.setId(null);
        assertThat(entryAttachment1).isNotEqualTo(entryAttachment2);
    }
}
