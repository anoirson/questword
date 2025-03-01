package com.example.wordquest.application.dto.player;


import com.example.wordquest.application.dto.base.BaseUpdateDTO;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Email;

public class PlayerUpdateDTO extends BaseUpdateDTO {
    @Nullable
    private String username;
    @Nullable
    @Email(message = "Email should be valid")
    private String email;

    public PlayerUpdateDTO() { }
    public PlayerUpdateDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}