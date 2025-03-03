package com.example.wordquest.wordquest.application.tests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.wordquest.application.dto.round.*;
import com.example.wordquest.application.factory.RoundDTOFactory;
import com.example.wordquest.application.service.impl.RoundServiceImpl;
import com.example.wordquest.domain.model.Round;
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
    private RoundDTOFactory roundDTOFactory; // ✅ Ensuring correct factory is mocked

    @InjectMocks
    private RoundServiceImpl roundService; // ✅ Mocks should be injected here

    private Round testRound;
    private RoundReadDTO readDTO;
    private UUID gameId;
    private List<Round> roundEntities;
    private List<RoundReadDTO> roundDTOs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // ✅ Ensures mocks are initialized

        gameId = UUID.randomUUID();

        // ✅ Set up a test round
        testRound = new Round();
        testRound.setId(UUID.randomUUID());
        testRound.setRoundNumber(1);
        testRound.setWord("apple");

        readDTO = new RoundReadDTO(
                testRound.getId(),
                testRound.getRoundNumber(),
                testRound.getWord(),
                null,
                null);

        // ✅ Setup multiple rounds for findAllByGameId
        Round round1 = new Round();
        round1.setId(UUID.randomUUID());
        round1.setRoundNumber(1);

        Round round2 = new Round();
        round2.setId(UUID.randomUUID());
        round2.setRoundNumber(2);

        roundEntities = List.of(round1, round2);
        roundDTOs = roundEntities.stream()
                .map(r -> new RoundReadDTO(r.getId(), r.getRoundNumber(), r.getWord(), null, null))
                .toList();
    }

    @Test
    void testSave_Success() {
        // Given
        RoundCreateDTO createDTO = new RoundCreateDTO(
                UUID.randomUUID().toString(),
                1,
                UUID.randomUUID().toString(),
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
        // Given
        when(roundRepository.findById(testRound.getId())).thenReturn(Optional.of(testRound));
        when(roundDTOFactory.createReadDto(testRound)).thenReturn(readDTO);

        // When
        Optional<RoundReadDTO> found = roundService.findById(testRound.getId());

        // Then
        assertTrue(found.isPresent());
        assertEquals("apple", found.get().getWord());
        verify(roundRepository).findById(testRound.getId());
    }

    @Test
    void testFindById_NotFound() {
        // Given
        UUID unknownId = UUID.randomUUID();
        when(roundRepository.findById(unknownId)).thenReturn(Optional.empty());

        // When
        Optional<RoundReadDTO> found = roundService.findById(unknownId);

        // Then
        assertFalse(found.isPresent());
        verify(roundRepository).findById(unknownId);
    }

    @Test
    void testGetAll() {
        // Given
        List<Round> rounds = List.of(testRound);
        List<RoundReadDTO> expectedDTOs = List.of(readDTO);

        when(roundRepository.findAll()).thenReturn(rounds);
        when(roundDTOFactory.createReadDto(testRound)).thenReturn(readDTO);

        // When
        List<RoundReadDTO> result = roundService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals("apple", result.get(0).getWord());
        verify(roundRepository).findAll();
    }

    @Test
    void testUpdate_Success() {
        // Given
        UUID id = testRound.getId();
        RoundUpdateDTO updateDTO = new RoundUpdateDTO(null, 2, null, "banana");

        when(roundRepository.findById(id)).thenReturn(Optional.of(testRound));
        when(roundDTOFactory.updateEntity(testRound, updateDTO)).thenReturn(testRound);
        when(roundRepository.save(testRound)).thenReturn(testRound);
        when(roundDTOFactory.createReadDto(testRound)).thenReturn(readDTO);

        // When
        RoundReadDTO updated = roundService.update(id, updateDTO);

        // Then
        assertNotNull(updated);
        assertEquals("apple", updated.getWord()); // Mocked response is still "apple"
        verify(roundRepository).save(testRound);
    }

    @Test
    void testDeleteById_Success() {
        // Given
        UUID id = testRound.getId();
        when(roundRepository.findById(id)).thenReturn(Optional.of(testRound));

        // When
        roundService.deleteById(id);

        // Then
        verify(roundRepository).delete(testRound);
    }

    // ----- Tests for findAllByGameId -----

    @Test
    void findAllByGameId_ShouldReturnRounds() {
        // Given
        when(roundRepository.findAllByGameId(gameId)).thenReturn(roundEntities);
        when(roundDTOFactory.createReadDto(any(Round.class))).thenAnswer(invocation -> {
            Round round = invocation.getArgument(0);
            return new RoundReadDTO(round.getId(), round.getRoundNumber(), round.getWord(), null, null);
        });

        // When
        List<RoundReadDTO> result = roundService.findAllByGameId(gameId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRoundNumber());
        assertEquals(2, result.get(1).getRoundNumber());
        verify(roundRepository).findAllByGameId(gameId);
    }

    @Test
    void findAllByGameId_ShouldReturnEmptyList_WhenNoRoundsExist() {
        // Given
        when(roundRepository.findAllByGameId(gameId)).thenReturn(List.of());

        // When
        List<RoundReadDTO> result = roundService.findAllByGameId(gameId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
