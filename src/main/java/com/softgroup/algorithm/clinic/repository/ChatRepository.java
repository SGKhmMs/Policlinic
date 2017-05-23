package com.softgroup.algorithm.clinic.repository;

import com.softgroup.algorithm.clinic.domain.Chat;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Chat entity.
 */
@SuppressWarnings("unused")
public interface ChatRepository extends JpaRepository<Chat,Long> {

}
