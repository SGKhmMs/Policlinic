package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<DoctorSpecialty> doctorSpecialties = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<DoctorDayRoutine> doctorDayRoutines = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Doctor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Doctor surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public Doctor lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public Doctor phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Doctor photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Doctor photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Set<DoctorSpecialty> getDoctorSpecialties() {
        return doctorSpecialties;
    }

    public Doctor doctorSpecialties(Set<DoctorSpecialty> doctorSpecialties) {
        this.doctorSpecialties = doctorSpecialties;
        return this;
    }

    public Doctor addDoctorSpecialty(DoctorSpecialty doctorSpecialty) {
        this.doctorSpecialties.add(doctorSpecialty);
        doctorSpecialty.setDoctor(this);
        return this;
    }

    public Doctor removeDoctorSpecialty(DoctorSpecialty doctorSpecialty) {
        this.doctorSpecialties.remove(doctorSpecialty);
        doctorSpecialty.setDoctor(null);
        return this;
    }

    public void setDoctorSpecialties(Set<DoctorSpecialty> doctorSpecialties) {
        this.doctorSpecialties = doctorSpecialties;
    }

    public Set<DoctorDayRoutine> getDoctorDayRoutines() {
        return doctorDayRoutines;
    }

    public Doctor doctorDayRoutines(Set<DoctorDayRoutine> doctorDayRoutines) {
        this.doctorDayRoutines = doctorDayRoutines;
        return this;
    }

    public Doctor addDoctorDayRoutine(DoctorDayRoutine doctorDayRoutine) {
        this.doctorDayRoutines.add(doctorDayRoutine);
        doctorDayRoutine.setDoctor(this);
        return this;
    }

    public Doctor removeDoctorDayRoutine(DoctorDayRoutine doctorDayRoutine) {
        this.doctorDayRoutines.remove(doctorDayRoutine);
        doctorDayRoutine.setDoctor(null);
        return this;
    }

    public void setDoctorDayRoutines(Set<DoctorDayRoutine> doctorDayRoutines) {
        this.doctorDayRoutines = doctorDayRoutines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Doctor doctor = (Doctor) o;
        if (doctor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doctor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Doctor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", phone='" + getPhone() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + photoContentType + "'" +
            "}";
    }
}
