package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.ClinicModeratorProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClinicModeratorProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClinicModeratorProfileRepository extends JpaRepository<ClinicModeratorProfile,Long> {

}
