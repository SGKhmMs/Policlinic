package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Specialty;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Specialty entity.
 */
@SuppressWarnings("unused")
public interface SpecialtyRepository extends JpaRepository<Specialty,Long> {

}
