package com.example.wordquest.wordquest.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.wordquest.application.dto.message.*;
import com.example.wordquest.application.service.MessageService;
import com.example.wordquest.presentation.controller.impl.MessageControllerImpl;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MessageControllerImpl.class)
@ExtendWith(SpringExtension.class)
class MessageControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageService messageService;

    @Test
    void testGetById_Found() throws Exception {
        UUID id = UUID.randomUUID();
        MessageReadDTO readDTO = new MessageReadDTO(id, null, null, "Guess1", false);

        when(messageService.findById(id)).thenReturn(Optional.of(readDTO));

        mockMvc.perform(get("/api/messages/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Guess1"));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(messageService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/messages/{id}", id.toString()))
                .andExpect(status().isOk()) // If you want 404, modify BaseController
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void testGetAll() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        List<MessageReadDTO> messages = List.of(
                new MessageReadDTO(id1, null, null, "Guess1", false),
                new MessageReadDTO(id2, null, null, "Guess2", true));

        when(messageService.findAll()).thenReturn(messages);

        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].message").value("Guess1"))
                .andExpect(jsonPath("$[1].message").value("Guess2"));
    }

    @Test
    void testCreateMessage_Success() throws Exception {
        UUID id = UUID.randomUUID();
        MessageReadDTO readDTO = new MessageReadDTO(id, null, null, "Guess1", false);

        when(messageService.save(any(MessageCreateDTO.class))).thenReturn(readDTO);

        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "roundId": "00000000-0000-0000-0000-000000000000",
                                "playerId": "11111111-1111-1111-1111-111111111111",
                                "message": "Guess1",
                                "isCorrect": false
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Guess1"));
    }

    @Test
    void testUpdateMessage_Success() throws Exception {
        UUID id = UUID.randomUUID();
        MessageReadDTO readDTO = new MessageReadDTO(id, null, null, "Updated Guess", true);

        when(messageService.update(any(UUID.class), any(MessageUpdateDTO.class))).thenReturn(readDTO);

        mockMvc.perform(put("/api/messages/{id}", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "message": "Updated Guess",
                                "isCorrect": true
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Updated Guess"))
                .andExpect(jsonPath("$.correct").value(true));
    }

    @Test
    void testDeleteMessage_Success() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/messages/{id}", id.toString()))
                .andExpect(status().isNoContent());
    }
}