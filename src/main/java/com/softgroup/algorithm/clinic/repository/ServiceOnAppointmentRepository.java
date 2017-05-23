package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.ServiceOnAppointment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceOnAppointment entity.
 */
@SuppressWarnings("unused")
public interface ServiceOnAppointmentRepository extends JpaRepository<ServiceOnAppointment,Long> {

}
