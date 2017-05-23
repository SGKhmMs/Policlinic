package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.ClinicDoctor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClinicDoctor entity.
 */
@SuppressWarnings("unused")
public interface ClinicDoctorRepository extends JpaRepository<ClinicDoctor,Long> {

}
