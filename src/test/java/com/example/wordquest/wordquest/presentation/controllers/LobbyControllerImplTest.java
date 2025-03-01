package com.example.wordquest.wordquest.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.wordquest.application.dto.lobby.LobbyCreateDTO;
import com.example.wordquest.application.dto.lobby.LobbyReadDTO;
import com.example.wordquest.application.service.LobbyService;
import com.example.wordquest.presentation.controller.impl.LobbyControllerImpl;
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

@WebMvcTest(LobbyControllerImpl.class)
@ExtendWith(SpringExtension.class)
class LobbyControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LobbyService lobbyService;

    @Test
    void getById_Found() throws Exception {
        UUID id = UUID.randomUUID();
        LobbyReadDTO readDTO = new LobbyReadDTO(id, "MyLobby", null);
        when(lobbyService.findById(id)).thenReturn(Optional.of(readDTO));

        mockMvc.perform(get("/api/lobbies/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyLobby"));
    }

    @Test
    void getById_NotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(lobbyService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/lobbies/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void create_Success() throws Exception {
        LobbyReadDTO readDTO = new LobbyReadDTO(UUID.randomUUID(), "CreatedLobby", null);
        when(lobbyService.save(any(LobbyCreateDTO.class))).thenReturn(readDTO);

        mockMvc.perform(post("/api/lobbies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "name": "CreatedLobby",
                                "hostId": "00000000-0000-0000-0000-000000000000"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("CreatedLobby"));
    }
}
