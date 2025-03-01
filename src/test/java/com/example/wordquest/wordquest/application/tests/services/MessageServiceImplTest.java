package com.example.wordquest.wordquest.application.tests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.wordquest.application.dto.message.*;
import com.example.wordquest.application.factory.impl.MessageDTOFactoryImpl;
import com.example.wordquest.application.service.impl.MessageServiceImpl;
import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageDTOFactoryImpl messageDTOFactory;

    @InjectMocks
    private MessageServiceImpl messageService;

    private Message testMessage;
    private MessageReadDTO readDTO;

    @BeforeEach
    void setUp() {
        testMessage = new Message(null, null, "Guess1", false);
        testMessage.setId(UUID.randomUUID());

        readDTO = new MessageReadDTO(testMessage.getId(), null, null, "Guess1", false);
    }

    @Test
    void testSave_Success() {
        MessageCreateDTO createDTO = new MessageCreateDTO("roundId", "playerId", "Guess1", false);

        when(messageDTOFactory.createEntity(createDTO)).thenReturn(testMessage);
        when(messageRepository.save(any(Message.class))).thenReturn(testMessage);
        when(messageDTOFactory.createReadDto(testMessage)).thenReturn(readDTO);

        MessageReadDTO result = messageService.save(createDTO);

        assertNotNull(result);
        assertEquals("Guess1", result.getMessage());
        verify(messageRepository).save(testMessage);
    }

    @Test
    void testFindById_Success() {
        when(messageRepository.findById(testMessage.getId())).thenReturn(Optional.of(testMessage));
        when(messageDTOFactory.createReadDto(testMessage)).thenReturn(readDTO);

        Optional<MessageReadDTO> found = messageService.findById(testMessage.getId());

        assertTrue(found.isPresent());
        assertEquals("Guess1", found.get().getMessage());
    }

    @Test
    void testFindAll() {
        List<Message> messages = List.of(testMessage);
        List<MessageReadDTO> readDTOs = List.of(readDTO);

        when(messageRepository.findAll()).thenReturn(messages);
        when(messageDTOFactory.createReadDto(testMessage)).thenReturn(readDTO);

        List<MessageReadDTO> result = messageService.findAll();

        assertEquals(1, result.size());
        assertEquals("Guess1", result.get(0).getMessage());
    }

    @Test
    void testUpdate_Success() {
        UUID id = testMessage.getId();
        MessageUpdateDTO updateDTO = new MessageUpdateDTO(null, null, "Updated Guess", true);

        when(messageRepository.findById(id)).thenReturn(Optional.of(testMessage));
        when(messageDTOFactory.updateEntity(testMessage, updateDTO)).thenReturn(testMessage);
        when(messageRepository.save(testMessage)).thenReturn(testMessage);
        when(messageDTOFactory.createReadDto(testMessage)).thenReturn(readDTO);

        MessageReadDTO updated = messageService.update(id, updateDTO);

        assertNotNull(updated);
        assertEquals("Guess1", updated.getMessage()); // since `readDTO` was not updated in the test
    }

    @Test
    void testDeleteById_Success() {
        UUID id = testMessage.getId();
        when(messageRepository.findById(id)).thenReturn(Optional.of(testMessage));

        messageService.deleteById(id);
        verify(messageRepository).delete(testMessage);
    }
}
