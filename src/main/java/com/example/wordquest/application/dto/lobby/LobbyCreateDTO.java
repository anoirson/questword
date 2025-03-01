package com.example.wordquest.application.dto.lobby;

import com.example.wordquest.application.dto.base.BaseCreateDTO;

public class LobbyCreateDTO extends BaseCreateDTO {
    private String name;
    private String hostId; // or use UUID if you prefer

    public LobbyCreateDTO() {
    }

    public LobbyCreateDTO(String name, String hostId) {
        this.name = name;
        this.hostId = hostId;
    }

    public String getName() {
        return name;
    }

    public String getHostId() {
        return hostId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
