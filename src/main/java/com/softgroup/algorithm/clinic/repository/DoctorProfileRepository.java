package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.DoctorProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DoctorProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile,Long> {

}
