package com.example.wordquest.domain.repository;

import com.example.wordquest.domain.model.Round;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends BaseRepository<Round, UUID> {
    // Add custom query methods if needed
}
