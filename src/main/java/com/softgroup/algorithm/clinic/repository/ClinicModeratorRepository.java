package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.ClinicModerator;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClinicModerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClinicModeratorRepository extends JpaRepository<ClinicModerator,Long> {

}
