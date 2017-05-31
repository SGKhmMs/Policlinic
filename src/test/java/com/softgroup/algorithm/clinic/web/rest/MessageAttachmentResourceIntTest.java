package com.softgroup.algorithm.clinic.web.rest;

import com.softgroup.algorithm.clinic.ClinicApp;

import com.softgroup.algorithm.clinic.domain.MessageAttachment;
import com.softgroup.algorithm.clinic.repository.MessageAttachmentRepository;
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
 * Test class for the MessageAttachmentResource REST controller.
 *
 * @see MessageAttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClinicApp.class)
public class MessageAttachmentResourceIntTest {

    private static final byte[] DEFAULT_ATTACHMENT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private MessageAttachmentRepository messageAttachmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMessageAttachmentMockMvc;

    private MessageAttachment messageAttachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MessageAttachmentResource messageAttachmentResource = new MessageAttachmentResource(messageAttachmentRepository);
        this.restMessageAttachmentMockMvc = MockMvcBuilders.standaloneSetup(messageAttachmentResource)
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
    public static MessageAttachment createEntity(EntityManager em) {
        MessageAttachment messageAttachment = new MessageAttachment()
            .attachmentFile(DEFAULT_ATTACHMENT_FILE)
            .attachmentFileContentType(DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE);
        return messageAttachment;
    }

    @Before
    public void initTest() {
        messageAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageAttachment() throws Exception {
        int databaseSizeBeforeCreate = messageAttachmentRepository.findAll().size();

        // Create the MessageAttachment
        restMessageAttachmentMockMvc.perform(post("/api/message-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageAttachment)))
            .andExpect(status().isCreated());

        // Validate the MessageAttachment in the database
        List<MessageAttachment> messageAttachmentList = messageAttachmentRepository.findAll();
        assertThat(messageAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        MessageAttachment testMessageAttachment = messageAttachmentList.get(messageAttachmentList.size() - 1);
        assertThat(testMessageAttachment.getAttachmentFile()).isEqualTo(DEFAULT_ATTACHMENT_FILE);
        assertThat(testMessageAttachment.getAttachmentFileContentType()).isEqualTo(DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMessageAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageAttachmentRepository.findAll().size();

        // Create the MessageAttachment with an existing ID
        messageAttachment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageAttachmentMockMvc.perform(post("/api/message-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageAttachment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MessageAttachment> messageAttachmentList = messageAttachmentRepository.findAll();
        assertThat(messageAttachmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMessageAttachments() throws Exception {
        // Initialize the database
        messageAttachmentRepository.saveAndFlush(messageAttachment);

        // Get all the messageAttachmentList
        restMessageAttachmentMockMvc.perform(get("/api/message-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachmentFileContentType").value(hasItem(DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachmentFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT_FILE))));
    }

    @Test
    @Transactional
    public void getMessageAttachment() throws Exception {
        // Initialize the database
        messageAttachmentRepository.saveAndFlush(messageAttachment);

        // Get the messageAttachment
        restMessageAttachmentMockMvc.perform(get("/api/message-attachments/{id}", messageAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(messageAttachment.getId().intValue()))
            .andExpect(jsonPath("$.attachmentFileContentType").value(DEFAULT_ATTACHMENT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachmentFile").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingMessageAttachment() throws Exception {
        // Get the messageAttachment
        restMessageAttachmentMockMvc.perform(get("/api/message-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageAttachment() throws Exception {
        // Initialize the database
        messageAttachmentRepository.saveAndFlush(messageAttachment);
        int databaseSizeBeforeUpdate = messageAttachmentRepository.findAll().size();

        // Update the messageAttachment
        MessageAttachment updatedMessageAttachment = messageAttachmentRepository.findOne(messageAttachment.getId());
        updatedMessageAttachment
            .attachmentFile(UPDATED_ATTACHMENT_FILE)
            .attachmentFileContentType(UPDATED_ATTACHMENT_FILE_CONTENT_TYPE);

        restMessageAttachmentMockMvc.perform(put("/api/message-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessageAttachment)))
            .andExpect(status().isOk());

        // Validate the MessageAttachment in the database
        List<MessageAttachment> messageAttachmentList = messageAttachmentRepository.findAll();
        assertThat(messageAttachmentList).hasSize(databaseSizeBeforeUpdate);
        MessageAttachment testMessageAttachment = messageAttachmentList.get(messageAttachmentList.size() - 1);
        assertThat(testMessageAttachment.getAttachmentFile()).isEqualTo(UPDATED_ATTACHMENT_FILE);
        assertThat(testMessageAttachment.getAttachmentFileContentType()).isEqualTo(UPDATED_ATTACHMENT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageAttachment() throws Exception {
        int databaseSizeBeforeUpdate = messageAttachmentRepository.findAll().size();

        // Create the MessageAttachment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMessageAttachmentMockMvc.perform(put("/api/message-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageAttachment)))
            .andExpect(status().isCreated());

        // Validate the MessageAttachment in the database
        List<MessageAttachment> messageAttachmentList = messageAttachmentRepository.findAll();
        assertThat(messageAttachmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMessageAttachment() throws Exception {
        // Initialize the database
        messageAttachmentRepository.saveAndFlush(messageAttachment);
        int databaseSizeBeforeDelete = messageAttachmentRepository.findAll().size();

        // Get the messageAttachment
        restMessageAttachmentMockMvc.perform(delete("/api/message-attachments/{id}", messageAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MessageAttachment> messageAttachmentList = messageAttachmentRepository.findAll();
        assertThat(messageAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageAttachment.class);
        MessageAttachment messageAttachment1 = new MessageAttachment();
        messageAttachment1.setId(1L);
        MessageAttachment messageAttachment2 = new MessageAttachment();
        messageAttachment2.setId(messageAttachment1.getId());
        assertThat(messageAttachment1).isEqualTo(messageAttachment2);
        messageAttachment2.setId(2L);
        assertThat(messageAttachment1).isNotEqualTo(messageAttachment2);
        messageAttachment1.setId(null);
        assertThat(messageAttachment1).isNotEqualTo(messageAttachment2);
    }
}
