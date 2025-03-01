package com.example.wordquest.application.dto.base;

import java.util.UUID;

public abstract class BaseReadDTO extends BaseDTO {
    protected UUID id;

    public UUID getId() { return id; }
}
