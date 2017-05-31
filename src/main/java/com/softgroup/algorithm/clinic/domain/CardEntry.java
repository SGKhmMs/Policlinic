package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "entry_text")
    private String entryText;

    @OneToMany(mappedBy = "cardEntry")
    @JsonIgnore
    private Set<EntryAttachment> entryAttachents = new HashSet<>();

    @ManyToOne
    private Appointment appointment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryText() {
        return entryText;
    }

    public CardEntry entryText(String entryText) {
        this.entryText = entryText;
        return this;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public Set<EntryAttachment> getEntryAttachents() {
        return entryAttachents;
    }

    public CardEntry entryAttachents(Set<EntryAttachment> entryAttachments) {
        this.entryAttachents = entryAttachments;
        return this;
    }

    public CardEntry addEntryAttachent(EntryAttachment entryAttachment) {
        this.entryAttachents.add(entryAttachment);
        entryAttachment.setCardEntry(this);
        return this;
    }

    public CardEntry removeEntryAttachent(EntryAttachment entryAttachment) {
        this.entryAttachents.remove(entryAttachment);
        entryAttachment.setCardEntry(null);
        return this;
    }

    public void setEntryAttachents(Set<EntryAttachment> entryAttachments) {
        this.entryAttachents = entryAttachments;
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
            ", entryText='" + getEntryText() + "'" +
            "}";
    }
}
