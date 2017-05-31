package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.RoutineCase;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RoutineCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoutineCaseRepository extends JpaRepository<RoutineCase,Long> {

}
