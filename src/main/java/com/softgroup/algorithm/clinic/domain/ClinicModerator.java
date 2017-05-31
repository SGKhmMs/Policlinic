package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClinicModerator.
 */
@Entity
@Table(name = "clinic_moderator")
public class ClinicModerator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    private Clinic clinic;

    @OneToOne
    @JoinColumn(unique = true)
    private ClinicModeratorProfile clinicModeratorProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public ClinicModerator surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public ClinicModerator name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public ClinicModerator lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public ClinicModerator phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public ClinicModerator clinic(Clinic clinic) {
        this.clinic = clinic;
        return this;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public ClinicModeratorProfile getClinicModeratorProfile() {
        return clinicModeratorProfile;
    }

    public ClinicModerator clinicModeratorProfile(ClinicModeratorProfile clinicModeratorProfile) {
        this.clinicModeratorProfile = clinicModeratorProfile;
        return this;
    }

    public void setClinicModeratorProfile(ClinicModeratorProfile clinicModeratorProfile) {
        this.clinicModeratorProfile = clinicModeratorProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClinicModerator clinicModerator = (ClinicModerator) o;
        if (clinicModerator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clinicModerator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClinicModerator{" +
            "id=" + getId() +
            ", surname='" + getSurname() + "'" +
            ", name='" + getName() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
