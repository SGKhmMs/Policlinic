package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CardEntry.
 */
@Entity
@Table(name = "card_entry")
public class CardEntry implements Serializable {

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

    @Column(name = "text")
    private String text;

    @OneToOne(mappedBy = "cardEntry")
    @JsonIgnore
    private Appointment appointment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAttachmentFile() {
        return attachmentFile;
    }

    public CardEntry attachmentFile(byte[] attachmentFile) {
        this.attachmentFile = attachmentFile;
        return this;
    }

    public void setAttachmentFile(byte[] attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public String getAttachmentFileContentType() {
        return attachmentFileContentType;
    }

    public CardEntry attachmentFileContentType(String attachmentFileContentType) {
        this.attachmentFileContentType = attachmentFileContentType;
        return this;
    }

    public void setAttachmentFileContentType(String attachmentFileContentType) {
        this.attachmentFileContentType = attachmentFileContentType;
    }

    public String getText() {
        return text;
    }

    public CardEntry text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public CardEntry appointment(Appointment appointment) {
        this.appointment = appointment;
        return this;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CardEntry cardEntry = (CardEntry) o;
        if (cardEntry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cardEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CardEntry{" +
            "id=" + getId() +
            ", attachmentFile='" + getAttachmentFile() + "'" +
            ", attachmentFileContentType='" + attachmentFileContentType + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
