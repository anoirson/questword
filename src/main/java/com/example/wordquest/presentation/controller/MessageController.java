package com.example.wordquest.presentation.controller;

import com.example.wordquest.application.dto.message.MessageCreateDTO;
import com.example.wordquest.application.dto.message.MessageReadDTO;
import com.example.wordquest.application.dto.message.MessageUpdateDTO;

public interface MessageController
        extends BaseController<MessageReadDTO, MessageCreateDTO, MessageUpdateDTO> {

    // Add custom endpoints if needed
}
