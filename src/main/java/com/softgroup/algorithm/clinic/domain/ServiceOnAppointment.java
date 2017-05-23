package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ServiceOnAppointment.
 */
@Entity
@Table(name = "service_on_appointment")
public class ServiceOnAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "price")
    private Double price;

    @OneToOne(mappedBy = "serviceOnAppointment")
    @JsonIgnore
    private Service service;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public ServiceOnAppointment price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Service getService() {
        return service;
    }

    public ServiceOnAppointment service(Service service) {
        this.service = service;
        return this;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceOnAppointment serviceOnAppointment = (ServiceOnAppointment) o;
        if (serviceOnAppointment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceOnAppointment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceOnAppointment{" +
            "id=" + getId() +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
