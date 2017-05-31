package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Chat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Chat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {

}
