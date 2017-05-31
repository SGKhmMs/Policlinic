package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DoctorAdress.
 */
@Entity
@Table(name = "doctor_adress")
public class DoctorAdress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "adress")
    private String adress;

    @OneToOne
    @JoinColumn(unique = true)
    private Doctor doctor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public DoctorAdress adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public DoctorAdress doctor(Doctor doctor) {
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
        DoctorAdress doctorAdress = (DoctorAdress) o;
        if (doctorAdress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doctorAdress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DoctorAdress{" +
            "id=" + getId() +
            ", adress='" + getAdress() + "'" +
            "}";
    }
}
