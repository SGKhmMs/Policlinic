package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.DoctorReview;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DoctorReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorReviewRepository extends JpaRepository<DoctorReview,Long> {

}
