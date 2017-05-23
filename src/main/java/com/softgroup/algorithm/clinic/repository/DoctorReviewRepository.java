package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.DoctorReview;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DoctorReview entity.
 */
@SuppressWarnings("unused")
public interface DoctorReviewRepository extends JpaRepository<DoctorReview,Long> {

}
