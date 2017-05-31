package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DoctorSpecialty.
 */
@Entity
@Table(name = "doctor_specialty")
public class DoctorSpecialty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Specialty specialty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public DoctorSpecialty doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public DoctorSpecialty specialty(Specialty specialty) {
        this.specialty = specialty;
        return this;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DoctorSpecialty doctorSpecialty = (DoctorSpecialty) o;
        if (doctorSpecialty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doctorSpecialty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DoctorSpecialty{" +
            "id=" + getId() +
            "}";
    }
}
