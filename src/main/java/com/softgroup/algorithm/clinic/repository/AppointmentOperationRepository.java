package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.AppointmentOperation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AppointmentOperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentOperationRepository extends JpaRepository<AppointmentOperation,Long> {

}
