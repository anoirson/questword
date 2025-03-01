package com.example.wordquest.wordquest.application.tests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.wordquest.application.dto.player.PlayerCreateDTO;
import com.example.wordquest.application.dto.player.PlayerReadDTO;
import com.example.wordquest.application.factory.PlayerDTOFactory;
import com.example.wordquest.application.service.impl.PlayerServiceImpl;
import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.repository.PlayerRepository;
import com.example.wordquest.domain.valueObjects.EmailVO;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerDTOFactory playerDTOFactory;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void save_Success() {
        // given
        PlayerCreateDTO createDTO = new PlayerCreateDTO("uniqueUser", "uniqueEmail@example.com");

        when(playerRepository.existsByEmailEmailAddress("uniqueEmail@example.com")).thenReturn(false);
        when(playerRepository.existsByUsername("uniqueUser")).thenReturn(false);

        Player entity = new Player("uniqueUser", new EmailVO("uniqueEmail@example.com"));
        when(playerDTOFactory.createEntity(createDTO)).thenReturn(entity);

        Player savedEntity = new Player("uniqueUser", new EmailVO("uniqueEmail@example.com"));
        savedEntity.setId(UUID.randomUUID());
        when(playerRepository.save(entity)).thenReturn(savedEntity);

        PlayerReadDTO readDTO = new PlayerReadDTO(savedEntity.getId(), "uniqueUser", "uniqueEmail@example.com");
        when(playerDTOFactory.createReadDto(savedEntity)).thenReturn(readDTO);

        // when
        PlayerReadDTO result = playerService.save(createDTO);

        // then
        assertNotNull(result);
        assertEquals("uniqueUser", result.getUsername());
        assertEquals("uniqueEmail@example.com", result.getEmail());
        verify(playerRepository).save(entity);
    }

    @Test
    void save_FailEmailExists() {
        // given
        PlayerCreateDTO createDTO = new PlayerCreateDTO("userX", "dupEmail@example.com");

        when(playerRepository.existsByEmailEmailAddress("dupEmail@example.com")).thenReturn(true);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> playerService.save(createDTO));
        verify(playerRepository, never()).save(any());
    }

    @Test
    void findByEmail_Found() {
        // given
        String email = "someEmail@example.com";
        Player entity = new Player("userABC", new EmailVO(email));
        entity.setId(UUID.randomUUID());
        when(playerRepository.findByEmailEmailAddress(email)).thenReturn(Optional.of(entity));

        PlayerReadDTO readDTO = new PlayerReadDTO(entity.getId(), entity.getUsername(), entity.getEmail().getValue());
        when(playerDTOFactory.createReadDto(entity)).thenReturn(readDTO);

        // when
        Optional<PlayerReadDTO> result = playerService.findByEmailEmailAddress(email);

        // then
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    void findByEmail_NotFound() {
        // given
        String email = "unknown@example.com";
        when(playerRepository.findByEmailEmailAddress(email)).thenReturn(Optional.empty());

        // when
        Optional<PlayerReadDTO> result = playerService.findByEmailEmailAddress(email);

        // then
        assertFalse(result.isPresent());
    }

}
