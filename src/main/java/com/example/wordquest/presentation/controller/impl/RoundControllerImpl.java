package com.example.wordquest.presentation.controller.impl;

import com.example.wordquest.application.dto.round.*;
import com.example.wordquest.application.service.RoundService;
import com.example.wordquest.presentation.controller.RoundController;
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

    // Implement custom endpoints if needed (e.g., /game/{gameId}/rounds)
}