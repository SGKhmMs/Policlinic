package com.softgroup.algorithm.clinic.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RoutineCase.
 */
@Entity
@Table(name = "routine_case")
public class RoutineCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "begin_time")
    private ZonedDateTime beginTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private DoctorDayRoutine doctorDayRoutine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getBeginTime() {
        return beginTime;
    }

    public RoutineCase beginTime(ZonedDateTime beginTime) {
        this.beginTime = beginTime;
        return this;
    }

    public void setBeginTime(ZonedDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public RoutineCase endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public RoutineCase description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DoctorDayRoutine getDoctorDayRoutine() {
        return doctorDayRoutine;
    }

    public RoutineCase doctorDayRoutine(DoctorDayRoutine doctorDayRoutine) {
        this.doctorDayRoutine = doctorDayRoutine;
        return this;
    }

    public void setDoctorDayRoutine(DoctorDayRoutine doctorDayRoutine) {
        this.doctorDayRoutine = doctorDayRoutine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoutineCase routineCase = (RoutineCase) o;
        if (routineCase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), routineCase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoutineCase{" +
            "id=" + getId() +
            ", beginTime='" + getBeginTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
