package com.example.wordquest.wordquest.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.wordquest.application.dto.round.*;
import com.example.wordquest.application.service.RoundService;
import com.example.wordquest.presentation.controller.impl.RoundControllerImpl;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoundControllerImpl.class)
@ExtendWith(SpringExtension.class)
class RoundControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoundService roundService;

    @Test
    void testGetById_Found() throws Exception {
        UUID id = UUID.randomUUID();
        RoundReadDTO readDTO = new RoundReadDTO(id, 1, "apple", null, null);

        when(roundService.findById(id)).thenReturn(Optional.of(readDTO));

        mockMvc.perform(get("/api/rounds/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("apple"));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(roundService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/rounds/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void testGetAll() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        List<RoundReadDTO> rounds = List.of(
                new RoundReadDTO(id1, 1, "apple", null, null),
                new RoundReadDTO(id2, 2, "banana", null, null));

        when(roundService.findAll()).thenReturn(rounds);

        mockMvc.perform(get("/api/rounds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].word").value("apple"))
                .andExpect(jsonPath("$[1].word").value("banana"));
    }

    @Test
    void testCreateRound_Success() throws Exception {
        UUID id = UUID.randomUUID();
        RoundReadDTO readDTO = new RoundReadDTO(id, 1, "apple", null, null);

        when(roundService.save(any(RoundCreateDTO.class))).thenReturn(readDTO);

        mockMvc.perform(post("/api/rounds")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "gameId": "00000000-0000-0000-0000-000000000000",
                                "roundNumber": 1,
                                "drawerId": "11111111-1111-1111-1111-111111111111",
                                "word": "apple"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.word").value("apple"));
    }

    @Test
    void testUpdateRound_Success() throws Exception {
        UUID id = UUID.randomUUID();
        RoundReadDTO readDTO = new RoundReadDTO(id, 2, "banana", null, null);

        when(roundService.update(any(UUID.class), any(RoundUpdateDTO.class))).thenReturn(readDTO);

        mockMvc.perform(put("/api/rounds/{id}", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "roundNumber": 2,
                                "word": "banana"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("banana"));
    }

    @Test
    void testDeleteRound_Success() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/rounds/{id}", id.toString()))
                .andExpect(status().isNoContent());
    }
}
