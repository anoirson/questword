package com.example.wordquest.presentation.controller.impl;

import com.example.wordquest.application.dto.message.*;
import com.example.wordquest.application.service.MessageService;
import com.example.wordquest.presentation.controller.MessageController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageControllerImpl
        extends BaseControllerImpl<MessageReadDTO, MessageCreateDTO, MessageUpdateDTO>
        implements MessageController {

    private final MessageService messageService;

    public MessageControllerImpl(MessageService messageService) {
        super(messageService);
        this.messageService = messageService;
    }

}
