package com.newts.newtapp.api.gateways;

import com.newts.newtapp.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MessageRepository implementation is handled by Spring Boot.
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
