package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Attechment.
 */
@Entity
@Table(name = "attechment")
public class Attechment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content_type")
    private String contentType;

    @OneToOne(mappedBy = "attechment")
    @JsonIgnore
    private Massage massage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public Attechment contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Massage getMassage() {
        return massage;
    }

    public Attechment massage(Massage massage) {
        this.massage = massage;
        return this;
    }

    public void setMassage(Massage massage) {
        this.massage = massage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attechment attechment = (Attechment) o;
        if (attechment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attechment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Attechment{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            "}";
    }
}
