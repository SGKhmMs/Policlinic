package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MessageAttachment.
 */
@Entity
@Table(name = "message_attachment")
public class MessageAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "attachment_file")
    private byte[] attachmentFile;

    @Column(name = "attachment_file_content_type")
    private String attachmentFileContentType;

    @ManyToOne
    private Message message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAttachmentFile() {
        return attachmentFile;
    }

    public MessageAttachment attachmentFile(byte[] attachmentFile) {
        this.attachmentFile = attachmentFile;
        return this;
    }

    public void setAttachmentFile(byte[] attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public String getAttachmentFileContentType() {
        return attachmentFileContentType;
    }

    public MessageAttachment attachmentFileContentType(String attachmentFileContentType) {
        this.attachmentFileContentType = attachmentFileContentType;
        return this;
    }

    public void setAttachmentFileContentType(String attachmentFileContentType) {
        this.attachmentFileContentType = attachmentFileContentType;
    }

    public Message getMessage() {
        return message;
    }

    public MessageAttachment message(Message message) {
        this.message = message;
        return this;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageAttachment messageAttachment = (MessageAttachment) o;
        if (messageAttachment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageAttachment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageAttachment{" +
            "id=" + getId() +
            ", attachmentFile='" + getAttachmentFile() + "'" +
            ", attachmentFileContentType='" + attachmentFileContentType + "'" +
            "}";
    }
}
