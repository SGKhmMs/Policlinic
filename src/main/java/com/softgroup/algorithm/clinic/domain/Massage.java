package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Massage.
 */
@Entity
@Table(name = "massage")
public class Massage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "time_of_sending")
    private ZonedDateTime timeOfSending;

    @Column(name = "sender")
    private String sender;

    @Column(name = "text")
    private String text;

    @OneToOne(mappedBy = "massage")
    @JsonIgnore
    private Chat chat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimeOfSending() {
        return timeOfSending;
    }

    public Massage timeOfSending(ZonedDateTime timeOfSending) {
        this.timeOfSending = timeOfSending;
        return this;
    }

    public void setTimeOfSending(ZonedDateTime timeOfSending) {
        this.timeOfSending = timeOfSending;
    }

    public String getSender() {
        return sender;
    }

    public Massage sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public Massage text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Chat getChat() {
        return chat;
    }

    public Massage chat(Chat chat) {
        this.chat = chat;
        return this;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Massage massage = (Massage) o;
        if (massage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), massage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Massage{" +
            "id=" + getId() +
            ", timeOfSending='" + getTimeOfSending() + "'" +
            ", sender='" + getSender() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
