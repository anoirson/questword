package com.example.wordquest.wordquest.domain.repositories;

import com.example.wordquest.domain.model.Lobby;
import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.repository.LobbyRepository;
import com.example.wordquest.domain.repository.PlayerRepository;
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
class LobbyRepositoryTest {

    @Autowired
    private LobbyRepository lobbyRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private Player host;
    private Lobby savedLobby;

    @BeforeEach
    void setUp() {
        lobbyRepository.deleteAll();
        playerRepository.deleteAll();

        host = new Player();
        host.setUsername("hostUsername");
        host = playerRepository.save(host);

        Lobby lobby = new Lobby("Test Lobby", host);
        savedLobby = lobbyRepository.save(lobby);
    }

    @Test
    void testSaveAndFind() {
        Optional<Lobby> found = lobbyRepository.findById(savedLobby.getId());
        assertTrue(found.isPresent(), "Expected to find the lobby");
        assertEquals("Test Lobby", found.get().getName());
        assertEquals(host.getId(), found.get().getHost().getId());
    }

    @Test
    void testDelete() {
        lobbyRepository.delete(savedLobby);
        Optional<Lobby> found = lobbyRepository.findById(savedLobby.getId());
        assertFalse(found.isPresent(), "Lobby should be deleted");
    }
}
