package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Clinic;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Clinic entity.
 */
@SuppressWarnings("unused")
public interface ClinicRepository extends JpaRepository<Clinic,Long> {

}
