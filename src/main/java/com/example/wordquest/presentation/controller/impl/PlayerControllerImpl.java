package com.example.wordquest.presentation.controller.impl;

import com.example.wordquest.application.dto.player.PlayerCreateDTO;
import com.example.wordquest.application.dto.player.PlayerReadDTO;
import com.example.wordquest.application.dto.player.PlayerUpdateDTO;
import com.example.wordquest.application.service.PlayerService;
import com.example.wordquest.presentation.controller.PlayerController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Tag(name = "Player API", description = "Manage Players in WordQuest")
@RestController
@RequestMapping("/api/players")
public class PlayerControllerImpl
        extends BaseControllerImpl<PlayerReadDTO, PlayerCreateDTO, PlayerUpdateDTO>
        implements PlayerController {

    private final PlayerService playerService;
    protected PlayerControllerImpl(PlayerService baseService) {
        super(baseService);
        this.playerService = baseService;
    }
    @Operation(summary = "Get Player by Username")
    @Override
    @GetMapping("/username/{username}")
    public ResponseEntity<PlayerReadDTO> findByUsername(@PathVariable String username) {
        return playerService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Player by Email")
    @Override
    @GetMapping("/email")
    public ResponseEntity<PlayerReadDTO> findByEmailEmailAddress(@RequestParam String email) {
        return playerService.findByEmailEmailAddress(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    } 

}