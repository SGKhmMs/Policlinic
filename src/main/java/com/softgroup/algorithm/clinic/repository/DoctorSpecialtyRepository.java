package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.DoctorSpecialty;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DoctorSpecialty entity.
 */
@SuppressWarnings("unused")
public interface DoctorSpecialtyRepository extends JpaRepository<DoctorSpecialty,Long> {

}
