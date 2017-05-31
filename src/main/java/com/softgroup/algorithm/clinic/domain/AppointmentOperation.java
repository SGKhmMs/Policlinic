package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AppointmentOperation.
 */
@Entity
@Table(name = "appointment_operation")
public class AppointmentOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    private Appointment appointment;

    @OneToOne
    @JoinColumn(unique = true)
    private Operation operation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public AppointmentOperation price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public AppointmentOperation appointment(Appointment appointment) {
        this.appointment = appointment;
        return this;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Operation getOperation() {
        return operation;
    }

    public AppointmentOperation operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppointmentOperation appointmentOperation = (AppointmentOperation) o;
        if (appointmentOperation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointmentOperation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppointmentOperation{" +
            "id=" + getId() +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
