package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.ClientProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClientProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientProfileRepository extends JpaRepository<ClientProfile,Long> {

}
