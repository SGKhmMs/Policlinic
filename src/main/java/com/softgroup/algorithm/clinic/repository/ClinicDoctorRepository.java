package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.ClinicDoctor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClinicDoctor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClinicDoctorRepository extends JpaRepository<ClinicDoctor,Long> {

}
