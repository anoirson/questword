package com.example.wordquest.wordquest.domain.repositories;

import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.*;
import com.example.wordquest.domain.valueObjects.GameStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RoundRepositoryTest {

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private Round savedRound;

    @BeforeEach
    void setUp() {
        roundRepository.deleteAll();
        gameRepository.deleteAll();
        playerRepository.deleteAll();

        Game game = new Game(null, GameStatus.WAITING);
        game = gameRepository.save(game);

        Player player = new Player("drawerPlayer", null);
        player = playerRepository.save(player);

        Round round = new Round(game, 1, player, "apple");
        savedRound = roundRepository.save(round);
    }

    @Test
    void testFindById() {
        Optional<Round> found = roundRepository.findById(savedRound.getId());
        assertTrue(found.isPresent());
        assertEquals("apple", found.get().getWord());
    }

    @Test
    void testDelete() {
        roundRepository.delete(savedRound);
        Optional<Round> found = roundRepository.findById(savedRound.getId());
        assertFalse(found.isPresent());
    }
}
