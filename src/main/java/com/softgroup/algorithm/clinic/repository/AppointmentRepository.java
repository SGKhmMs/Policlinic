package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Appointment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

}
