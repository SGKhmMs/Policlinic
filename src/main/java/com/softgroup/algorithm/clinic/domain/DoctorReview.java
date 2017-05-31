package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DoctorReview.
 */
@Entity
@Table(name = "doctor_review")
public class DoctorReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "review_text")
    private String reviewText;

    @ManyToOne
    private Appointment appointment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public DoctorReview reviewText(String reviewText) {
        this.reviewText = reviewText;
        return this;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public DoctorReview appointment(Appointment appointment) {
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
        DoctorReview doctorReview = (DoctorReview) o;
        if (doctorReview.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doctorReview.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DoctorReview{" +
            "id=" + getId() +
            ", reviewText='" + getReviewText() + "'" +
            "}";
    }
}
