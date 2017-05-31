package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "sender")
    private Integer sender;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @ManyToOne
    private Chat chat;

    @OneToMany(mappedBy = "message")
    @JsonIgnore
    private Set<MessageAttachment> massageAttachments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public Message messageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Integer getSender() {
        return sender;
    }

    public Message sender(Integer sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public Message dateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Chat getChat() {
        return chat;
    }

    public Message chat(Chat chat) {
        this.chat = chat;
        return this;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Set<MessageAttachment> getMassageAttachments() {
        return massageAttachments;
    }

    public Message massageAttachments(Set<MessageAttachment> messageAttachments) {
        this.massageAttachments = messageAttachments;
        return this;
    }

    public Message addMassageAttachment(MessageAttachment messageAttachment) {
        this.massageAttachments.add(messageAttachment);
        messageAttachment.setMessage(this);
        return this;
    }

    public Message removeMassageAttachment(MessageAttachment messageAttachment) {
        this.massageAttachments.remove(messageAttachment);
        messageAttachment.setMessage(null);
        return this;
    }

    public void setMassageAttachments(Set<MessageAttachment> messageAttachments) {
        this.massageAttachments = messageAttachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", messageText='" + getMessageText() + "'" +
            ", sender='" + getSender() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            "}";
    }
}
