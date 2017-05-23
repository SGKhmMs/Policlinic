package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
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

    @Column(name = "begin_time")
    private ZonedDateTime beginTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "price")
    private Double price;

    @OneToOne(mappedBy = "appointment")
    @JsonIgnore
    private Doctor doctor;

    @OneToOne(mappedBy = "appointment")
    @JsonIgnore
    private Client client;

    @OneToOne(mappedBy = "appointment")
    @JsonIgnore
    private ServiceOnAppointment serviceOnAppointment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getBeginTime() {
        return beginTime;
    }

    public Appointment beginTime(ZonedDateTime beginTime) {
        this.beginTime = beginTime;
        return this;
    }

    public void setBeginTime(ZonedDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public Appointment endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public Appointment doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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

    public ServiceOnAppointment getServiceOnAppointment() {
        return serviceOnAppointment;
    }

    public Appointment serviceOnAppointment(ServiceOnAppointment serviceOnAppointment) {
        this.serviceOnAppointment = serviceOnAppointment;
        return this;
    }

    public void setServiceOnAppointment(ServiceOnAppointment serviceOnAppointment) {
        this.serviceOnAppointment = serviceOnAppointment;
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
            ", beginTime='" + getBeginTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
