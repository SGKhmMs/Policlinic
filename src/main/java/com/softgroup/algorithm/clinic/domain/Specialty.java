package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Specialty.
 */
@Entity
@Table(name = "specialty")
public class Specialty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "specialty_name")
    private String specialtyName;

    @OneToMany(mappedBy = "specialty")
    @JsonIgnore
    private Set<DoctorSpecialty> doctorSpecialties = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public Specialty specialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
        return this;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public Set<DoctorSpecialty> getDoctorSpecialties() {
        return doctorSpecialties;
    }

    public Specialty doctorSpecialties(Set<DoctorSpecialty> doctorSpecialties) {
        this.doctorSpecialties = doctorSpecialties;
        return this;
    }

    public Specialty addDoctorSpecialty(DoctorSpecialty doctorSpecialty) {
        this.doctorSpecialties.add(doctorSpecialty);
        doctorSpecialty.setSpecialty(this);
        return this;
    }

    public Specialty removeDoctorSpecialty(DoctorSpecialty doctorSpecialty) {
        this.doctorSpecialties.remove(doctorSpecialty);
        doctorSpecialty.setSpecialty(null);
        return this;
    }

    public void setDoctorSpecialties(Set<DoctorSpecialty> doctorSpecialties) {
        this.doctorSpecialties = doctorSpecialties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Specialty specialty = (Specialty) o;
        if (specialty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Specialty{" +
            "id=" + getId() +
            ", specialtyName='" + getSpecialtyName() + "'" +
            "}";
    }
}
