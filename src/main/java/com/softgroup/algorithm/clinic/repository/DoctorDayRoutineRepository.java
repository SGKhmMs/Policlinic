package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.DoctorDayRoutine;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DoctorDayRoutine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorDayRoutineRepository extends JpaRepository<DoctorDayRoutine,Long> {

}
