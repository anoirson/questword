package com.example.wordquest.domain.repository;

import org.springframework.stereotype.Repository;
import com.example.wordquest.domain.model.Player;

@Repository
public interface PlayerRepository extends BaseRepository<Player, Long> {
}