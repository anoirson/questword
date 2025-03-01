package com.example.wordquest.wordquest.domain.repositories;

import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.repository.PlayerRepository;
import com.example.wordquest.domain.valueObjects.EmailVO;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest // Uses an in-memory database by default (H2)
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    private Player savedPlayer;

    @BeforeEach
    void setUp() {
        playerRepository.deleteAll();
        Player player = new Player();
        player.setUsername("testUser");
        player.setEmail(new EmailVO("testEmail@example.com"));
        savedPlayer = playerRepository.save(player);

    }

    @Test
    void findByUsername_ShouldReturnPlayer_WhenExists() {
        // when
        Optional<Player> result = playerRepository.findByUsername("testUser");

        // then
        assertTrue(result.isPresent(), "Expected player to be found by username");
        assertEquals("testUser", result.get().getUsername());
        assertEquals("testEmail@example.com", result.get().getEmail().getValue());
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenNotExists() {
        // when
        Optional<Player> result = playerRepository.findByUsername("unknownUser");

        // then
        assertFalse(result.isPresent(), "Expected no player for unknown username");
    }

    @Test
    void findByEmail_ShouldReturnPlayer_WhenExists() {
        // when
        Optional<Player> result = playerRepository.findByEmailEmailAddress("testEmail@example.com");

        // then
        assertTrue(result.isPresent(), "Expected player to be found by email");
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenNotExists() {
        // when
        Optional<Player> result = playerRepository.findByEmailEmailAddress("nonExistent@example.com");

        // then
        assertFalse(result.isPresent(), "Expected no player for non-existent email");
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenEmailExists() {
        // when
        boolean exists = playerRepository.existsByEmailEmailAddress("testEmail@example.com");

        // then
        assertTrue(exists, "Expected email to exist in DB");
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenEmailNotExists() {
        // when
        boolean exists = playerRepository.existsByEmailEmailAddress("fake@example.com");

        // then
        assertFalse(exists, "Expected email to not exist in DB");
    }

    @Test
    void existsByUsername_ShouldReturnTrue_WhenUsernameExists() {
        // when
        boolean exists = playerRepository.existsByUsername("testUser");

        // then
        assertTrue(exists, "Expected username to exist in DB");
    }

    @Test
    void existsByUsername_ShouldReturnFalse_WhenUsernameNotExists() {
        // when
        boolean exists = playerRepository.existsByUsername("noUser");

        // then
        assertFalse(exists, "Expected username to not exist in DB");
    }
}