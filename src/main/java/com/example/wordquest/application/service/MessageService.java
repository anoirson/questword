package com.example.wordquest.application.service;

import com.example.wordquest.application.dto.message.MessageCreateDTO;
import com.example.wordquest.application.dto.message.MessageReadDTO;
import com.example.wordquest.application.dto.message.MessageUpdateDTO;

public interface MessageService extends BaseService<MessageReadDTO, MessageCreateDTO, MessageUpdateDTO> {

}
