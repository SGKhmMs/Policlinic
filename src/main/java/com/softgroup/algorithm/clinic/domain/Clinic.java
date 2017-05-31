package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "begin_of_work")
    private ZonedDateTime beginOfWork;

    @Column(name = "end_of_work")
    private ZonedDateTime endOfWork;

    @Column(name = "registry_phone")
    private String registryPhone;

    @OneToMany(mappedBy = "clinic")
    @JsonIgnore
    private Set<ClinicDoctor> clinicDoctors = new HashSet<>();

    @OneToMany(mappedBy = "clinic")
    @JsonIgnore
    private Set<ClinicModerator> clinicModerators = new HashSet<>();

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

    public ZonedDateTime getBeginOfWork() {
        return beginOfWork;
    }

    public Clinic beginOfWork(ZonedDateTime beginOfWork) {
        this.beginOfWork = beginOfWork;
        return this;
    }

    public void setBeginOfWork(ZonedDateTime beginOfWork) {
        this.beginOfWork = beginOfWork;
    }

    public ZonedDateTime getEndOfWork() {
        return endOfWork;
    }

    public Clinic endOfWork(ZonedDateTime endOfWork) {
        this.endOfWork = endOfWork;
        return this;
    }

    public void setEndOfWork(ZonedDateTime endOfWork) {
        this.endOfWork = endOfWork;
    }

    public String getRegistryPhone() {
        return registryPhone;
    }

    public Clinic registryPhone(String registryPhone) {
        this.registryPhone = registryPhone;
        return this;
    }

    public void setRegistryPhone(String registryPhone) {
        this.registryPhone = registryPhone;
    }

    public Set<ClinicDoctor> getClinicDoctors() {
        return clinicDoctors;
    }

    public Clinic clinicDoctors(Set<ClinicDoctor> clinicDoctors) {
        this.clinicDoctors = clinicDoctors;
        return this;
    }

    public Clinic addClinicDoctor(ClinicDoctor clinicDoctor) {
        this.clinicDoctors.add(clinicDoctor);
        clinicDoctor.setClinic(this);
        return this;
    }

    public Clinic removeClinicDoctor(ClinicDoctor clinicDoctor) {
        this.clinicDoctors.remove(clinicDoctor);
        clinicDoctor.setClinic(null);
        return this;
    }

    public void setClinicDoctors(Set<ClinicDoctor> clinicDoctors) {
        this.clinicDoctors = clinicDoctors;
    }

    public Set<ClinicModerator> getClinicModerators() {
        return clinicModerators;
    }

    public Clinic clinicModerators(Set<ClinicModerator> clinicModerators) {
        this.clinicModerators = clinicModerators;
        return this;
    }

    public Clinic addClinicModerator(ClinicModerator clinicModerator) {
        this.clinicModerators.add(clinicModerator);
        clinicModerator.setClinic(this);
        return this;
    }

    public Clinic removeClinicModerator(ClinicModerator clinicModerator) {
        this.clinicModerators.remove(clinicModerator);
        clinicModerator.setClinic(null);
        return this;
    }

    public void setClinicModerators(Set<ClinicModerator> clinicModerators) {
        this.clinicModerators = clinicModerators;
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
            ", beginOfWork='" + getBeginOfWork() + "'" +
            ", endOfWork='" + getEndOfWork() + "'" +
            ", registryPhone='" + getRegistryPhone() + "'" +
            "}";
    }
}
