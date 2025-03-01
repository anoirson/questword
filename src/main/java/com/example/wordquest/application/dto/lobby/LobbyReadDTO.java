package com.example.wordquest.application.dto.lobby;

import com.example.wordquest.application.dto.base.BaseReadDTO;
import java.util.UUID;

public class LobbyReadDTO extends BaseReadDTO {
    private String name;
    private UUID hostId;

    public LobbyReadDTO(UUID id, String name, UUID hostId) {
        this.id = id;       // from BaseReadDTO
        this.name = name;
        this.hostId = hostId;
    }

    public String getName() {
        return name;
    }

    public UUID getHostId() {
        return hostId;
    }
}
