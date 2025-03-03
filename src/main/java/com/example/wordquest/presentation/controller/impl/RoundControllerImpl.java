package com.example.wordquest.presentation.controller.impl;

import com.example.wordquest.application.dto.round.*;
import com.example.wordquest.application.service.RoundService;
import com.example.wordquest.presentation.controller.RoundController;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rounds")
public class RoundControllerImpl
        extends BaseControllerImpl<RoundReadDTO, RoundCreateDTO, RoundUpdateDTO>
        implements RoundController {

    private final RoundService roundService;

    public RoundControllerImpl(RoundService roundService) {
        super(roundService);
        this.roundService = roundService;
    }

    @Override
    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<RoundReadDTO>> findByGameId(@PathVariable UUID gameId) {
        List<RoundReadDTO> rounds = roundService.findAllByGameId(gameId);
        return ResponseEntity.ok(rounds);
    }

}