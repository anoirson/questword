package com.example.wordquest.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.example.wordquest.application.dto.round.RoundCreateDTO;
import com.example.wordquest.application.dto.round.RoundReadDTO;
import com.example.wordquest.application.dto.round.RoundUpdateDTO;

public interface RoundController extends BaseController<RoundReadDTO, RoundCreateDTO, RoundUpdateDTO> {
    ResponseEntity<List<RoundReadDTO>> findByGameId(UUID gameId);

}
