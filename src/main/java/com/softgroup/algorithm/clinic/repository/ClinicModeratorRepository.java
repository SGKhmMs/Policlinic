package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.ClinicModerator;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClinicModerator entity.
 */
@SuppressWarnings("unused")
public interface ClinicModeratorRepository extends JpaRepository<ClinicModerator,Long> {

}
