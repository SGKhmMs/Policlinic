package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.DoctorSpecialty;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DoctorSpecialty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorSpecialtyRepository extends JpaRepository<DoctorSpecialty,Long> {

}
