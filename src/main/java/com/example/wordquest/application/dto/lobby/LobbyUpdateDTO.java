package com.example.wordquest.application.dto.lobby;

import com.example.wordquest.application.dto.base.BaseUpdateDTO;
import io.micrometer.common.lang.Nullable;

public class LobbyUpdateDTO extends BaseUpdateDTO {
    @Nullable
    private String name;
    @Nullable
    private String hostId; // or UUID

    public LobbyUpdateDTO() { }

    public LobbyUpdateDTO(String name, String hostId) {
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
