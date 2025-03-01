package com.example.wordquest.wordquest.application.tests.factories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.wordquest.application.dto.message.*;
import com.example.wordquest.application.factory.impl.MessageDTOFactoryImpl;
import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.PlayerRepository;
import com.example.wordquest.domain.repository.RoundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class MessageDTOFactoryTest {

    @Mock
    private RoundRepository roundRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private MessageDTOFactoryImpl messageDTOFactory;

    private Round testRound;
    private Player testPlayer;
    private Message testMessage;

    @BeforeEach
    void setUp() {
        testRound = new Round(null, 1, null, "apple");
        testRound.setId(UUID.randomUUID());

        testPlayer = new Player("testUser", null);
        testPlayer.setId(UUID.randomUUID());

        testMessage = new Message(testRound, testPlayer, "Guess1", false);
        testMessage.setId(UUID.randomUUID());
    }

    @Test
    void testCreateEntity_Success() {
        MessageCreateDTO createDTO = new MessageCreateDTO(
                testRound.getId().toString(), testPlayer.getId().toString(), "Guess1", false);

        when(roundRepository.findById(any(UUID.class))).thenReturn(Optional.of(testRound));
        when(playerRepository.findById(any(UUID.class))).thenReturn(Optional.of(testPlayer));

        Message message = messageDTOFactory.createEntity(createDTO);

        assertNotNull(message);
        assertEquals("Guess1", message.getMessage());
        assertFalse(message.isCorrect());
        assertEquals(testRound, message.getRound());
        assertEquals(testPlayer, message.getPlayer());
    }

    @Test
    void testCreateEntity_RoundNotFound() {
        MessageCreateDTO createDTO = new MessageCreateDTO(UUID.randomUUID().toString(), testPlayer.getId().toString(),
                "Guess1", false);
        when(roundRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> messageDTOFactory.createEntity(createDTO));
    }

    @Test
    void testCreateReadDto() {
        MessageReadDTO readDTO = messageDTOFactory.createReadDto(testMessage);

        assertNotNull(readDTO);
        assertEquals(testMessage.getId(), readDTO.getId());
        assertEquals("Guess1", readDTO.getMessage());
        assertFalse(readDTO.isCorrect());
    }

    @Test
    void testUpdateEntity() {
        MessageUpdateDTO updateDTO = new MessageUpdateDTO(null, null, "New Guess", true);

        Message updatedMessage = messageDTOFactory.updateEntity(testMessage, updateDTO);

        assertNotNull(updatedMessage);
        assertEquals("New Guess", updatedMessage.getMessage());
        assertTrue(updatedMessage.isCorrect());
    }
}
