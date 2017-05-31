package com.softgroup.algorithm.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DoctorDayRoutine.
 */
@Entity
@Table(name = "doctor_day_routine")
public class DoctorDayRoutine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "day_begin_time")
    private ZonedDateTime dayBeginTime;

    @Column(name = "day_end_time")
    private ZonedDateTime dayEndTime;

    @Column(name = "jhi_date")
    private LocalDate date;

    @ManyToOne
    private Doctor doctor;

    @OneToMany(mappedBy = "doctorDayRoutine")
    @JsonIgnore
    private Set<RoutineCase> routineCases = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDayBeginTime() {
        return dayBeginTime;
    }

    public DoctorDayRoutine dayBeginTime(ZonedDateTime dayBeginTime) {
        this.dayBeginTime = dayBeginTime;
        return this;
    }

    public void setDayBeginTime(ZonedDateTime dayBeginTime) {
        this.dayBeginTime = dayBeginTime;
    }

    public ZonedDateTime getDayEndTime() {
        return dayEndTime;
    }

    public DoctorDayRoutine dayEndTime(ZonedDateTime dayEndTime) {
        this.dayEndTime = dayEndTime;
        return this;
    }

    public void setDayEndTime(ZonedDateTime dayEndTime) {
        this.dayEndTime = dayEndTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public DoctorDayRoutine date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public DoctorDayRoutine doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Set<RoutineCase> getRoutineCases() {
        return routineCases;
    }

    public DoctorDayRoutine routineCases(Set<RoutineCase> routineCases) {
        this.routineCases = routineCases;
        return this;
    }

    public DoctorDayRoutine addRoutineCase(RoutineCase routineCase) {
        this.routineCases.add(routineCase);
        routineCase.setDoctorDayRoutine(this);
        return this;
    }

    public DoctorDayRoutine removeRoutineCase(RoutineCase routineCase) {
        this.routineCases.remove(routineCase);
        routineCase.setDoctorDayRoutine(null);
        return this;
    }

    public void setRoutineCases(Set<RoutineCase> routineCases) {
        this.routineCases = routineCases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DoctorDayRoutine doctorDayRoutine = (DoctorDayRoutine) o;
        if (doctorDayRoutine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doctorDayRoutine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DoctorDayRoutine{" +
            "id=" + getId() +
            ", dayBeginTime='" + getDayBeginTime() + "'" +
            ", dayEndTime='" + getDayEndTime() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
