package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Specialty;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Specialty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty,Long> {

}
