package com.example.wordquest.domain.repository;

import com.example.wordquest.domain.model.Player;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends BaseRepository<Player, UUID> {

    Optional<Player> findByUsername(String username);
   // @Query("SELECT p FROM Player p WHERE p.email.emailAddress = :email")
    Optional<Player> findByEmailEmailAddress(String email);
    boolean existsByEmailEmailAddress(String email);
    boolean existsByUsername(String username);
}