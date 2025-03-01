package com.example.wordquest.wordquest.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.wordquest.application.dto.game.GameCreateDTO;
import com.example.wordquest.application.dto.game.GameReadDTO;
import com.example.wordquest.application.service.GameService;
import com.example.wordquest.domain.valueObjects.GameStatus;
import com.example.wordquest.presentation.controller.impl.GameControllerImpl;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GameControllerImpl.class)
@ExtendWith(SpringExtension.class)
class GameControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @Test
    void getById_Found() throws Exception {
        UUID id = UUID.randomUUID();
        GameReadDTO readDTO = new GameReadDTO(id, GameStatus.WAITING, null);

        when(gameService.findById(id)).thenReturn(Optional.of(readDTO));

        mockMvc.perform(get("/api/games/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("WAITING"));
    }

    @Test
    void getById_NotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(gameService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/games/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void createGame_Success() throws Exception {
        GameReadDTO readDTO = new GameReadDTO(UUID.randomUUID(), GameStatus.WAITING, null);

        when(gameService.save(any(GameCreateDTO.class))).thenReturn(readDTO);

        mockMvc.perform(post("/api/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "lobbyId": "00000000-0000-0000-0000-000000000000",
                                "status": "WAITING"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("WAITING"));
    }
}
