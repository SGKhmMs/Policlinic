package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DoctorProfile.
 */
@Entity
@Table(name = "doctor_profile")
public class DoctorProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "pass_hash")
    private Long passHash;

    @OneToOne
    @JoinColumn(unique = true)
    private Doctor doctor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public DoctorProfile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPassHash() {
        return passHash;
    }

    public DoctorProfile passHash(Long passHash) {
        this.passHash = passHash;
        return this;
    }

    public void setPassHash(Long passHash) {
        this.passHash = passHash;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public DoctorProfile doctor(Doctor doctor) {
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
        DoctorProfile doctorProfile = (DoctorProfile) o;
        if (doctorProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doctorProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DoctorProfile{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", passHash='" + getPassHash() + "'" +
            "}";
    }
}
