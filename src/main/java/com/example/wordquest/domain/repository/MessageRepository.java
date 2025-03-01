package com.example.wordquest.domain.repository;

import com.example.wordquest.domain.model.Message;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends BaseRepository<Message, UUID> {
    // Add custom queries if needed
}