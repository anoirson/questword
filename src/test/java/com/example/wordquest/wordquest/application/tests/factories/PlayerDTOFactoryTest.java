package com.example.wordquest.wordquest.application.tests.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.example.wordquest.application.dto.player.PlayerCreateDTO;
import com.example.wordquest.application.dto.player.PlayerReadDTO;
import com.example.wordquest.application.dto.player.PlayerUpdateDTO;
import com.example.wordquest.application.factory.impl.PlayerDTOFactoryImpl;
import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.valueObjects.EmailVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlayerDTOFactoryTest {

    private PlayerDTOFactoryImpl playerDTOFactory;

    @BeforeEach
    void setUp() {
        // instantiating the dto factory
        playerDTOFactory = new PlayerDTOFactoryImpl();
    }

    @Test
    void testCreateEntity() {
        PlayerCreateDTO createDTO = new PlayerCreateDTO();
        createDTO.setEmail("test@example.com");
        createDTO.setUsername("testUser");

        // when
        Player entity = playerDTOFactory.createEntity(createDTO);

        // then
        assertNotNull(entity);
        assertEquals("test@example.com", entity.getEmail().getValue());
        assertEquals("testUser", entity.getUsername());
        // ...
    }

    @Test
    void testCreateReadDto() {

        Player entity = new Player("testUser", new EmailVO("test@example.com"));

        PlayerReadDTO readDTO = playerDTOFactory.createReadDto(entity);

        assertNotNull(readDTO);
        assertEquals("test@example.com", readDTO.getEmail());
        assertEquals("testUser", readDTO.getUsername());
    }

    @Test
    void testUpdateEntity() {
        Player entity = new Player("oldUser", new EmailVO("old@example.com"));

        PlayerUpdateDTO updateDTO = new PlayerUpdateDTO("newUser", "new@example.com");

        Player updatedEntity = playerDTOFactory.updateEntity(entity, updateDTO);

        // Assertation
        assertNotNull(updatedEntity);
        assertEquals("new@example.com", updatedEntity.getEmail().getValue());
        assertEquals("newUser", updatedEntity.getUsername());
    }
}
