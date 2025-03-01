package com.example.wordquest.application.service.impl;

import com.example.wordquest.application.dto.message.*;
import com.example.wordquest.application.factory.impl.MessageDTOFactoryImpl;
import com.example.wordquest.application.service.MessageService;
import com.example.wordquest.domain.model.Message;
import com.example.wordquest.domain.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MessageServiceImpl
        extends BaseServiceImpl<Message, MessageReadDTO, MessageCreateDTO, MessageUpdateDTO>
        implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageDTOFactoryImpl messageDTOFactory;

    public MessageServiceImpl(MessageRepository messageRepository,
            MessageDTOFactoryImpl messageDTOFactory) {
        super(messageRepository, messageDTOFactory);
        this.messageRepository = messageRepository;
        this.messageDTOFactory = messageDTOFactory;
    }
    // Add custom methods if needed
}
