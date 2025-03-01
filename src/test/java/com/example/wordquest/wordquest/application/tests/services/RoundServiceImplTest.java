package com.example.wordquest.wordquest.application.tests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.wordquest.application.dto.round.*;
import com.example.wordquest.application.factory.impl.RoundDTOFactoryImpl;
import com.example.wordquest.application.service.impl.RoundServiceImpl;
import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.RoundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class RoundServiceImplTest {

    @Mock
    private RoundRepository roundRepository;

    @Mock
    private RoundDTOFactoryImpl roundDTOFactory;

    @InjectMocks
    private RoundServiceImpl roundService;

    private Round testRound;
    private RoundReadDTO readDTO;

    @BeforeEach
    void setUp() {
        testRound = new Round(null, 1, null, "apple");
        testRound.setId(UUID.randomUUID());

        readDTO = new RoundReadDTO(testRound.getId(), 1, "apple", null, null);
    }

    @Test
    void testSave_Success() {
        // Given
        RoundCreateDTO createDTO = new RoundCreateDTO(UUID.randomUUID().toString(), 1, UUID.randomUUID().toString(),
                "apple");

        when(roundDTOFactory.createEntity(createDTO)).thenReturn(testRound);
        when(roundRepository.save(any(Round.class))).thenReturn(testRound);
        when(roundDTOFactory.createReadDto(testRound)).thenReturn(readDTO);

        // When
        RoundReadDTO result = roundService.save(createDTO);

        // Then
        assertNotNull(result);
        assertEquals("apple", result.getWord());
        verify(roundRepository).save(testRound);
    }

    @Test
    void testFindById_Success() {
        when(roundRepository.findById(testRound.getId())).thenReturn(Optional.of(testRound));
        when(roundDTOFactory.createReadDto(testRound)).thenReturn(readDTO);

        Optional<RoundReadDTO> found = roundService.findById(testRound.getId());

        assertTrue(found.isPresent());
        assertEquals("apple", found.get().getWord());
    }

    @Test
    void testFindById_NotFound() {
        UUID id = UUID.randomUUID();
        when(roundRepository.findById(id)).thenReturn(Optional.empty());

        Optional<RoundReadDTO> found = roundService.findById(id);

        assertFalse(found.isPresent());
    }

    @Test
    void testGetAll() {
        List<Round> rounds = List.of(testRound);
        List<RoundReadDTO> readDTOs = List.of(readDTO);

        when(roundRepository.findAll()).thenReturn(rounds);
        when(roundDTOFactory.createReadDto(testRound)).thenReturn(readDTO);

        List<RoundReadDTO> result = roundService.findAll();

        assertEquals(1, result.size());
        assertEquals("apple", result.get(0).getWord());
    }

    @Test
    void testUpdate_Success() {
        UUID id = testRound.getId();
        RoundUpdateDTO updateDTO = new RoundUpdateDTO(null, 2, null, "banana");

        when(roundRepository.findById(id)).thenReturn(Optional.of(testRound));
        when(roundDTOFactory.updateEntity(testRound, updateDTO)).thenReturn(testRound);
        when(roundRepository.save(testRound)).thenReturn(testRound);
        when(roundDTOFactory.createReadDto(testRound)).thenReturn(readDTO);

        RoundReadDTO updated = roundService.update(id, updateDTO);

        assertNotNull(updated);
        assertEquals("apple", updated.getWord()); // because readDTO has "apple", not "banana"
    }

    @Test
    void testDeleteById_Success() {
        UUID id = testRound.getId();
        when(roundRepository.findById(id)).thenReturn(Optional.of(testRound));

        roundService.deleteById(id);
        verify(roundRepository).delete(testRound);
    }
}
