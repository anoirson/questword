package com.example.wordquest.wordquest.domain.repositories;

import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private Message savedMessage;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
        roundRepository.deleteAll();
        playerRepository.deleteAll();

        Round round = new Round(null, 1, null, "apple");
        round = roundRepository.save(round);

        Player player = new Player("testUser", null);
        player = playerRepository.save(player);

        Message message = new Message(round, player, "Guess1", false);
        savedMessage = messageRepository.save(message);
    }

    @Test
    void testFindById() {
        Optional<Message> found = messageRepository.findById(savedMessage.getId());
        assertTrue(found.isPresent());
        assertEquals("Guess1", found.get().getMessage());
    }

    @Test
    void testDelete() {
        messageRepository.delete(savedMessage);
        Optional<Message> found = messageRepository.findById(savedMessage.getId());
        assertFalse(found.isPresent());
    }
}
