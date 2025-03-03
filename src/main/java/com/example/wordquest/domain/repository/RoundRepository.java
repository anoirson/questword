package com.example.wordquest.domain.repository;

import com.example.wordquest.domain.model.Round;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends BaseRepository<Round, UUID> {
    List<Round> findAllByGameId(UUID gameId);
}
