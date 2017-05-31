package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EntryAttachment.
 */
@Entity
@Table(name = "entry_attachment")
public class EntryAttachment implements Serializable {

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
    private CardEntry cardEntry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAttachmentFile() {
        return attachmentFile;
    }

    public EntryAttachment attachmentFile(byte[] attachmentFile) {
        this.attachmentFile = attachmentFile;
        return this;
    }

    public void setAttachmentFile(byte[] attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public String getAttachmentFileContentType() {
        return attachmentFileContentType;
    }

    public EntryAttachment attachmentFileContentType(String attachmentFileContentType) {
        this.attachmentFileContentType = attachmentFileContentType;
        return this;
    }

    public void setAttachmentFileContentType(String attachmentFileContentType) {
        this.attachmentFileContentType = attachmentFileContentType;
    }

    public CardEntry getCardEntry() {
        return cardEntry;
    }

    public EntryAttachment cardEntry(CardEntry cardEntry) {
        this.cardEntry = cardEntry;
        return this;
    }

    public void setCardEntry(CardEntry cardEntry) {
        this.cardEntry = cardEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntryAttachment entryAttachment = (EntryAttachment) o;
        if (entryAttachment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entryAttachment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntryAttachment{" +
            "id=" + getId() +
            ", attachmentFile='" + getAttachmentFile() + "'" +
            ", attachmentFileContentType='" + attachmentFileContentType + "'" +
            "}";
    }
}
