package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Clinic.
 */
@Entity
@Table(name = "clinic")
public class Clinic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "adress")
    private String adress;

    @Column(name = "work_dat_begin")
    private Instant workDatBegin;

    @Column(name = "work_day_end")
    private Instant workDayEnd;

    @Column(name = "reception_phone")
    private String receptionPhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClinicName() {
        return clinicName;
    }

    public Clinic clinicName(String clinicName) {
        this.clinicName = clinicName;
        return this;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getAdress() {
        return adress;
    }

    public Clinic adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Instant getWorkDatBegin() {
        return workDatBegin;
    }

    public Clinic workDatBegin(Instant workDatBegin) {
        this.workDatBegin = workDatBegin;
        return this;
    }

    public void setWorkDatBegin(Instant workDatBegin) {
        this.workDatBegin = workDatBegin;
    }

    public Instant getWorkDayEnd() {
        return workDayEnd;
    }

    public Clinic workDayEnd(Instant workDayEnd) {
        this.workDayEnd = workDayEnd;
        return this;
    }

    public void setWorkDayEnd(Instant workDayEnd) {
        this.workDayEnd = workDayEnd;
    }

    public String getReceptionPhone() {
        return receptionPhone;
    }

    public Clinic receptionPhone(String receptionPhone) {
        this.receptionPhone = receptionPhone;
        return this;
    }

    public void setReceptionPhone(String receptionPhone) {
        this.receptionPhone = receptionPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Clinic clinic = (Clinic) o;
        if (clinic.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clinic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Clinic{" +
            "id=" + getId() +
            ", clinicName='" + getClinicName() + "'" +
            ", adress='" + getAdress() + "'" +
            ", workDatBegin='" + getWorkDatBegin() + "'" +
            ", workDayEnd='" + getWorkDayEnd() + "'" +
            ", receptionPhone='" + getReceptionPhone() + "'" +
            "}";
    }
}
