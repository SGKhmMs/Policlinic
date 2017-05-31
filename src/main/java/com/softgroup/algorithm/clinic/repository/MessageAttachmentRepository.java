package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.MessageAttachment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MessageAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageAttachmentRepository extends JpaRepository<MessageAttachment,Long> {

}
