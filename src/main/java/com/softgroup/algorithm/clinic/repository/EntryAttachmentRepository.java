package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.EntryAttachment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EntryAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryAttachmentRepository extends JpaRepository<EntryAttachment,Long> {

}
