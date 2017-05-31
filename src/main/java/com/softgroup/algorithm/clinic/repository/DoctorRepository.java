package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Doctor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Doctor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

}
