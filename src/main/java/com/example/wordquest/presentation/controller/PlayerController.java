package com.example.wordquest.presentation.controller;

import com.example.wordquest.application.dto.PlayerDTO;
import com.example.wordquest.domain.model.Player;
import org.springframework.web.bind.annotation.PathVariable;

public interface PlayerController extends BaseController<Player, Long> {
    PlayerDTO getPlayerDTO(@PathVariable Long id);
}