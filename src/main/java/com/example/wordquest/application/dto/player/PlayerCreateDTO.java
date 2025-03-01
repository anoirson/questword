package com.example.wordquest.application.dto.player;

import com.example.wordquest.application.dto.base.BaseCreateDTO;

public class PlayerCreateDTO extends BaseCreateDTO {
    private String username;
    private String email;

    // Constructors, getters, setters
    public PlayerCreateDTO() { }
    public PlayerCreateDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
}