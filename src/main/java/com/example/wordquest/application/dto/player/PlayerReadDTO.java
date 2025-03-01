package com.example.wordquest.application.dto.player;

import com.example.wordquest.application.dto.base.BaseReadDTO;
import java.util.UUID;


public class PlayerReadDTO extends BaseReadDTO {
    private String username;
    private String email;

    public PlayerReadDTO(UUID id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
}