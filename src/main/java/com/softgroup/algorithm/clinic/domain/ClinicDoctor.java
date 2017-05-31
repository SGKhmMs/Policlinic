package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClinicDoctor.
 */
@Entity
@Table(name = "clinic_doctor")
public class ClinicDoctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Clinic clinic;

    @OneToOne
    @JoinColumn(unique = true)
    private Doctor doctor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public ClinicDoctor clinic(Clinic clinic) {
        this.clinic = clinic;
        return this;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public ClinicDoctor doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClinicDoctor clinicDoctor = (ClinicDoctor) o;
        if (clinicDoctor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clinicDoctor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClinicDoctor{" +
            "id=" + getId() +
            "}";
    }
}
