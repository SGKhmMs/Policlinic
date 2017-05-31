package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Clinic;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Clinic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClinicRepository extends JpaRepository<Clinic,Long> {

}
