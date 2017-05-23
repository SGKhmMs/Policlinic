package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.CardEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CardEntry entity.
 */
@SuppressWarnings("unused")
public interface CardEntryRepository extends JpaRepository<CardEntry,Long> {

}
