package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    private Client client;

    @OneToOne
    @JoinColumn(unique = true)
    private RoutineCase routineCase;

    @OneToMany(mappedBy = "appointment")
    @JsonIgnore
    private Set<AppointmentOperation> appointmentOperations = new HashSet<>();

    @OneToMany(mappedBy = "appointment")
    @JsonIgnore
    private Set<DoctorReview> doctorRewiews = new HashSet<>();

    @OneToMany(mappedBy = "appointment")
    @JsonIgnore
    private Set<CardEntry> cardEntries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public Appointment price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public Appointment client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public RoutineCase getRoutineCase() {
        return routineCase;
    }

    public Appointment routineCase(RoutineCase routineCase) {
        this.routineCase = routineCase;
        return this;
    }

    public void setRoutineCase(RoutineCase routineCase) {
        this.routineCase = routineCase;
    }

    public Set<AppointmentOperation> getAppointmentOperations() {
        return appointmentOperations;
    }

    public Appointment appointmentOperations(Set<AppointmentOperation> appointmentOperations) {
        this.appointmentOperations = appointmentOperations;
        return this;
    }

    public Appointment addAppointmentOperation(AppointmentOperation appointmentOperation) {
        this.appointmentOperations.add(appointmentOperation);
        appointmentOperation.setAppointment(this);
        return this;
    }

    public Appointment removeAppointmentOperation(AppointmentOperation appointmentOperation) {
        this.appointmentOperations.remove(appointmentOperation);
        appointmentOperation.setAppointment(null);
        return this;
    }

    public void setAppointmentOperations(Set<AppointmentOperation> appointmentOperations) {
        this.appointmentOperations = appointmentOperations;
    }

    public Set<DoctorReview> getDoctorRewiews() {
        return doctorRewiews;
    }

    public Appointment doctorRewiews(Set<DoctorReview> doctorReviews) {
        this.doctorRewiews = doctorReviews;
        return this;
    }

    public Appointment addDoctorRewiew(DoctorReview doctorReview) {
        this.doctorRewiews.add(doctorReview);
        doctorReview.setAppointment(this);
        return this;
    }

    public Appointment removeDoctorRewiew(DoctorReview doctorReview) {
        this.doctorRewiews.remove(doctorReview);
        doctorReview.setAppointment(null);
        return this;
    }

    public void setDoctorRewiews(Set<DoctorReview> doctorReviews) {
        this.doctorRewiews = doctorReviews;
    }

    public Set<CardEntry> getCardEntries() {
        return cardEntries;
    }

    public Appointment cardEntries(Set<CardEntry> cardEntries) {
        this.cardEntries = cardEntries;
        return this;
    }

    public Appointment addCardEntry(CardEntry cardEntry) {
        this.cardEntries.add(cardEntry);
        cardEntry.setAppointment(this);
        return this;
    }

    public Appointment removeCardEntry(CardEntry cardEntry) {
        this.cardEntries.remove(cardEntry);
        cardEntry.setAppointment(null);
        return this;
    }

    public void setCardEntries(Set<CardEntry> cardEntries) {
        this.cardEntries = cardEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Appointment appointment = (Appointment) o;
        if (appointment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
