package com.example.wordquest.presentation.controller;

import com.example.wordquest.application.dto.player.PlayerCreateDTO;
import com.example.wordquest.application.dto.player.PlayerReadDTO;
import com.example.wordquest.application.dto.player.PlayerUpdateDTO;
import org.springframework.http.ResponseEntity;



public interface PlayerController extends BaseController<PlayerReadDTO, PlayerCreateDTO, PlayerUpdateDTO> {

    ResponseEntity<PlayerReadDTO> findByUsername(String username);

    ResponseEntity<PlayerReadDTO> findByEmailEmailAddress(String email);
}
