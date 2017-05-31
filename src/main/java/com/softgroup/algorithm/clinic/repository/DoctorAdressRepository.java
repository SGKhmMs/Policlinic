package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.DoctorAdress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DoctorAdress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorAdressRepository extends JpaRepository<DoctorAdress,Long> {

}
