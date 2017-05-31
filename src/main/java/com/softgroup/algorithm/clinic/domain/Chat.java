package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Chat.
 */
@Entity
@Table(name = "chat")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Client client;

    @OneToOne
    @JoinColumn(unique = true)
    private Doctor doctor;

    @OneToMany(mappedBy = "chat")
    @JsonIgnore
    private Set<Message> massages = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public Chat client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Chat doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Set<Message> getMassages() {
        return massages;
    }

    public Chat massages(Set<Message> messages) {
        this.massages = messages;
        return this;
    }

    public Chat addMassage(Message message) {
        this.massages.add(message);
        message.setChat(this);
        return this;
    }

    public Chat removeMassage(Message message) {
        this.massages.remove(message);
        message.setChat(null);
        return this;
    }

    public void setMassages(Set<Message> messages) {
        this.massages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chat chat = (Chat) o;
        if (chat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chat{" +
            "id=" + getId() +
            "}";
    }
}
