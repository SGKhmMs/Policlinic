package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.CardEntry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CardEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardEntryRepository extends JpaRepository<CardEntry,Long> {

}
