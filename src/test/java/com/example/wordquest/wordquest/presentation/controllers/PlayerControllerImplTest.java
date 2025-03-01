package com.example.wordquest.wordquest.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.wordquest.application.dto.player.PlayerCreateDTO;
import com.example.wordquest.application.dto.player.PlayerReadDTO;
import com.example.wordquest.application.service.PlayerService;
import com.example.wordquest.presentation.controller.impl.PlayerControllerImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerControllerImpl.class)
@ExtendWith(SpringExtension.class)
class PlayerControllerImplTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;

    @Test
    void findByUsername_Found() throws Exception {
        // given
        String username = "testUser";
        PlayerReadDTO readDTO = new PlayerReadDTO(UUID.randomUUID(), username, "email@example.com");
        when(playerService.findByUsername(username)).thenReturn(Optional.of(readDTO));

        // when & then
        mockMvc.perform(get("/api/players/username/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("email@example.com"));
    }

    @Test
    void findByUsername_NotFound() throws Exception {
        // given
        String username = "unknownUser";
        when(playerService.findByUsername(username)).thenReturn(Optional.empty());

        // when & then
        mockMvc.perform(get("/api/players/username/{username}", username))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByEmail_Found() throws Exception {
        // given
        String email = "someEmail@example.com";
        PlayerReadDTO readDTO = new PlayerReadDTO(UUID.randomUUID(), "someUser", email);
        when(playerService.findByEmailEmailAddress(email)).thenReturn(Optional.of(readDTO));

        // when & then
        mockMvc.perform(get("/api/players/email")
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("someUser"))
                .andExpect(jsonPath("$.email").value("someEmail@example.com"));
    }

    @Test
    void findByEmail_NotFound() throws Exception {
        // given
        String email = "notfound@example.com";
        when(playerService.findByEmailEmailAddress(email)).thenReturn(Optional.empty());

        // when & then
        mockMvc.perform(get("/api/players/email").param("email", email))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPlayer_Success() throws Exception {
        // given
        PlayerReadDTO readDTO = new PlayerReadDTO(UUID.randomUUID(), "newUser", "newEmail@example.com");

        when(playerService.save(any(PlayerCreateDTO.class))).thenReturn(readDTO);

        // when & then
        mockMvc.perform(post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                               "username":"newUser",
                               "email":"newEmail@example.com"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newUser"))
                .andExpect(jsonPath("$.email").value("newEmail@example.com"));
    }
}
