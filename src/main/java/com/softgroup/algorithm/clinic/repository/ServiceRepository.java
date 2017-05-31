package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Service;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Service entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {

}
