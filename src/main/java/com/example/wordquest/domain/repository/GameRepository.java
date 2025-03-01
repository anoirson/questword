package com.example.wordquest.domain.repository;

import com.example.wordquest.domain.model.Game;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends BaseRepository<Game, UUID> {
    // Add custom queries if needed
}
