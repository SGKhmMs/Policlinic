package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Massage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Massage entity.
 */
@SuppressWarnings("unused")
public interface MassageRepository extends JpaRepository<Massage,Long> {

}
