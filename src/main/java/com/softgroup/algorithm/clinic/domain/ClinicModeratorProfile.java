package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClinicModeratorProfile.
 */
@Entity
@Table(name = "clinic_moderator_profile")
public class ClinicModeratorProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pass_hash")
    private Long passHash;

    @Column(name = "email")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPassHash() {
        return passHash;
    }

    public ClinicModeratorProfile passHash(Long passHash) {
        this.passHash = passHash;
        return this;
    }

    public void setPassHash(Long passHash) {
        this.passHash = passHash;
    }

    public String getEmail() {
        return email;
    }

    public ClinicModeratorProfile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClinicModeratorProfile clinicModeratorProfile = (ClinicModeratorProfile) o;
        if (clinicModeratorProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clinicModeratorProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClinicModeratorProfile{" +
            "id=" + getId() +
            ", passHash='" + getPassHash() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
