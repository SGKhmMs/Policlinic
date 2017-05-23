package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Attechment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Attechment entity.
 */
@SuppressWarnings("unused")
public interface AttechmentRepository extends JpaRepository<Attechment,Long> {

}
