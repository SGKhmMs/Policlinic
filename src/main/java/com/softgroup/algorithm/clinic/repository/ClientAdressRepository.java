package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.ClientAdress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClientAdress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientAdressRepository extends JpaRepository<ClientAdress,Long> {

}
